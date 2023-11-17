package edu.tcu.cs.easybites.recipe;

import edu.tcu.cs.easybites.allergen.Allergen;
import edu.tcu.cs.easybites.appliance.Appliance;
import edu.tcu.cs.easybites.ingredient.Ingredient;
import edu.tcu.cs.easybites.nutritionuser.NutritionUser;
import edu.tcu.cs.easybites.protein.Protein;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RecipeServiceTest {
    @Mock
    RecipeRepository recipeRepository;
    @InjectMocks
    RecipeService recipeService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testFindByIdSuccess() {
        // Given
        Allergen a = new Allergen();
        a.setAllergenId(123);
        List<Allergen> list = new ArrayList<>();
        list.add(a);

        Protein chicken = new Protein();
        chicken.setProteinId(12345);

        NutritionUser n = new NutritionUser();
        n.setNutritionUserId(110409760);

        Ingredient i = new Ingredient();
        i.setUpcValue("1234567890");
        List<Ingredient> ingredientList = new ArrayList<>();
        ingredientList.add(i);

        Appliance appliance = new Appliance();
        appliance.setApplianceId(987654321);
        List<Appliance> applianceList = new ArrayList<>();
        applianceList.add(appliance);

        Recipe r = new Recipe();
        r.setRecipeId(110405679);
        r.setTitle("Enchilada Casserole");
        r.setCookTime(10);
        r.setIngredientsQuantity("5 oz Red Enchilada Sauce\n8 oz Ground Turkey\n");
        r.setEstimatedCost(5.50);
        r.setInstructions("Heat oven to 400F.\nIn a pan over medium high heat...");
        r.setServings(2);
        r.setProtein(chicken);
        r.setRecipeOwner(n);
        r.setIngredients(ingredientList);
        r.setAppliances(applianceList);
        r.setAllergens(list);

        given(recipeRepository.findById(110405679)).willReturn(Optional.of(r));

        // When
        Recipe returnedRecipe = recipeService.findById(110405679);

        // Then
        assertThat(returnedRecipe.getRecipeId()).isEqualTo(r.getRecipeId());
        assertThat(returnedRecipe.getTitle()).isEqualTo(r.getTitle());
        assertThat(returnedRecipe.getCookTime()).isEqualTo(r.getCookTime());
        assertThat(returnedRecipe.getIngredientsQuantity()).isEqualTo(r.getIngredientsQuantity());
        assertThat(returnedRecipe.getEstimatedCost()).isEqualTo(r.getEstimatedCost());
        assertThat(returnedRecipe.getInstructions()).isEqualTo(r.getInstructions());
        assertThat(returnedRecipe.getServings()).isEqualTo(r.getServings());
        assertThat(returnedRecipe.getProtein()).isEqualTo(r.getProtein());
        assertThat(returnedRecipe.getIngredients()).isEqualTo(r.getIngredients());
        verify(recipeRepository, times(1)).findById(110405679);
    }

    @Test
    void testFindByIdNotFound() {
        //Given
        given(recipeRepository.findById(Mockito.any(Integer.class))).willReturn(Optional.empty());

        //When
        Throwable thrown = catchThrowable(() -> {
            Recipe returnedRecipe = recipeService.findById(110405679);
        });

        //Then
        assertThat(thrown)
                .isInstanceOf(RecipeNotFoundException.class)
                .hasMessage("Could not find recipe with ID 110405679.");
        verify(recipeRepository, times(1)).findById(110405679);
    }

}