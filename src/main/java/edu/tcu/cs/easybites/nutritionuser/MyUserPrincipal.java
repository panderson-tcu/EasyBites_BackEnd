package edu.tcu.cs.easybites.nutritionuser;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MyUserPrincipal implements UserDetails {

    private NutritionUser nutritionUser;

    public MyUserPrincipal(NutritionUser nutritionUser) {
        this.nutritionUser = nutritionUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String role = "ROLE_" + this.nutritionUser.getAdminLevel(); // create role string from NutritionUser.adminLevel
        List<SimpleGrantedAuthority> roles = new ArrayList<>(); // instantiate a list because this method requires a collection o SimpleGrantedAuthority
        roles.add(new SimpleGrantedAuthority(role)); // add role to list
        return roles;
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
        return true;
    }

    public NutritionUser getNutritionUser() {
        return nutritionUser;
    }
}
