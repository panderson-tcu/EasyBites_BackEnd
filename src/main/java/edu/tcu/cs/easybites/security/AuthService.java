package edu.tcu.cs.easybites.security;

import edu.tcu.cs.easybites.nutritionuser.MyUserPrincipal;
import edu.tcu.cs.easybites.nutritionuser.NutritionUser;
import edu.tcu.cs.easybites.nutritionuser.converter.NutritionUserToNutritionUserDtoConverter;
import edu.tcu.cs.easybites.nutritionuser.dto.NutritionUserDto;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    private final JwtProvider jwtProvider;

    private final NutritionUserToNutritionUserDtoConverter nutritionUserToNutritionUserDtoConverter;

    public AuthService(JwtProvider jwtProvider, NutritionUserToNutritionUserDtoConverter nutritionUserToNutritionUserDtoConverter) {
        this.jwtProvider = jwtProvider;
        this.nutritionUserToNutritionUserDtoConverter = nutritionUserToNutritionUserDtoConverter;
    }

    public Map<String, Object> createLoginInfo(Authentication authentication) {
        // Create user info.
        MyUserPrincipal principal = (MyUserPrincipal) authentication.getPrincipal();
        NutritionUser nutritionUser = principal.getNutritionUser();
        NutritionUserDto userDto = this.nutritionUserToNutritionUserDtoConverter.convert(nutritionUser);

        // Create a JWT
        String token = this.jwtProvider.createToken(authentication);

        Map<String, Object> loginResultMap = new HashMap<>();
        loginResultMap.put("userInfo", userDto);
        loginResultMap.put("token", token);


        return loginResultMap;
    }
}
