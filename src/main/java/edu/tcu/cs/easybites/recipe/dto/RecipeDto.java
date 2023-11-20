package edu.tcu.cs.easybites.recipe.dto;

import edu.tcu.cs.easybites.allergen.dto.AllergenDto;
import edu.tcu.cs.easybites.appliance.dto.ApplianceDto;
import edu.tcu.cs.easybites.appuser.dto.AppUserDto;
import edu.tcu.cs.easybites.ingredient.dto.IngredientDto;
import edu.tcu.cs.easybites.nutritionuser.dto.NutritionUserDto;
import edu.tcu.cs.easybites.protein.dto.ProteinDto;

import java.util.List;

public record RecipeDto(Integer recipeId,
                       String title,
                       Integer cooktime,
                       String ingredientsQuantity,
                       Double estimatedCost,
                       String instructions,
                       Integer servings,
                       String status,
                       ProteinDto protein,
                       NutritionUserDto recipeOwner,
                       List<IngredientDto> ingredients,
                       List<ApplianceDto> appliances,
                       List<AllergenDto> allergens,
                       List<AppUserDto> appUsers
                       ){
}
