package edu.tcu.cs.easybites.recipe;

import edu.tcu.cs.easybites.allergen.Allergen;
import edu.tcu.cs.easybites.appliance.Appliance;
import edu.tcu.cs.easybites.appuser.AppUser;
import edu.tcu.cs.easybites.appuser.AppUserRepository;
import edu.tcu.cs.easybites.ingredient.Ingredient;
import edu.tcu.cs.easybites.ingredient.IngredientRepository;
import edu.tcu.cs.easybites.system.exception.ObjectNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;

    private final AppUserRepository appUserRepository;

    public RecipeService(RecipeRepository recipeRepository,
                         IngredientRepository ingredientRepository,
                         AppUserRepository appUserRepository) {
        this.recipeRepository = recipeRepository;
        this.ingredientRepository = ingredientRepository;
        this.appUserRepository = appUserRepository;
    }

    public Recipe findById(Integer recipeId) {
        return this.recipeRepository.findById(recipeId)
                .orElseThrow(() -> new ObjectNotFoundException("recipe", recipeId));
    }

    public List<Recipe> findAll() {
        return this.recipeRepository.findAll();
    }

    public List<Recipe> findApprovedRecipes() {
        return this.recipeRepository.findAllByStatus("approved");
    }

    public Recipe save(Recipe newRecipe) {
        // save ingredient to ingredient table if not exist
        List<Ingredient> ingredients = newRecipe.getIngredients();
        ingredients.removeIf(ingredient -> ingredient.getUpcValue() == null || ingredient.getUpcValue().isEmpty());

        for (Ingredient ingredientToAdd : ingredients) {
            Optional<Ingredient> existingIngredient = ingredientRepository.findById(ingredientToAdd.getUpcValue());

            if(existingIngredient.isEmpty()) {
                Ingredient newIngredient = new Ingredient();
                newIngredient.setUpcValue(ingredientToAdd.getUpcValue());
                ingredientRepository.save(newIngredient);
            }
        }

//        proteinRepository.findById(newRecipe.getProtein().getProteinId())
//                .orElseThrow(() -> new ProteinNotFoundException(newRecipe.getProtein().getProteinId()));

        return this.recipeRepository.save(newRecipe);
    }

    public Recipe update(Integer recipeId, Recipe update) {
        List<Ingredient> ingredients = update.getIngredients();
        ingredients.removeIf(ingredient -> ingredient.getUpcValue() == null || ingredient.getUpcValue().isEmpty());

        for (Ingredient ingredientToAdd : ingredients) {
            Optional<Ingredient> existingIngredient = ingredientRepository.findById(ingredientToAdd.getUpcValue());
            if(existingIngredient.isEmpty()) {
                Ingredient newIngredient = new Ingredient();
                newIngredient.setUpcValue(ingredientToAdd.getUpcValue());
                ingredientRepository.save(newIngredient);
            }
        }
        return this.recipeRepository.findById(recipeId)
                .map(oldRecipe -> {
                    oldRecipe.setTitle(update.getTitle());
                    oldRecipe.setCookTime(update.getCookTime());
                    oldRecipe.setIngredientsQuantity(update.getIngredientsQuantity());
                    oldRecipe.setEstimatedCost(update.getEstimatedCost());
                    oldRecipe.setInstructions(update.getInstructions());
                    oldRecipe.setServings(update.getServings());
                    oldRecipe.setStatus("pending");
                    oldRecipe.setProtein(update.getProtein());
                    oldRecipe.setIngredients(ingredients);
                    oldRecipe.setAppliances(update.getAppliances());
                    oldRecipe.setAllergens(update.getAllergens());
                    oldRecipe.setAppUsers(update.getAppUsers());
                    return this.recipeRepository.save(oldRecipe);
                })
                .orElseThrow(() -> new ObjectNotFoundException("recipe", recipeId));

    }

    public void changeRecipeStatus(Integer recipeId, String newStatus) {
        this.recipeRepository.findById(recipeId)
                .map(oldRecipe -> {
                    oldRecipe.setStatus(newStatus);
                    return this.recipeRepository.save(oldRecipe);
                })
                .orElseThrow(() -> new ObjectNotFoundException("recipe", recipeId));
    }

    public List<Recipe> findRecipesByUserId(Integer nutritionUserId) {
        return this.recipeRepository.findAllByRecipeOwner_NutritionUserId(nutritionUserId);
    }

    public Recipe likeRecipe(Integer recipeId, String userId) {
        Optional<Recipe> foundRecipeOptional = recipeRepository.findById(recipeId);
        Optional<AppUser> newAppUserOptional = appUserRepository.findByUserId(userId);

        if (newAppUserOptional.isPresent() && foundRecipeOptional.isPresent()) {
            AppUser newAppUser = newAppUserOptional.get();
            Recipe foundRecipe = foundRecipeOptional.get();

            List<AppUser> oldAppUsers = foundRecipe.getAppUsers();
            List<Recipe> userRecipes = newAppUser.getRecipes();

            oldAppUsers.add(newAppUser);
            userRecipes.add(foundRecipe);

            foundRecipe.setAppUsers(oldAppUsers);
            newAppUser.setRecipes(userRecipes);

            return this.recipeRepository.save(foundRecipe);
        }
        else {
            throw new ObjectNotFoundException("user", userId);
        }
    }

    public Recipe removeLikedRecipe(Integer recipeId, String userId) {
        Recipe foundRecipe = this.findById(recipeId);
        AppUser foundAppUser = this.appUserRepository.findByUserId(userId)
                .orElseThrow(() -> new ObjectNotFoundException("app user", userId));

        List<Recipe> likedRecipes = foundAppUser.getRecipes();
        List<AppUser> recipeUsers = foundRecipe.getAppUsers();

        likedRecipes.remove(foundRecipe);
        recipeUsers.remove(foundAppUser);

        this.appUserRepository.save(foundAppUser);
        return this.recipeRepository.save(foundRecipe);
    }

    public Recipe addToShoppingCart(Integer recipeId, String userId) {
        Optional<Recipe> foundRecipeOptional = recipeRepository.findById(recipeId);
        Optional<AppUser> newAppUserOptional = appUserRepository.findByUserId(userId);

        if (newAppUserOptional.isPresent() && foundRecipeOptional.isPresent()) {
            AppUser newAppUser = newAppUserOptional.get();
            Recipe foundRecipe = foundRecipeOptional.get();

            List<AppUser> oldAppUsers = foundRecipe.getShoppingCart();
            List<Recipe> userShoppingCart = newAppUser.getShoppingCart();

            oldAppUsers.add(newAppUser);
            userShoppingCart.add(foundRecipe);

            foundRecipe.setShoppingCart(oldAppUsers);
            newAppUser.setShoppingCart(userShoppingCart);

            return this.recipeRepository.save(foundRecipe);
        }
        else {
            throw new ObjectNotFoundException("user", userId);
        }
    }

    public Recipe removeShoppingCart(Integer recipeId, String userId) {
        Recipe foundRecipe = this.findById(recipeId);
        AppUser foundAppUser = this.appUserRepository.findByUserId(userId)
                .orElseThrow(() -> new ObjectNotFoundException("app user", userId));

        List<Recipe> likedRecipes = foundAppUser.getShoppingCart();
        List<AppUser> recipeUsers = foundRecipe.getShoppingCart();

        likedRecipes.remove(foundRecipe);
        recipeUsers.remove(foundAppUser);

        this.appUserRepository.save(foundAppUser);
        return this.recipeRepository.save(foundRecipe);
    }

    public List<Recipe> findFilteredRecipes(String userId) {
        AppUser foundUser = this.appUserRepository.findByUserId(userId).orElseThrow(() -> new ObjectNotFoundException("user", userId));
        List<Allergen> allergens = foundUser.getAllergens();
        List<Appliance> appliances = foundUser.getAppliances();
        return this.recipeRepository.findApprovedRecipesByUserAppliancesAndNotAllergens(appliances, allergens);
    }
}
