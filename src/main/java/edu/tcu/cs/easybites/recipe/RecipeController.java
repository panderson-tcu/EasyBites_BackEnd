package edu.tcu.cs.easybites.recipe;

import edu.tcu.cs.easybites.system.Result;
import edu.tcu.cs.easybites.system.StatusCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RecipeController {
    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/recipe/{recipeId}")
    public Result findRecipeById(@PathVariable Integer recipeId) {
        Recipe foundRecipe = this.recipeService.findById(recipeId);
        return new Result(true, StatusCode.SUCCESS, "View recipe by id successful", foundRecipe);
    }

}


