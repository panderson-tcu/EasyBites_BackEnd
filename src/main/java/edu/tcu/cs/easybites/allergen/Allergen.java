package edu.tcu.cs.easybites.allergen;

import edu.tcu.cs.easybites.appuser.AppUser;
import edu.tcu.cs.easybites.recipe.Recipe;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Allergen implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer allergenId;

    private String name;

    @ManyToMany(mappedBy="allergens")
    private List<Recipe> recipes = new ArrayList<>();

    @ManyToMany(mappedBy="allergens")
    private List<AppUser> appUsers = new ArrayList<>();

    public Allergen() {
    }

    public Integer getAllergenId() {
        return allergenId;
    }

    public void setAllergenId(Integer allergenId) {
        this.allergenId = allergenId;
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
}
