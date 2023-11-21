package edu.tcu.cs.easybites.nutritionuser.dto;

public record NutritionUserDto(String firstName,
                               String lastName,
                               Integer nutritionUserId,
                               String adminLevel
                               ) {
}
