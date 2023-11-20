package edu.tcu.cs.easybites.recipe;

import edu.tcu.cs.easybites.allergen.Allergen;
import edu.tcu.cs.easybites.appliance.Appliance;
import edu.tcu.cs.easybites.ingredient.Ingredient;
import edu.tcu.cs.easybites.nutritionuser.NutritionUser;
import edu.tcu.cs.easybites.protein.Protein;
import edu.tcu.cs.easybites.system.StatusCode;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class RecipeControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    RecipeService recipeService;

    List<Recipe> recipes;

    @BeforeEach
    void setUp() {
        this.recipes = new ArrayList<>();

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
    void testFindRecipeByIdSuccess() throws Exception{
        // Given
        given(this.recipeService.findById(110405679)).willReturn(this.recipes.get(0));

        // When and then
        this.mockMvc.perform(get("/recipe/110405679").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("View recipe by id successful"))
                .andExpect(jsonPath("$.data.recipeId").value(110405679))
                .andExpect(jsonPath("$.data.title").value("Enchilada Casserole"));
    }

    @Test
    void testFindRecipeByIdNotFound() throws Exception {
        // Given
        given(this.recipeService.findById(110405679)).willThrow(new RecipeNotFoundException(110405679));

        // When and then
        this.mockMvc.perform(get("/recipe/110405679").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Could not find recipe with ID 110405679."))
                .andExpect(jsonPath("$.data").isEmpty());

    }

    @Test
    void testFindAllSuccess() throws Exception {

        // given
        given(recipeService.findAll()).willReturn(this.recipes);

        // when and then
        this.mockMvc.perform(get("/recipes").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("View all recipes successful"))
                .andExpect(jsonPath("$.data").value(Matchers.hasSize(this.recipes.size())))
                .andExpect(jsonPath("$.data[0].recipeId").value(110405679))
                .andExpect(jsonPath("$.data[0].title").value("Enchilada Casserole"))
                .andExpect(jsonPath("$.data[1].recipeId").value(110405680))
                .andExpect(jsonPath("$.data[1].title").value("Vegetarian Pasta"));


    }

}