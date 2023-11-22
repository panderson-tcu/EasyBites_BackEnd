package edu.tcu.cs.easybites.nutritionuser.dto;

import edu.tcu.cs.easybites.recipe.dto.RecipeDto;

import java.util.List;

public record NutritionUserDto(String firstName,
                               String lastName,
                               Integer nutritionUserId,
                               String adminLevel

                               ) {
}
