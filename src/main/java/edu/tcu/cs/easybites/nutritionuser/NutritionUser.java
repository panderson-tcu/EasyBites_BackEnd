package edu.tcu.cs.easybites.nutritionuser;

import edu.tcu.cs.easybites.recipe.Recipe;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class NutritionUser implements Serializable {
    private String firstName;
    private String lastName;
    private String adminLevel;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "recipeOwner")
    private List<Recipe> recipes = new ArrayList<>();

    @Id
    private Integer nutritionUserId;


    public NutritionUser() {

    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAdminLevel() {
        return adminLevel;
    }

    public void setAdminLevel(String adminLevel) {
        this.adminLevel = adminLevel;
    }

    public Integer getNutritionUserId() {
        return nutritionUserId;
    }

    public void setNutritionUserId(Integer nutritionUserId) {
        this.nutritionUserId = nutritionUserId;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    public void addRecipe(Recipe recipe) {
        recipe.setRecipeOwner(this);
        this.recipes.add(recipe);
    }

}
