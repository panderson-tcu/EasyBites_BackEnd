package edu.tcu.cs.easybites.nutritionuser.dto;

import edu.tcu.cs.easybites.recipe.dto.RecipeDto;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record NutritionUserDto(Integer nutritionUserId,
                               @NotEmpty(message = "first name is required.")
                               String firstName,
                               @NotEmpty(message = "last name is required.")
                               String lastName,
                               @NotEmpty(message = "admin level is required.")
                               String adminLevel,
                               @NotEmpty(message = "email  is required.")
                               String email
                               ) {
}
