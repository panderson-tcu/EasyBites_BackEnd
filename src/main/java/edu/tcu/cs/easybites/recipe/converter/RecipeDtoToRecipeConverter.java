package edu.tcu.cs.easybites.recipe.converter;

import edu.tcu.cs.easybites.allergen.converter.AllergenDtoToAllergenConverter;
import edu.tcu.cs.easybites.appliance.converter.ApplianceDtoToApplianceConverter;
import edu.tcu.cs.easybites.appuser.converter.AppUserDtoToAppUserConverter;
import edu.tcu.cs.easybites.ingredient.converter.IngredientDtoToIngredientConverter;
import edu.tcu.cs.easybites.nutritionuser.converter.NutritionUserDtoToNutritionUserConverter;
import edu.tcu.cs.easybites.protein.converter.ProteinDtoToProteinConverter;
import edu.tcu.cs.easybites.recipe.Recipe;
import edu.tcu.cs.easybites.recipe.dto.RecipeDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class RecipeDtoToRecipeConverter implements Converter<RecipeDto, Recipe> {
    private final ProteinDtoToProteinConverter proteinDtoToProteinConverter;
    private final NutritionUserDtoToNutritionUserConverter nutritionUserDtoToNutritionUserConverter;
    private final IngredientDtoToIngredientConverter ingredientDtoToIngredientConverter;
    private final ApplianceDtoToApplianceConverter applianceDtoToApplianceConverter;
    private final AllergenDtoToAllergenConverter allergenDtoToAllergenConverter;
    private final AppUserDtoToAppUserConverter appUserDtoToAppUserConverter;

    public RecipeDtoToRecipeConverter(ProteinDtoToProteinConverter proteinDtoToProteinConverter,
                                      NutritionUserDtoToNutritionUserConverter nutritionUserDtoToNutritionUserConverter,
                                      IngredientDtoToIngredientConverter ingredientDtoToIngredientConverter,
                                      ApplianceDtoToApplianceConverter applianceDtoToApplianceConverter,
                                      AllergenDtoToAllergenConverter allergenDtoToAllergenConverter,
                                      AppUserDtoToAppUserConverter appUserDtoToAppUserConverter) {
        this.proteinDtoToProteinConverter = proteinDtoToProteinConverter;
        this.nutritionUserDtoToNutritionUserConverter = nutritionUserDtoToNutritionUserConverter;
        this.ingredientDtoToIngredientConverter = ingredientDtoToIngredientConverter;
        this.applianceDtoToApplianceConverter = applianceDtoToApplianceConverter;
        this.allergenDtoToAllergenConverter = allergenDtoToAllergenConverter;
        this.appUserDtoToAppUserConverter = appUserDtoToAppUserConverter;
    }

    @Override
    public Recipe convert(RecipeDto source) {
        Recipe recipe = new Recipe();
        recipe.setRecipeId(source.recipeId());
        recipe.setTitle(source.title());
        recipe.setCookTime(source.cooktime());
        recipe.setIngredientsQuantity(source.ingredientsQuantity());
        recipe.setEstimatedCost(source.estimatedCost());
        recipe.setInstructions(source.instructions());
        recipe.setServings(source.servings());
        recipe.setStatus(source.status());
        recipe.setProtein(source.protein() != null ? this.proteinDtoToProteinConverter.convert(source.protein()) : null);
        recipe.setRecipeOwner(source.recipeOwner() != null ? this.nutritionUserDtoToNutritionUserConverter.convert(source.recipeOwner()) : null );
        recipe.setIngredients(source.ingredients() != null ? this.ingredientDtoToIngredientConverter.convertList(source.ingredients()) : null );
        recipe.setAppliances(source.appliances() != null ? this.applianceDtoToApplianceConverter.convertList(source.appliances()) : null );
        recipe.setAllergens(source.allergens() != null ? this.allergenDtoToAllergenConverter.convertList(source.allergens()) : null );
        recipe.setAppUsers(source.appUsers() != null ? this.appUserDtoToAppUserConverter.convertList(source.appUsers()) : null );
        return recipe;
    }
}
