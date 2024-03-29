package edu.tcu.cs.easybites.recipe;

import edu.tcu.cs.easybites.allergen.Allergen;
import edu.tcu.cs.easybites.appliance.Appliance;
import edu.tcu.cs.easybites.appuser.AppUser;
import edu.tcu.cs.easybites.nutritionuser.NutritionUser;
import edu.tcu.cs.easybites.protein.Protein;
import edu.tcu.cs.easybites.ingredient.Ingredient;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Recipe implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer recipeId;

    private String title;

    private Integer cookTime;
    private String imageUrl;
    @Column(columnDefinition = "TEXT")
    private String ingredientsQuantity;

    private Double estimatedCost;

    @Column(columnDefinition = "TEXT")
    private String instructions;

    @NotNull
    private Integer servings;

    private String status;

    // foreign key to protein's PK (one to one)
    @ManyToOne
    @JoinColumn(name = "protein_id")
    private Protein protein;

    // foreign key to nutriton_user's PK (one to one)
    @ManyToOne
    @JoinColumn(name = "recipe_owner_id")
    private NutritionUser recipeOwner;


    @ManyToMany
    @JoinTable(
            name="recipe_ingredients",
            joinColumns = @JoinColumn(name="recipe_id"),
            inverseJoinColumns = @JoinColumn(name="ingredient_id")
    )
    private List<Ingredient> ingredients = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name="recipe_appliances",
            joinColumns = @JoinColumn(name="recipe_id"),
            inverseJoinColumns = @JoinColumn(name="appliance_id")
    )
    private List<Appliance> appliances = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name="recipe_allergens",
            joinColumns = @JoinColumn(name="recipe_id"),
            inverseJoinColumns = @JoinColumn(name="allergen_id")
    )
    private List<Allergen> allergens = new ArrayList<>();

    @ManyToMany(mappedBy="recipes")
    private List<AppUser> appUsers = new ArrayList<>();

    @ManyToMany(mappedBy="shoppingCart")
    private List<AppUser> shoppingCart = new ArrayList<>();

    public Recipe() {
    }

    public Integer getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(Integer recipeId) {
        this.recipeId = recipeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getCookTime() {
        return cookTime;
    }

    public void setCookTime(Integer cookTime) {
        this.cookTime = cookTime;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getIngredientsQuantity() {
        return ingredientsQuantity;
    }

    public void setIngredientsQuantity(String ingredientsQuantity) {
        this.ingredientsQuantity = ingredientsQuantity;
    }

    public Double getEstimatedCost() {
        return estimatedCost;
    }

    public void setEstimatedCost(Double estimatedCost) {
        this.estimatedCost = estimatedCost;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public Integer getServings() {
        return servings;
    }

    public void setServings(Integer servings) {
        this.servings = servings;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Protein getProtein() {
        return protein;
    }

    public void setProtein(Protein protein) {
        this.protein = protein;
    }

    public NutritionUser getRecipeOwner() {
        return recipeOwner;
    }

    public void setRecipeOwner(NutritionUser recipeOwner) {
        this.recipeOwner = recipeOwner;
//        recipeOwner.addRecipe(this);
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
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

    public List<AppUser> getAppUsers() {
        return appUsers;
    }

    public void setAppUsers(List<AppUser> appUsers) {
        this.appUsers = appUsers;
    }

    public List<AppUser> getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(List<AppUser> shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public void addIngredient(Ingredient ingredient) {
        this.ingredients.add(ingredient);
        ingredient.addRecipe(this);
    }

    public void addAppliance(Appliance appliance) {
        this.appliances.add(appliance);
        appliance.addRecipe(this);
    }

    public void addAllergen(Allergen allergen) {
        this.allergens.add(allergen);
        allergen.addRecipe(this);
    }

}
