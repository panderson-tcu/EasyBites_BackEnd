package edu.tcu.cs.easybites.nutritionuser;

import edu.tcu.cs.easybites.recipe.Recipe;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotEmpty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class NutritionUser implements Serializable {
    @Id
    private Integer nutritionUserId;
    @NotEmpty(message = "first name is required.")
    private String firstName;
    @NotEmpty(message = "last name is required.")
    private String lastName;
    @NotEmpty(message = "admin level is required.")
    private String adminLevel;
    @NotEmpty(message = "email  is required.")
    private String email;

    private boolean enabled;
    @NotEmpty(message = "password is required.")
    private String password;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "recipeOwner")
    private List<Recipe> recipes = new ArrayList<>();

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void addRecipe(Recipe recipe) {
        recipe.setRecipeOwner(this);
        this.recipes.add(recipe);
    }

}
