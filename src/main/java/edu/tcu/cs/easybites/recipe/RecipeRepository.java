package edu.tcu.cs.easybites.recipe;

import edu.tcu.cs.easybites.allergen.Allergen;
import edu.tcu.cs.easybites.appliance.Appliance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Integer> {
//    List<Recipe> findAllByRecipeOwner_NutritionUserId(Integer nutritionOwnerId);
    List<Recipe> findAllByRecipeOwner_NutritionUserId(Integer nutritionUserId);
    List<Recipe> findAllByStatus(String status);

    @Query("SELECT DISTINCT r FROM Recipe r " +
            "LEFT JOIN r.appliances ra " +
            "LEFT JOIN r.allergens al " +
            "WHERE r.status = 'approved' " +
            "AND (ra IN :userAppliances OR ra IS NULL) " +
            "AND NOT EXISTS (SELECT ua FROM Allergen ua WHERE ua IN :userAllergens AND ua MEMBER OF r.allergens)")
    List<Recipe> findApprovedRecipesByUserAppliancesAndNotAllergens(
            @Param("userAppliances") List<Appliance> userAppliances,
            @Param("userAllergens") List<Allergen> userAllergens);
}
