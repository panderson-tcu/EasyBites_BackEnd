package edu.tcu.cs.easybites.recipe;

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

    public RecipeService(RecipeRepository recipeRepository, IngredientRepository ingredientRepository) {
        this.recipeRepository = recipeRepository;
        this.ingredientRepository = ingredientRepository;
    }

    public Recipe findById(Integer recipeId) {
        return this.recipeRepository.findById(recipeId)
                .orElseThrow(() -> new ObjectNotFoundException("recipe", recipeId));
    }

    public List<Recipe> findAll() {
        return this.recipeRepository.findAll();
    }

    public Recipe save(Recipe newRecipe) {
        // save ingredient to ingredient table if not exist
        List<Ingredient> ingredients = newRecipe.getIngredients();
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
                    oldRecipe.setIngredients(update.getIngredients());
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
}
