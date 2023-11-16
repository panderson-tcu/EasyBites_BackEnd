package edu.tcu.cs.easybites.ingredient;

import edu.tcu.cs.easybites.recipe.Recipe;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Ingredient implements Serializable {
    @Id
    private String upcValue;

    @ManyToMany(mappedBy="ingredients")
    private List<Recipe> recipes = new ArrayList<>();

    public Ingredient() {
    }

    public String getUpcValue() {
        return upcValue;
    }

    public void setUpcValue(String upcValue) {
        this.upcValue = upcValue;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }
}
