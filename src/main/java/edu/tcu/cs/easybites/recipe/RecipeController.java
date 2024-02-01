package edu.tcu.cs.easybites.recipe;

import edu.tcu.cs.easybites.recipe.converter.RecipeDtoToRecipeConverter;
import edu.tcu.cs.easybites.recipe.converter.RecipeToRecipeDtoConverter;
import edu.tcu.cs.easybites.recipe.dto.RecipeDto;
import edu.tcu.cs.easybites.system.Result;
import edu.tcu.cs.easybites.system.StatusCode;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.endpoint.base-url}/recipes")
@CrossOrigin(origins = "https://easy-bites-tcu.vercel.app") // PROD ONLY: this is the URL for the EasyBites front end server. Here we allow CORS.**/
//@CrossOrigin(origins = "http://localhost:3000") // FOR DEV ONLY
public class RecipeController {
    private final RecipeService recipeService;
    private final RecipeToRecipeDtoConverter recipeToRecipeDtoConverter;
    private final RecipeDtoToRecipeConverter recipeDtoToRecipeConverter;

    public RecipeController(RecipeService recipeService, RecipeToRecipeDtoConverter recipeToRecipeDtoConverter, RecipeDtoToRecipeConverter recipeDtoToRecipeConverter) {
        this.recipeService = recipeService;
        this.recipeToRecipeDtoConverter = recipeToRecipeDtoConverter;
        this.recipeDtoToRecipeConverter = recipeDtoToRecipeConverter;
    }

    @GetMapping("/{recipeId}")
    public Result findRecipeById(@PathVariable Integer recipeId) {
        Recipe foundRecipe = this.recipeService.findById(recipeId);
        RecipeDto recipeDto = this.recipeToRecipeDtoConverter.convert(foundRecipe);
        return new Result(true, StatusCode.SUCCESS, "View recipe by id successful", recipeDto);
    }

    @GetMapping
    public Result findAllRecipes() {
        List<Recipe> foundRecipes = this.recipeService.findAll();
        List<RecipeDto> recipeDtos = recipeToRecipeDtoConverter.convertList(foundRecipes);
        return new Result(true, StatusCode.SUCCESS, "View all recipes successful", recipeDtos);
    }

    @PostMapping
    public Result addRecipe(@Valid @RequestBody RecipeDto recipeDto) {
        Recipe newRecipe = this.recipeDtoToRecipeConverter.convert(recipeDto);
        newRecipe.setStatus("pending");
        Recipe savedRecipe = this.recipeService.save(newRecipe);
        RecipeDto savedRecipeDto = recipeToRecipeDtoConverter.convert(savedRecipe);
        return new Result(true, StatusCode.SUCCESS, "Recipe submitted successfully", savedRecipeDto);
    }

    @PutMapping("/{recipeId}")
    public Result updateRecipe(@PathVariable Integer recipeId, @Valid @RequestBody RecipeDto recipeDto) {
        Recipe update = this.recipeDtoToRecipeConverter.convert(recipeDto);
        Recipe updatedRecipe = this.recipeService.update(recipeId, update);
        RecipeDto updatedRecipeDto = this.recipeToRecipeDtoConverter.convert(updatedRecipe);
        return new Result(true, StatusCode.SUCCESS, "Recipe edited successfully", updatedRecipeDto);
    }

    @PutMapping("/{newStatus}/{recipeId}")
    public Result changeRecipeStatus(@PathVariable String newStatus, @PathVariable Integer recipeId) {
        this.recipeService.changeRecipeStatus(recipeId, newStatus);
        return new Result(true, StatusCode.SUCCESS, "Recipe status changed successfully");
    }

    @GetMapping("nutrition-user/{nutritionUserId}")
    public Result findRecipesByUserId(@PathVariable Integer nutritionUserId) {
        List<Recipe> foundRecipes = recipeService.findRecipesByUserId(nutritionUserId);
        List<RecipeDto> recipeDtos = this.recipeToRecipeDtoConverter.convertList(foundRecipes);
        return new Result(true, StatusCode.SUCCESS, "Find recipe by user id successful.", recipeDtos);
    }

}


