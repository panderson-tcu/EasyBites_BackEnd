package edu.tcu.cs.easybites.recipe;

import edu.tcu.cs.easybites.nutritionuser.NutritionUser;
import edu.tcu.cs.easybites.protein.Protein;
import jakarta.persistence.*;

import java.io.Serializable;

@Entity
public class Recipe implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer recipeId;

    private String title;

    private Integer cookTime;

    private String ingredientsQuantity;

    private Double estimatedCost;

    private String instructions;

    private Integer servings;

    private String status;

    // foreign key to protein's PK (one to one)
    @ManyToOne
    private Protein protein;

    // foreign key to nutriton_user's PK (one to one)
    @ManyToOne
    private NutritionUser recipeOwner;

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
    }
}
