package edu.tcu.cs.easybites.protein;

import edu.tcu.cs.easybites.appuser.AppUser;
import edu.tcu.cs.easybites.recipe.Recipe;
import edu.tcu.cs.easybites.userfilter.UserFilter;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Protein implements Serializable {
    private String proteinName;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "protein")
    private List<Recipe> recipes = new ArrayList<>();

    @Id
    private Integer proteinId;

    @ManyToMany(mappedBy="proteins")
    private List<UserFilter> userFilters = new ArrayList<>();

    public Protein() {

    }

    public String getProteinName() {
        return proteinName;
    }

    public void setProteinName(String proteinName) {
        this.proteinName = proteinName;
    }

    public Integer getProteinId() {
        return proteinId;
    }

    public void setProteinId(Integer proteinId) {
        this.proteinId = proteinId;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    public List<UserFilter> getUserFilters() {
        return userFilters;
    }

    public void setUserFilters(List<UserFilter> userFilters) {
        this.userFilters = userFilters;
    }

    public void addRecipe(Recipe recipe) {
        recipe.setProtein(this);
        this.recipes.add(recipe);
    }
}
