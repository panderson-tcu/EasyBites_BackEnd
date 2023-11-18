package edu.tcu.cs.easybites.appliance;

import edu.tcu.cs.easybites.appuser.AppUser;
import edu.tcu.cs.easybites.recipe.Recipe;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Appliance implements Serializable {

    @Id
    private Integer applianceId;

    private String name;

    @ManyToMany(mappedBy="appliances")
    private List<Recipe> recipes = new ArrayList<>();

    @ManyToMany(mappedBy="appliances")
    private List<AppUser> appUsers = new ArrayList<>();

    public Appliance() {
    }

    public Integer getApplianceId() {
        return applianceId;
    }

    public void setApplianceId(Integer applianceId) {
        this.applianceId = applianceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    public List<AppUser> getAppUsers() {
        return appUsers;
    }

    public void setAppUsers(List<AppUser> appUsers) {
        this.appUsers = appUsers;
    }

    public void addRecipe(Recipe recipe) {
        this.recipes.add(recipe);
    }
}
