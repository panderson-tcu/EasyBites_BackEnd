package edu.tcu.cs.easybites.recipe.converter;

import edu.tcu.cs.easybites.allergen.converter.AllergenToAllergenDtoConverter;
import edu.tcu.cs.easybites.appliance.Appliance;
import edu.tcu.cs.easybites.appliance.converter.ApplianceToApplianceDtoConverter;
import edu.tcu.cs.easybites.appliance.dto.ApplianceDto;
import edu.tcu.cs.easybites.appuser.converter.AppUserToAppUserDtoConverter;
import edu.tcu.cs.easybites.ingredient.converter.IngredientToIngredientDtoConverter;
import edu.tcu.cs.easybites.nutritionuser.converter.NutritionUserToNutritionUserDtoConverter;
import edu.tcu.cs.easybites.protein.converter.ProteinToProteinDtoConverter;
import edu.tcu.cs.easybites.recipe.Recipe;
import edu.tcu.cs.easybites.recipe.dto.RecipeDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RecipeToRecipeDtoConverter implements Converter<Recipe, RecipeDto> {

    private final ProteinToProteinDtoConverter proteinToProteinDtoConverter;
    private final NutritionUserToNutritionUserDtoConverter nutritionUserToNutritionUserDtoConverter;
    private final IngredientToIngredientDtoConverter ingredientToIngredientDtoConverter;
    private final ApplianceToApplianceDtoConverter applianceToApplianceDtoConverter;
    private final AllergenToAllergenDtoConverter allergenToAllergenDtoConverter;
    private final AppUserToAppUserDtoConverter appUserToAppUserDtoConverter;

    public RecipeToRecipeDtoConverter(ProteinToProteinDtoConverter proteinToProteinDtoConverter,
                                      NutritionUserToNutritionUserDtoConverter nutritionUserToNutritionUserDtoConverter,
                                      IngredientToIngredientDtoConverter ingredientToIngredientDtoConverter,
                                      ApplianceToApplianceDtoConverter applianceToApplianceDtoConverter,
                                      AllergenToAllergenDtoConverter allergenToAllergenDtoConverter,
                                      AppUserToAppUserDtoConverter appUserToAppUserDtoConverter) {

        this.proteinToProteinDtoConverter = proteinToProteinDtoConverter;
        this.nutritionUserToNutritionUserDtoConverter = nutritionUserToNutritionUserDtoConverter;
        this.ingredientToIngredientDtoConverter = ingredientToIngredientDtoConverter;
        this.applianceToApplianceDtoConverter = applianceToApplianceDtoConverter;
        this.allergenToAllergenDtoConverter = allergenToAllergenDtoConverter;
        this.appUserToAppUserDtoConverter = appUserToAppUserDtoConverter;

    }

    @Override
    public RecipeDto convert(Recipe source) {
        RecipeDto recipeDto = new RecipeDto(source.getRecipeId(),
                                            source.getTitle(),
                                            source.getCookTime(),
                                            source.getIngredientsQuantity(),
                                            source.getEstimatedCost(),
                                            source.getInstructions(),
                                            source.getServings(),
                                            source.getStatus(),
                                            source.getProtein() != null
                                                    ? this.proteinToProteinDtoConverter.convert(source.getProtein())
                                                    : null,
                                            source.getRecipeOwner() != null
                                                    ? this.nutritionUserToNutritionUserDtoConverter.convert(source.getRecipeOwner())
                                                    : null,
                                            source.getIngredients() != null
                                                    ? this.ingredientToIngredientDtoConverter.convertList(source.getIngredients())
                                                    : null,
                                            source.getAppliances() != null
                                                    ? this.applianceToApplianceDtoConverter.convertList(source.getAppliances())
                                                    : null,
                                            source.getAllergens() != null
                                                    ? this.allergenToAllergenDtoConverter.convertList(source.getAllergens())
                                                    : null,
                                            source.getAppUsers() != null
                                                    ? this.appUserToAppUserDtoConverter.convertList(source.getAppUsers())
                                                    : null
        );
        return recipeDto;
    }

    public List<RecipeDto> convertList(List<Recipe> recipes) {
        return recipes.stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }
}
