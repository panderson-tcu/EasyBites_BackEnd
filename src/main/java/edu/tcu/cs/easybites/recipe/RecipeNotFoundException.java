package edu.tcu.cs.easybites.recipe;

public class RecipeNotFoundException extends RuntimeException {

    public RecipeNotFoundException(Integer recipeId) {
        super("Could not find recipe with ID " + recipeId + ".");
    }
}
