package edu.tcu.cs.easybites.recipe.converter;

import edu.tcu.cs.easybites.allergen.converter.AllergenToAllergenDtoConverter;
import edu.tcu.cs.easybites.ingredient.converter.IngredientToIngredientDtoConverter;
import edu.tcu.cs.easybites.protein.converter.ProteinToProteinDtoConverter;
import edu.tcu.cs.easybites.recipe.Recipe;
import edu.tcu.cs.easybites.recipe.dto.ApprovedRecipeDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RecipeToApprovedRecipeDtoConverter implements Converter<Recipe, ApprovedRecipeDto> {
    private final ProteinToProteinDtoConverter proteinToProteinDtoConverter;
    private final AllergenToAllergenDtoConverter allergenToAllergenDtoConverter;
    private final IngredientToIngredientDtoConverter ingredientToIngredientDtoConverter;
    public RecipeToApprovedRecipeDtoConverter(ProteinToProteinDtoConverter proteinToProteinDtoConverter,
                                              AllergenToAllergenDtoConverter allergenToAllergenDtoConverter, IngredientToIngredientDtoConverter ingredientToIngredientDtoConverter) {
        this.proteinToProteinDtoConverter = proteinToProteinDtoConverter;
        this.allergenToAllergenDtoConverter = allergenToAllergenDtoConverter;
        this.ingredientToIngredientDtoConverter = ingredientToIngredientDtoConverter;
    }

    @Override
    public ApprovedRecipeDto convert(Recipe source) {
        ApprovedRecipeDto approvedRecipeDto = new ApprovedRecipeDto(
                source.getRecipeId(),
                source.getTitle(),
                source.getCookTime(),
                source.getImageUrl(),
                source.getEstimatedCost(),
                source.getProtein() != null
                    ? this.proteinToProteinDtoConverter.convert(source.getProtein())
                    : null,
                source.getAllergens() != null
                    ? this.allergenToAllergenDtoConverter.convertList(source.getAllergens())
                    : null,
                source.getIngredients() != null
                        ? this.ingredientToIngredientDtoConverter.convertList(source.getIngredients())
                        : null,
                source.getServings()
        );
        return approvedRecipeDto;
    }

    public List<ApprovedRecipeDto> convertList(List<Recipe> recipes) {
        return recipes.stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }
}
