package edu.tcu.cs.easybites.recipe;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Integer> {
//    List<Recipe> findAllByRecipeOwner_NutritionUserId(Integer nutritionOwnerId);
    List<Recipe> findAllByRecipeOwner_NutritionUserId(Integer nutritionUserId);
}
