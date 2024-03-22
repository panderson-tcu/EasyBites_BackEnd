package edu.tcu.cs.easybites.recipe;

import edu.tcu.cs.easybites.appuser.AppUser;
import edu.tcu.cs.easybites.appuser.dto.AppUserDto;
import edu.tcu.cs.easybites.recipe.converter.RecipeDtoToRecipeConverter;
import edu.tcu.cs.easybites.recipe.converter.RecipeToApprovedRecipeDtoConverter;
import edu.tcu.cs.easybites.recipe.converter.RecipeToRecipeDtoConverter;
import edu.tcu.cs.easybites.recipe.dto.ApprovedRecipeDto;
import edu.tcu.cs.easybites.recipe.dto.RecipeDto;
import edu.tcu.cs.easybites.system.Result;
import edu.tcu.cs.easybites.system.StatusCode;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("${api.endpoint.base-url}/recipes")
@CrossOrigin(origins = "https://easy-bites-tcu.vercel.app") // PROD ONLY: this is the URL for the EasyBites front end server. Here we allow CORS.**/
//@CrossOrigin(origins = "http://localhost:3000") // FOR DEV ONLY
public class RecipeController {
    private final RecipeService recipeService;
    private final RecipeToRecipeDtoConverter recipeToRecipeDtoConverter;
    private final RecipeDtoToRecipeConverter recipeDtoToRecipeConverter;
    private final RecipeToApprovedRecipeDtoConverter recipeToApprovedRecipeDtoConverter;

    public RecipeController(RecipeService recipeService, RecipeToRecipeDtoConverter recipeToRecipeDtoConverter, RecipeDtoToRecipeConverter recipeDtoToRecipeConverter, RecipeToApprovedRecipeDtoConverter recipeToApprovedRecipeDtoConverter) {
        this.recipeService = recipeService;
        this.recipeToRecipeDtoConverter = recipeToRecipeDtoConverter;
        this.recipeDtoToRecipeConverter = recipeDtoToRecipeConverter;
        this.recipeToApprovedRecipeDtoConverter = recipeToApprovedRecipeDtoConverter;
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

    @GetMapping("/approved")
    public Result findApprovedRecipes() {
        List<Recipe> foundRecipes = this.recipeService.findApprovedRecipes();
        List<ApprovedRecipeDto> approvedRecipeDtos = recipeToApprovedRecipeDtoConverter.convertList(foundRecipes);
        return new Result(true, StatusCode.SUCCESS, "View Approved recipes successful", approvedRecipeDtos);
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

    //like a recipe
    @PatchMapping("like/{recipeId}/{userId}")
    public Result likeRecipe(@PathVariable Integer recipeId, @PathVariable String userId) {
        Recipe updatedRecipe = this.recipeService.likeRecipe(recipeId, userId);
        RecipeDto updatedRecipeDto = this.recipeToRecipeDtoConverter.convert(updatedRecipe);

        return new Result(true, StatusCode.SUCCESS, "Add liked recipe success", updatedRecipeDto);
    }

    // remove liked recipe
    @PatchMapping("removeLike/{recipeId}/{userId}")
    public Result removeLike(@PathVariable Integer recipeId, @PathVariable String userId) {
        Recipe updatedRecipe = this.recipeService.removeLikedRecipe(recipeId, userId);
        RecipeDto updatedRecipeDto = this.recipeToRecipeDtoConverter.convert(updatedRecipe);

        return new Result(true, StatusCode.SUCCESS, "remove liked recipe success", updatedRecipeDto);
    }

    // add recipe to shopping cart
    @PatchMapping("shoppingCart/{recipeId}/{userId}")
    public Result addToShoppingCart(@PathVariable Integer recipeId, @PathVariable String userId) {
        Recipe updatedRecipe = this.recipeService.addToShoppingCart(recipeId, userId);
        RecipeDto updatedRecipeDto = this.recipeToRecipeDtoConverter.convert(updatedRecipe);

        return new Result(true, StatusCode.SUCCESS, "Add app user success", updatedRecipeDto);
    }

    // remove recipe from shopping cart
    @PatchMapping("removeShoppingCart/{recipeId}/{userId}")
    public Result removeShoppingCart(@PathVariable Integer recipeId, @PathVariable String userId) {
        Recipe updatedRecipe = this.recipeService.removeShoppingCart(recipeId, userId);
        RecipeDto updatedRecipeDto = this.recipeToRecipeDtoConverter.convert(updatedRecipe);

        return new Result(true, StatusCode.SUCCESS, "remove recipe from shopping cart success", updatedRecipeDto);
    }

}


