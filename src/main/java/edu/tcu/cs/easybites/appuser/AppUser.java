package edu.tcu.cs.easybites.appuser;

import edu.tcu.cs.easybites.allergen.Allergen;
import edu.tcu.cs.easybites.appliance.Appliance;
import edu.tcu.cs.easybites.recipe.Recipe;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class AppUser implements Serializable {
    @Id //add generated value annotation to generate ID?
//    @GeneratedValue(strategy = GenerationType.AUTO)
    private String userId;
    private String email;
    private String firstName;
    private String lastName;

    @ManyToMany
    @JoinTable(
            name="user_appliances",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name="appliance_id")
    )
    private List<Appliance> appliances = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name="user_allergens",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name="allergen_id")
    )
    private List<Allergen> allergens = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name="user_likes",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name="recipe_id")
    )
    private List<Recipe> recipes = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name="shopping_cart",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name="recipe_id")
    )
    private List<Recipe> shoppingCart = new ArrayList<>();
    public AppUser() {

    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public List<Appliance> getAppliances() {
        return appliances;
    }

    public void setAppliances(List<Appliance> appliances) {
        this.appliances = appliances;
    }

    public List<Allergen> getAllergens() {
        return allergens;
    }

    public void setAllergens(List<Allergen> allergens) {
        this.allergens = allergens;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    public List<Recipe> getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(List<Recipe> shoppingCart) {
        this.shoppingCart = shoppingCart;
    }
}
