package edu.tcu.cs.easybites.recipe.dto;

import edu.tcu.cs.easybites.allergen.dto.AllergenDto;
import edu.tcu.cs.easybites.ingredient.dto.IngredientDto;
import edu.tcu.cs.easybites.protein.dto.ProteinDto;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

public record ApprovedRecipeDto(Integer recipeId,
                               @NotEmpty(message = "title is required.")
                               String title,
                               @NotNull(message = "cooktime is required.")
                               @Positive(message = "cooktime must be positive number")
                               Integer cooktime,
                               String imageUrl,
                               @NotNull(message = "cost is required.")
                               @Positive(message = "cost must be positive number")
                               Double estimatedCost,
                               @NotNull(message = "protein is required.")
                               ProteinDto protein,
                               List<AllergenDto> allergens,
                               List<IngredientDto> ingredients,
                               Integer servings
                               ){
}
