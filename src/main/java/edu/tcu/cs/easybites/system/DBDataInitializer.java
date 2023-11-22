package edu.tcu.cs.easybites.system;

import edu.tcu.cs.easybites.allergen.Allergen;
import edu.tcu.cs.easybites.allergen.AllergenRepository;
import edu.tcu.cs.easybites.appliance.Appliance;
import edu.tcu.cs.easybites.appliance.ApplianceRepository;
import edu.tcu.cs.easybites.ingredient.Ingredient;
import edu.tcu.cs.easybites.ingredient.IngredientRepository;
import edu.tcu.cs.easybites.nutritionuser.NutritionUser;
import edu.tcu.cs.easybites.nutritionuser.NutritionUserRepository;
import edu.tcu.cs.easybites.protein.Protein;
import edu.tcu.cs.easybites.protein.ProteinRepository;
import edu.tcu.cs.easybites.recipe.Recipe;
import edu.tcu.cs.easybites.recipe.RecipeRepository;
import edu.tcu.cs.easybites.recipe.RecipeService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DBDataInitializer implements CommandLineRunner {
    private final RecipeRepository recipeRepository;
    private final AllergenRepository allergenRepository;
    private final ProteinRepository proteinRepository;
    private final NutritionUserRepository nutritionUserRepository;
    private final IngredientRepository ingredientRepository;
    private final ApplianceRepository applianceRepository;

    public DBDataInitializer(RecipeRepository recipeRepository, AllergenRepository allergenRepository,
                             ProteinRepository proteinRepository, NutritionUserRepository nutritionUserRepository,
                             IngredientRepository ingredientRepository, ApplianceRepository applianceRepository) {
        this.recipeRepository = recipeRepository;
        this.allergenRepository = allergenRepository;
        this.proteinRepository = proteinRepository;
        this.nutritionUserRepository = nutritionUserRepository;
        this.ingredientRepository = ingredientRepository;
        this.applianceRepository = applianceRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Allergen milk = new Allergen();
        milk.setName("Milk");
        milk.setAllergenId(2000);

        Allergen peanuts = new Allergen();
        peanuts.setName("Peanuts");
        peanuts.setAllergenId(2002);

        allergenRepository.save(milk);
        allergenRepository.save(peanuts);


        // Create Protein
        Protein chicken = new Protein();
        chicken.setProteinName("Chicken");
        chicken.setProteinId(1000);

        Protein beef = new Protein();
        beef.setProteinName("Beef");
        beef.setProteinId(1001);

        proteinRepository.save(chicken);
        proteinRepository.save(beef);

        // Create NutritionUser
        NutritionUser nutritionUser = new NutritionUser();
        nutritionUser.setNutritionUserId(110409760);
        nutritionUser.setFirstName("Francisco");
        nutritionUser.setLastName("Alarcon");
        nutritionUser.setAdminLevel("student");
        nutritionUser.setEmail("f.alarcon@tcu.edu");
        nutritionUser.setPassword("password");


        NutritionUser nutritionUser2 = new NutritionUser();
        nutritionUser2.setNutritionUserId(110409762);
        nutritionUser2.setFirstName("Paige");
        nutritionUser2.setLastName("Anderson");
        nutritionUser2.setAdminLevel("admin");
        nutritionUser2.setEmail("paige.anderson@tcu.edu");
        nutritionUser2.setPassword("password");


        nutritionUserRepository.save(nutritionUser);
        nutritionUserRepository.save(nutritionUser2);

        // Create Ingredient
        Ingredient i1 = new Ingredient();
        i1.setUpcValue("5000");
        Ingredient i2 = new Ingredient();
        i2.setUpcValue("5001");
        Ingredient i3 = new Ingredient();
        i3.setUpcValue("5002");

        ingredientRepository.save(i1);
        ingredientRepository.save(i2);
        ingredientRepository.save(i3);

        // Create Appliance
        Appliance appliance = new Appliance();
        appliance.setName("Air Fryer");
        appliance.setApplianceId(3000);

        Appliance appliance2 = new Appliance();
        appliance2.setName("Crockpot");
        appliance2.setApplianceId(3001);

        applianceRepository.save(appliance);
        applianceRepository.save(appliance2);


        // Create Recipe 1
        Recipe recipe1 = new Recipe();
        recipe1.setTitle("Enchilada Casserole");
        recipe1.setCookTime(10);
        recipe1.setIngredientsQuantity("5 oz Red Enchilada Sauce\n8 oz Ground Turkey\n");
        recipe1.setEstimatedCost(5.50);
        recipe1.setInstructions("Heat oven to 400F.\nIn a pan over medium high heat...");
        recipe1.setServings(2);
        recipe1.setProtein(chicken);
        recipe1.setRecipeOwner(nutritionUser);
        recipe1.addIngredient(i1);
        recipe1.addIngredient(i2);
        recipe1.addAppliance(appliance);
        recipe1.addAppliance(appliance2);
        recipe1.addAllergen(milk);
        recipe1.addAllergen(peanuts);




        // Create Recipe 2
        Recipe recipe2 = new Recipe();
        recipe2.setTitle("Vegetarian Pasta");
        recipe2.setCookTime(15);
        recipe2.setIngredientsQuantity("8 oz Pasta\n1 cup Cherry Tomatoes\n");
        recipe2.setEstimatedCost(3.50);
        recipe2.setInstructions("Boil pasta according to package instructions.\nCut cherry tomatoes in half...");
        recipe2.setServings(4);
        recipe2.setRecipeOwner(nutritionUser2);
        recipe2.setProtein(beef);
        recipe2.addIngredient(i1);
        recipe2.addIngredient(i2);

        // Set different Protein, NutritionUser, Ingredients, and Appliances as needed for Recipe 2

        // Create Recipe 3
        Recipe recipe3 = new Recipe();
        recipe3.setTitle("Grilled Salmon");
        recipe3.setCookTime(12);
        recipe3.setIngredientsQuantity("1 lb Salmon fillets\n1 lemon\n");
        recipe3.setEstimatedCost(8.00);
        recipe3.setInstructions("Preheat grill.\nSeason salmon with salt and pepper.\nGrill for 6 minutes per side...");
        recipe3.setServings(3);

        recipeRepository.save(recipe1);
        recipeRepository.save(recipe2);
        recipeRepository.save(recipe3);
    }
}
