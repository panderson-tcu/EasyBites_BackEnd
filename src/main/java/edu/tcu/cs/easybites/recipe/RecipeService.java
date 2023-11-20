package edu.tcu.cs.easybites.recipe;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class RecipeService {
    private final RecipeRepository recipeRepository;

//    private final IdWorker idWorker;

    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public Recipe findById(Integer recipeId) {
        return this.recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RecipeNotFoundException(recipeId));
    }

    public List<Recipe> findAll() {
        return this.recipeRepository.findAll();
    }

    public Recipe save(Recipe newRecipe) {
//        newRecipe.setRecipeId(idWorker.nextId() + "");

        return this.recipeRepository.save(newRecipe);
    }
}
