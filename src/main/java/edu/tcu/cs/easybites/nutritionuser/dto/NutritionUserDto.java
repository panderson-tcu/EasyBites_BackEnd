package edu.tcu.cs.easybites.nutritionuser.dto;

import jakarta.validation.constraints.NotEmpty;

public record NutritionUserDto(Integer nutritionUserId,
                               @NotEmpty(message = "first name is required.")
                               String firstName,
                               @NotEmpty(message = "last name is required.")
                               String lastName,
                               @NotEmpty(message = "admin level is required.")
                               String adminLevel,
                               @NotEmpty(message = "email  is required.")
                               String email,
                               boolean enabled
                               ) {
}
