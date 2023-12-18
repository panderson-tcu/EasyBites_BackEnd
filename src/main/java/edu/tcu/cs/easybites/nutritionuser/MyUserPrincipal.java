package edu.tcu.cs.easybites.nutritionuser;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Collection;

/**
 * My UserPrincipal class serves as a bridge between your application's user
 * representation (NutritionUser) and Spring Security's UserDetails interface,
 * allowing Spring Security to interact with your user objects.
 * It extracts necessary information such as authorities, username, password,
 * and account status from the NutritionUser object.
 */
public class MyUserPrincipal implements UserDetails {
    private NutritionUser nutritionUser; // Inject NutritionUser to be used

    public MyUserPrincipal(NutritionUser nutritionUser) {
        this.nutritionUser = nutritionUser;
    }

    /**
     * converts the adminLevel property of the nutritionUser
     * into a collection of GrantedAuthority objects.
     * Each role is prefixed with "ROLE_" and added to the collection.
     * @return - Collection of GrantedAuthority objects.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.stream(StringUtils.tokenizeToStringArray(this.nutritionUser.getAdminLevel(), " "))
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .toList();
    }

    @Override
    public String getPassword() {
        return this.nutritionUser.getPassword();
    }

    @Override
    public String getUsername() {
        return this.nutritionUser.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return nutritionUser.isEnabled();
    }

    public NutritionUser getNutritionUser() {
        return nutritionUser;
    }
}
