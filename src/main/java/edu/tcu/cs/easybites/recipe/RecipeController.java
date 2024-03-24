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

    /**
     * Find Recipe by ID endpoint
     * @param recipeId : id of recipe to find
     * @return Result
     */
    @GetMapping("/{recipeId}")
    public Result findRecipeById(@PathVariable Integer recipeId) {
        Recipe foundRecipe = this.recipeService.findById(recipeId);
        RecipeDto recipeDto = this.recipeToRecipeDtoConverter.convert(foundRecipe);
        return new Result(true, StatusCode.SUCCESS, "View recipe by id successful", recipeDto);
    }

    /**
     * Find all recipes endpoint
     * @return Result
     */
    @GetMapping
    public Result findAllRecipes() {
        List<Recipe> foundRecipes = this.recipeService.findAll();
        List<RecipeDto> recipeDtos = recipeToRecipeDtoConverter.convertList(foundRecipes);
        return new Result(true, StatusCode.SUCCESS, "View all recipes successful", recipeDtos);
    }

    /**
     * Find recipes with approved status (approved recipe DTO is a shorter version of a recipe)
     * This method is used in the Mobile App front end to only show App users the recipes that have been approved
     * in the Web portal.
     * @return Result
     */
    @GetMapping("/approved")
    public Result findApprovedRecipes() {
        List<Recipe> foundRecipes = this.recipeService.findApprovedRecipes();
        List<ApprovedRecipeDto> approvedRecipeDtos = recipeToApprovedRecipeDtoConverter.convertList(foundRecipes);
        return new Result(true, StatusCode.SUCCESS, "View Approved recipes successful", approvedRecipeDtos);
    }

    /**
     * Add Recipe to database endpoint
     * Recipe is automatically set to "pending" status
     * @param recipeDto : DTO (RecipeDto json) of the recipe to be added
     * @return Result
     */
    @PostMapping
    public Result addRecipe(@Valid @RequestBody RecipeDto recipeDto) {
        Recipe newRecipe = this.recipeDtoToRecipeConverter.convert(recipeDto);
        newRecipe.setStatus("pending");
        Recipe savedRecipe = this.recipeService.save(newRecipe);
        RecipeDto savedRecipeDto = recipeToRecipeDtoConverter.convert(savedRecipe);
        return new Result(true, StatusCode.SUCCESS, "Recipe submitted successfully", savedRecipeDto);
    }

    /**
     * Update a recipe by ID
     * @param recipeId : id of recipe to update
     * @param recipeDto : new json object with the new recipe
     * @return Result
     */
    @PutMapping("/{recipeId}")
    public Result updateRecipe(@PathVariable Integer recipeId, @Valid @RequestBody RecipeDto recipeDto) {
        Recipe update = this.recipeDtoToRecipeConverter.convert(recipeDto);
        Recipe updatedRecipe = this.recipeService.update(recipeId, update);
        RecipeDto updatedRecipeDto = this.recipeToRecipeDtoConverter.convert(updatedRecipe);
        return new Result(true, StatusCode.SUCCESS, "Recipe edited successfully", updatedRecipeDto);
    }

    /**
     * Change Recipe Status endpoint
     * This endpoint is used by the web portal to approve or deny submitted recipes
     * @param newStatus : string of new status. must be pending, approved, denied
     * @param recipeId : id of recipe to be changed
     * @return Result
     */
    @PutMapping("/{newStatus}/{recipeId}")
    public Result changeRecipeStatus(@PathVariable String newStatus, @PathVariable Integer recipeId) {
        this.recipeService.changeRecipeStatus(recipeId, newStatus);
        return new Result(true, StatusCode.SUCCESS, "Recipe status changed successfully");
    }

    /**
     * Get recipes submitted by a specific Nutrition User (Web portal)
     * @param nutritionUserId : id of nutrition user
     * @return Result
     */
    @GetMapping("nutrition-user/{nutritionUserId}")
    public Result findRecipesByUserId(@PathVariable Integer nutritionUserId) {
        List<Recipe> foundRecipes = recipeService.findRecipesByUserId(nutritionUserId);
        List<RecipeDto> recipeDtos = this.recipeToRecipeDtoConverter.convertList(foundRecipes);
        return new Result(true, StatusCode.SUCCESS, "Find recipe by user id successful.", recipeDtos);
    }

    /**
     * Add recipe to Liked Recipes (or "Favorite recipes" - user_likes table in DB) - App
     * @param recipeId : id of recipe
     * @param userId : id of app user
     * @return
     */
    @PatchMapping("like/{recipeId}/{userId}")
    public Result likeRecipe(@PathVariable Integer recipeId, @PathVariable String userId) {
        Recipe updatedRecipe = this.recipeService.likeRecipe(recipeId, userId);
        RecipeDto updatedRecipeDto = this.recipeToRecipeDtoConverter.convert(updatedRecipe);

        return new Result(true, StatusCode.SUCCESS, "Add liked recipe success", updatedRecipeDto);
    }

    /**
     * Remove a recipe from liked recipes - App
     * @param recipeId : recipe id
     * @param userId : app user id
     * @return Result
     */
    @PatchMapping("removeLike/{recipeId}/{userId}")
    public Result removeLike(@PathVariable Integer recipeId, @PathVariable String userId) {
        Recipe updatedRecipe = this.recipeService.removeLikedRecipe(recipeId, userId);
        RecipeDto updatedRecipeDto = this.recipeToRecipeDtoConverter.convert(updatedRecipe);

        return new Result(true, StatusCode.SUCCESS, "remove liked recipe success", updatedRecipeDto);
    }

    /**
     * Add a recipe to an App user's shopping cart
     * @param recipeId : recipe id
     * @param userId : app user id
     * @return Result
     */
    @PatchMapping("shoppingCart/{recipeId}/{userId}")
    public Result addToShoppingCart(@PathVariable Integer recipeId, @PathVariable String userId) {
        Recipe updatedRecipe = this.recipeService.addToShoppingCart(recipeId, userId);
        RecipeDto updatedRecipeDto = this.recipeToRecipeDtoConverter.convert(updatedRecipe);

        return new Result(true, StatusCode.SUCCESS, "Add app user success", updatedRecipeDto);
    }

    /**
     * Remove a recipe from an app user's shopping cart
     * @param recipeId : recipe id
     * @param userId : app user id
     * @return Result
     */
    @PatchMapping("removeShoppingCart/{recipeId}/{userId}")
    public Result removeShoppingCart(@PathVariable Integer recipeId, @PathVariable String userId) {
        Recipe updatedRecipe = this.recipeService.removeShoppingCart(recipeId, userId);
        RecipeDto updatedRecipeDto = this.recipeToRecipeDtoConverter.convert(updatedRecipe);

        return new Result(true, StatusCode.SUCCESS, "remove recipe from shopping cart success", updatedRecipeDto);
    }

}


