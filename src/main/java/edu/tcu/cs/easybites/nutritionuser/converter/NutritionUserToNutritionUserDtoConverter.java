package edu.tcu.cs.easybites.nutritionuser.converter;

import edu.tcu.cs.easybites.nutritionuser.NutritionUser;
import edu.tcu.cs.easybites.nutritionuser.dto.NutritionUserDto;
//import edu.tcu.cs.easybites.recipe.Recipe;
//import edu.tcu.cs.easybites.recipe.converter.RecipeToRecipeDtoConverter;
//import edu.tcu.cs.easybites.recipe.dto.RecipeDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class NutritionUserToNutritionUserDtoConverter implements Converter<NutritionUser, NutritionUserDto> {
//    private final RecipeToRecipeDtoConverter recipeToRecipeDtoConverter;
//
//    public NutritionUserToNutritionUserDtoConverter(RecipeToRecipeDtoConverter recipeToRecipeDtoConverter) {
//        this.recipeToRecipeDtoConverter = recipeToRecipeDtoConverter;
//    }

    @Override
    public NutritionUserDto convert(NutritionUser source) {
        NutritionUserDto nutritionUserDto = new NutritionUserDto(source.getFirstName(),
                                                                    source.getLastName(),
                                                                    source.getNutritionUserId(),
                                                                    source.getAdminLevel()
//                                                                    source.getRecipes() != null
//                                                                            ? this.recipeToRecipeDtoConverter.convertList(source.getRecipes())
//                                                                            : null
        );
        return nutritionUserDto;
    }

    public List<NutritionUserDto> convertList(List<NutritionUser> nutritionUsers) {
        return nutritionUsers.stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }
}
