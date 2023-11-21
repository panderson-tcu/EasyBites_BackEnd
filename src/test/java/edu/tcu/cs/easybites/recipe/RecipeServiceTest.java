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

    List<Recipe> recipes;

    @BeforeEach
    void setUp() {
        recipes = new ArrayList<>();

        Allergen allergen = new Allergen();
        allergen.setAllergenId(123);
        List<Allergen> allergenList = new ArrayList<>();
        allergenList.add(allergen);


        // Create Protein
        Protein chicken = new Protein();
        chicken.setProteinId(12345);

        // Create NutritionUser
        NutritionUser nutritionUser = new NutritionUser();
        nutritionUser.setNutritionUserId(110409760);

        // Create Ingredient
        Ingredient ingredient = new Ingredient();
        ingredient.setUpcValue("1234567890");
        List<Ingredient> ingredientList = new ArrayList<>();
        ingredientList.add(ingredient);

        // Create Appliance
        Appliance appliance = new Appliance();
        appliance.setApplianceId(987654321);
        List<Appliance> applianceList = new ArrayList<>();
        applianceList.add(appliance);

        // Create Recipe 1
        Recipe recipe1 = new Recipe();
        recipe1.setRecipeId(110405679);
        recipe1.setTitle("Enchilada Casserole");
        recipe1.setCookTime(10);
        recipe1.setIngredientsQuantity("5 oz Red Enchilada Sauce\n8 oz Ground Turkey\n");
        recipe1.setEstimatedCost(5.50);
        recipe1.setInstructions("Heat oven to 400F.\nIn a pan over medium high heat...");
        recipe1.setServings(2);
        recipe1.setProtein(chicken);
        recipe1.setRecipeOwner(nutritionUser);
        recipe1.setIngredients(ingredientList);
        recipe1.setAppliances(applianceList);
        recipe1.setAllergens(allergenList);
        recipes.add(recipe1);

        // Create Recipe 2
        Recipe recipe2 = new Recipe();
        recipe2.setRecipeId(110405680);
        recipe2.setTitle("Vegetarian Pasta");
        recipe2.setCookTime(15);
        recipe2.setIngredientsQuantity("8 oz Pasta\n1 cup Cherry Tomatoes\n");
        recipe2.setEstimatedCost(3.50);
        recipe2.setInstructions("Boil pasta according to package instructions.\nCut cherry tomatoes in half...");
        recipe2.setServings(4);
        recipes.add(recipe2);

        // Set different Protein, NutritionUser, Ingredients, and Appliances as needed for Recipe 2

        // Create Recipe 3
        Recipe recipe3 = new Recipe();
        recipe3.setRecipeId(110405681);
        recipe3.setTitle("Grilled Salmon");
        recipe3.setCookTime(12);
        recipe3.setIngredientsQuantity("1 lb Salmon fillets\n1 lemon\n");
        recipe3.setEstimatedCost(8.00);
        recipe3.setInstructions("Preheat grill.\nSeason salmon with salt and pepper.\nGrill for 6 minutes per side...");
        recipe3.setServings(3);
        recipes.add(recipe3);

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

    @Test
    void testFindAllSuccess() {
        // given
        given(recipeRepository.findAll()).willReturn(this.recipes);

        // when
        List<Recipe> actualRecipes = recipeService.findAll();

        // then
        assertThat(actualRecipes.size()).isEqualTo(this.recipes.size());
        verify(recipeRepository, times(1)).findAll();

    }

    @Test
    void testSaveSuccess() {
        //given
        Allergen a = new Allergen();
        a.setAllergenId(123);
        List<Allergen> list = new ArrayList<>();
        list.add(a);

        Recipe newRecipe = new Recipe();
        newRecipe.setTitle("Recipe 1");
        newRecipe.setInstructions("preheat oven, chop onions");
        newRecipe.addAllergen(a);

        given(recipeRepository.save(newRecipe)).willReturn(newRecipe);

        //when
        Recipe savedRecipe = recipeService.save(newRecipe);

        //then
        assertThat(savedRecipe.getRecipeId()).isEqualTo(newRecipe.getRecipeId());
        assertThat(savedRecipe.getTitle()).isEqualTo("Recipe 1");
        assertThat(savedRecipe.getAllergens()).isEqualTo(list);
        assertThat(savedRecipe.getInstructions()).isEqualTo(newRecipe.getInstructions());
        verify(recipeRepository, times(1)).save(newRecipe);

    }

    @Test
    void testUpdateSuccess() {
        // given
        //given
        Allergen a = new Allergen();
        a.setAllergenId(123);
        List<Allergen> list = new ArrayList<>();
        list.add(a);

        Recipe oldRecipe = new Recipe();
        oldRecipe.setRecipeId(110405679);
        oldRecipe.setTitle("Recipe 1");
        oldRecipe.setInstructions("preheat oven, chop onions");
        oldRecipe.setIngredientsQuantity("old ingredients");
        oldRecipe.addAllergen(a);

        Recipe update = new Recipe();
        update.setRecipeId(110405679);
        update.setTitle("Recipe 1");
        update.setInstructions("new instructions");
        update.setIngredientsQuantity("new ingredients");
        update.addAllergen(a);




        given(recipeRepository.findById(110405679)).willReturn(Optional.of(update));
        given(recipeService.save(update)).willReturn(update);

        // when
        Recipe updatedRecipe = recipeService.update(110405679, update);

        // then
        assertThat(updatedRecipe.getRecipeId()).isEqualTo(110405679);
        assertThat(updatedRecipe.getIngredientsQuantity()).isEqualTo(update.getIngredientsQuantity());
        assertThat(updatedRecipe.getInstructions()).isEqualTo(update.getInstructions());
        verify(recipeRepository, times(1)).findById(110405679);
        verify(recipeRepository, times(1)).save(update);

    }

}