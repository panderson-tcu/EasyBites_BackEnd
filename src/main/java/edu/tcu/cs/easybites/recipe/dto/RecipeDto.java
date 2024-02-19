package edu.tcu.cs.easybites.recipe.dto;

import edu.tcu.cs.easybites.allergen.dto.AllergenDto;
import edu.tcu.cs.easybites.appliance.dto.ApplianceDto;
import edu.tcu.cs.easybites.appuser.dto.AppUserDto;
import edu.tcu.cs.easybites.ingredient.dto.IngredientDto;
import edu.tcu.cs.easybites.nutritionuser.dto.NutritionUserDto;
import edu.tcu.cs.easybites.protein.dto.ProteinDto;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

public record RecipeDto(Integer recipeId,
                       @NotEmpty(message = "title is required.")
                       String title,
                        @NotNull(message = "cooktime is required.")
                        @Positive(message = "cooktime must be positive number")
                        Integer cooktime,
                        String imageUrl,
                        @NotEmpty(message = "ingredient quantity is required.")
                        String ingredientsQuantity,
                        @NotNull(message = "cost is required.")
                        @Positive(message = "cosst must be positive number")
                        Double estimatedCost,
                        @NotEmpty(message = "instructions are required.")
                        String instructions,
                        @NotNull(message = "servings is required.")
                        @Positive(message = "servings must be positive number")
                        Integer servings,
                        String status,
                        @NotNull(message = "protein is required.")
                        ProteinDto protein,
                        @NotNull(message = "recipeOwner is required.")
                        NutritionUserDto recipeOwner,
                        @NotNull(message = "ingredients is required.")
                        List<IngredientDto> ingredients,
                        @NotNull(message = "appliances is required.")
                        List<ApplianceDto> appliances,
                        List<AllergenDto> allergens,
                        List<AppUserDto> appUsers
                       ){
}
