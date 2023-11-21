package edu.tcu.cs.easybites.ingredient.converter;

import edu.tcu.cs.easybites.ingredient.Ingredient;
import edu.tcu.cs.easybites.ingredient.dto.IngredientDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class IngredientDtoToIngredientConverter implements Converter<IngredientDto, Ingredient> {
    @Override
    public Ingredient convert(IngredientDto source) {
        Ingredient ingredient = new Ingredient();
        ingredient.setUpcValue(source.upcValue());
        return ingredient;
    }

    public List<Ingredient> convertList(List<IngredientDto> ingredientDtos) {
        return ingredientDtos.stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }
}
