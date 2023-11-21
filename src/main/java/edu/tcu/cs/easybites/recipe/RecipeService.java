package edu.tcu.cs.easybites.recipe;

import edu.tcu.cs.easybites.ingredient.Ingredient;
import edu.tcu.cs.easybites.ingredient.IngredientRepository;
import edu.tcu.cs.easybites.protein.Protein;
import edu.tcu.cs.easybites.protein.ProteinRepository;
import edu.tcu.cs.easybites.protein.utils.ProteinNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;
    private final ProteinRepository proteinRepository;

//    private final IdWorker idWorker;


    public RecipeService(RecipeRepository recipeRepository, IngredientRepository ingredientRepository, ProteinRepository proteinRepository) {
        this.recipeRepository = recipeRepository;
        this.ingredientRepository = ingredientRepository;
        this.proteinRepository = proteinRepository;
    }

    public Recipe findById(Integer recipeId) {
        return this.recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RecipeNotFoundException(recipeId));
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
}
