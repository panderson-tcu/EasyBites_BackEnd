package edu.tcu.cs.easybites.ingredient.converter;

import edu.tcu.cs.easybites.ingredient.Ingredient;
import edu.tcu.cs.easybites.ingredient.dto.IngredientDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class IngredientToIngredientDtoConverter implements Converter<Ingredient, IngredientDto> {
    @Override
    public IngredientDto convert(Ingredient source) {
        IngredientDto ingredientDto = new IngredientDto(source.getUpcValue());
        return ingredientDto;
    }

    public List<IngredientDto> convertList(List<Ingredient> ingredients) {
        return ingredients.stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }
}
