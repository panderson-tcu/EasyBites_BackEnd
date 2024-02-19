package edu.tcu.cs.easybites.recipe;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.tcu.cs.easybites.allergen.Allergen;
import edu.tcu.cs.easybites.allergen.dto.AllergenDto;
import edu.tcu.cs.easybites.appliance.Appliance;
import edu.tcu.cs.easybites.appliance.dto.ApplianceDto;
import edu.tcu.cs.easybites.ingredient.Ingredient;
import edu.tcu.cs.easybites.ingredient.dto.IngredientDto;
import edu.tcu.cs.easybites.nutritionuser.NutritionUser;
import edu.tcu.cs.easybites.nutritionuser.converter.NutritionUserToNutritionUserDtoConverter;
import edu.tcu.cs.easybites.nutritionuser.dto.NutritionUserDto;
import edu.tcu.cs.easybites.protein.Protein;
import edu.tcu.cs.easybites.protein.ProteinRepository;
import edu.tcu.cs.easybites.protein.converter.ProteinToProteinDtoConverter;
import edu.tcu.cs.easybites.protein.dto.ProteinDto;
import edu.tcu.cs.easybites.recipe.converter.RecipeDtoToRecipeConverter;
import edu.tcu.cs.easybites.recipe.dto.RecipeDto;
import edu.tcu.cs.easybites.system.StatusCode;
import netscape.javascript.JSObject;
import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Integration tests for Recipe API endpoints")
@Tag("integration")
@ActiveProfiles(value = "dev") // uses the specified profile to run this class
public class RecipeControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Value("${api.endpoint.base-url}")
    String baseUrl;

    @Autowired
    ObjectMapper objectMapper;

    String token;


    @BeforeEach
    void setUp() throws Exception {
        //this method gets called before every test case.

        // use mockMvc to simulate a login
        ResultActions resultActions = this.mockMvc
                .perform(post(this.baseUrl + "/nutrition-user/login")
                        .with(httpBasic("f.alarcon@tcu.edu", "password")));
        MvcResult mvcResult = resultActions.andDo(print()).andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString(); // get json response
        JSONObject json = new JSONObject(contentAsString);
        this.token = "Bearer " + json.getJSONObject("data").getString("token"); //save jwt into token
    }
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    void testFindAllRecipesSuccess() throws Exception {
        this.mockMvc.perform(get(this.baseUrl + "/recipes").header("Authorization", this.token).accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("View all recipes successful"))
                .andExpect(jsonPath("$.data", Matchers.hasSize(3)));
    }

    @Test
    @DisplayName("Check addRecipe with valid input (POST)")
    void testAddRecipeSuccess() throws Exception {
        // given
        Protein chicken = new Protein();
        chicken.setProteinId(1000);
        ProteinToProteinDtoConverter proteinToProteinDtoConverter = new ProteinToProteinDtoConverter();
        ProteinDto chickenDto = proteinToProteinDtoConverter.convert(chicken);


        NutritionUser nutritionUser = new NutritionUser();
        nutritionUser.setNutritionUserId(110409760);
        NutritionUserToNutritionUserDtoConverter nutritionUserToNutritionUserDtoConverter = new NutritionUserToNutritionUserDtoConverter();
        NutritionUserDto nutritionUserDto = nutritionUserToNutritionUserDtoConverter.convert(nutritionUser);

        List<IngredientDto> ingredientDtoList = new ArrayList<>();
        IngredientDto ingredientDto = new IngredientDto("12345");
        ingredientDtoList.add(ingredientDto);

        List<ApplianceDto> applianceList = new ArrayList<>();
        ApplianceDto applianceDto = new ApplianceDto(3000, "Oven");
        applianceList.add(applianceDto);

        AllergenDto allergenDto = new AllergenDto(2000, "Milk");
        List<AllergenDto> allergenDtos = new ArrayList<>();
        allergenDtos.add(allergenDto);


        RecipeDto recipeDto = new RecipeDto(null,
                "Recipe 1",
                5,
                "https://easybitesblobstorage.blob.core.windows.net/recipephotos/1-tofu-pad-thai.png",
                "1 cup of cheese...",
                5.50,
                "Preheat oven...",
                5,
                null,
                chickenDto,
                nutritionUserDto,
                ingredientDtoList,
                applianceList,
                allergenDtos,
                null);

        Recipe savedRecipe = new Recipe();
        savedRecipe.setRecipeId(123456);
        savedRecipe.setTitle("Recipe 1");
        savedRecipe.setIngredientsQuantity("1 cup of cheese...");
        savedRecipe.setEstimatedCost(5.50);
        savedRecipe.setInstructions("Preheat oven...");
        savedRecipe.setServings(5);
        savedRecipe.setProtein(chicken);

        String json = this.objectMapper.writeValueAsString(recipeDto);
        System.out.println(json);

        this.mockMvc.perform(post(this.baseUrl + "/recipes").header("Authorization", this.token).contentType(MediaType.APPLICATION_JSON).content(json).accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Recipe submitted successfully"))
                .andExpect(jsonPath("$.data.recipeId").isNotEmpty())
                .andExpect(jsonPath("$.data.title").value(savedRecipe.getTitle()))
                .andExpect(jsonPath("$.data.ingredientsQuantity").value(savedRecipe.getIngredientsQuantity()));
        this.mockMvc.perform(get(this.baseUrl + "/recipes").header("Authorization", this.token).accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("View all recipes successful"))
                .andExpect(jsonPath("$.data", Matchers.hasSize(4)));

    }
}
