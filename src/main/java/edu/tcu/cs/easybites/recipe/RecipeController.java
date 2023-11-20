package edu.tcu.cs.easybites.recipe;

import edu.tcu.cs.easybites.recipe.converter.RecipeToRecipeDtoConverter;
import edu.tcu.cs.easybites.recipe.dto.RecipeDto;
import edu.tcu.cs.easybites.system.Result;
import edu.tcu.cs.easybites.system.StatusCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RecipeController {
    private final RecipeService recipeService;
    private final RecipeToRecipeDtoConverter recipeToRecipeDtoConverter;

    public RecipeController(RecipeService recipeService, RecipeToRecipeDtoConverter recipeToRecipeDtoConverter) {
        this.recipeService = recipeService;
        this.recipeToRecipeDtoConverter = recipeToRecipeDtoConverter;
    }

    @GetMapping("/recipes/{recipeId}")
    public Result findRecipeById(@PathVariable Integer recipeId) {
        Recipe foundRecipe = this.recipeService.findById(recipeId);
        RecipeDto recipeDto = this.recipeToRecipeDtoConverter.convert(foundRecipe);
        return new Result(true, StatusCode.SUCCESS, "View recipe by id successful", recipeDto);
    }

    @GetMapping("/recipes")
    public Result findAllRecipes() {
        List<Recipe> foundRecipes = this.recipeService.findAll();
        List<RecipeDto> recipeDtos = recipeToRecipeDtoConverter.convertList(foundRecipes);
        return new Result(true, StatusCode.SUCCESS, "View all recipes successful", recipeDtos);
    }

}


