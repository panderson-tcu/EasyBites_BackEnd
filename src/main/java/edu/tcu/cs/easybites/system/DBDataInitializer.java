package edu.tcu.cs.easybites.system;

import edu.tcu.cs.easybites.allergen.Allergen;
import edu.tcu.cs.easybites.allergen.AllergenRepository;
import edu.tcu.cs.easybites.appliance.Appliance;
import edu.tcu.cs.easybites.appliance.ApplianceRepository;
import edu.tcu.cs.easybites.ingredient.Ingredient;
import edu.tcu.cs.easybites.ingredient.IngredientRepository;
import edu.tcu.cs.easybites.nutritionuser.NutritionUser;
import edu.tcu.cs.easybites.nutritionuser.NutritionUserRepository;
import edu.tcu.cs.easybites.nutritionuser.NutritionUserService;
import edu.tcu.cs.easybites.protein.Protein;
import edu.tcu.cs.easybites.protein.ProteinRepository;
import edu.tcu.cs.easybites.recipe.Recipe;
import edu.tcu.cs.easybites.recipe.RecipeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
public class DBDataInitializer implements CommandLineRunner {
    private final RecipeRepository recipeRepository;
    private final AllergenRepository allergenRepository;
    private final ProteinRepository proteinRepository;
    private final NutritionUserService nutritionUserService;
    private final NutritionUserRepository nutritionUserRepository;
    private final IngredientRepository ingredientRepository;
    private final ApplianceRepository applianceRepository;


    public DBDataInitializer(RecipeRepository recipeRepository,
                             AllergenRepository allergenRepository,
                             ProteinRepository proteinRepository,
                             NutritionUserService nutritionUserService,
                             NutritionUserRepository nutritionUserRepository, IngredientRepository ingredientRepository,
                             ApplianceRepository applianceRepository) {
        this.recipeRepository = recipeRepository;
        this.allergenRepository = allergenRepository;
        this.proteinRepository = proteinRepository;
        this.nutritionUserService = nutritionUserService;
        this.nutritionUserRepository = nutritionUserRepository;
        this.ingredientRepository = ingredientRepository;
        this.applianceRepository = applianceRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Allergen milk = new Allergen();
        milk.setName("Milk");
        milk.setAllergenId(2000);

        Allergen eggs = new Allergen();
        eggs.setName("Eggs");
        eggs.setAllergenId(2001);

        Allergen peanuts = new Allergen();
        peanuts.setName("Peanuts");
        peanuts.setAllergenId(2002);

        Allergen treeNuts = new Allergen();
        treeNuts.setName("Tree Nuts");
        treeNuts.setAllergenId(2003);

        Allergen soy = new Allergen();
        soy.setName("Soy");
        soy.setAllergenId(2004);

        Allergen wheat = new Allergen();
        wheat.setName("Wheat");
        wheat.setAllergenId(2005);

        Allergen shellfish = new Allergen();
        shellfish.setName("Shellfish");
        shellfish.setAllergenId(2006);

        Allergen fish = new Allergen();
        fish.setName("Fish");
        fish.setAllergenId(2007);

        Allergen none = new Allergen();
        none.setName("None");
        none.setAllergenId(2008);

        allergenRepository.save(milk);
        allergenRepository.save(eggs);
        allergenRepository.save(peanuts);
        allergenRepository.save(treeNuts);
        allergenRepository.save(soy);
        allergenRepository.save(wheat);
        allergenRepository.save(shellfish);
        allergenRepository.save(fish);
        allergenRepository.save(none);

        // Create Protein
        Protein chicken = new Protein();
        chicken.setProteinName("Chicken");
        chicken.setProteinId(1000);

        Protein beef = new Protein();
        beef.setProteinName("Beef");
        beef.setProteinId(1001);

        Protein pork = new Protein();
        pork.setProteinName("Pork");
        pork.setProteinId(1002);

        Protein tofu = new Protein();
        tofu.setProteinName("Tofu");
        tofu.setProteinId(1003);

        Protein seafood = new Protein();
        seafood.setProteinName("Seafood");
        seafood.setProteinId(1004);

        Protein turkey = new Protein();
        turkey.setProteinName("Turkey");
        turkey.setProteinId(1006);

        Protein noneProtein = new Protein();
        noneProtein.setProteinName("None");
        noneProtein.setProteinId(1005);

        proteinRepository.save(chicken);
        proteinRepository.save(beef);
        proteinRepository.save(pork);
        proteinRepository.save(tofu);
        proteinRepository.save(seafood);
        proteinRepository.save(turkey);
        proteinRepository.save(noneProtein);


        // Create NutritionUser
        NutritionUser nutritionUser = new NutritionUser();
        nutritionUser.setNutritionUserId(110409760);
        nutritionUser.setFirstName("Francisco");
        nutritionUser.setLastName("Alarcon");
        nutritionUser.setAdminLevel("admin");
        nutritionUser.setEmail("f.alarcon@tcu.edu");
        nutritionUser.setPassword("password");
        nutritionUser.setEnabled(true);


        NutritionUser nutritionUser2 = new NutritionUser();
        nutritionUser2.setNutritionUserId(110409762);
        nutritionUser2.setFirstName("Paige");
        nutritionUser2.setLastName("Anderson");
        nutritionUser2.setAdminLevel("admin");
        nutritionUser2.setEmail("paige.anderson@tcu.edu");
        nutritionUser2.setPassword("password");
        nutritionUser2.setEnabled(true);

        NutritionUser nutritionUser3 = new NutritionUser();
        nutritionUser3.setNutritionUserId(110400159);
        nutritionUser3.setFirstName("Anna");
        nutritionUser3.setLastName("Gadbois");
        nutritionUser3.setAdminLevel("admin");
        nutritionUser3.setEmail("anna.j.gadbois@tcu.edu");
        nutritionUser3.setPassword("password");
        nutritionUser3.setEnabled(true);

        NutritionUser nutritionUser4 = new NutritionUser();
        nutritionUser4.setNutritionUserId(5000);
        nutritionUser4.setFirstName("John");
        nutritionUser4.setLastName("Smith");
        nutritionUser4.setAdminLevel("student");
        nutritionUser4.setEmail("john@gmail.com");
        nutritionUser4.setPassword("password");
        nutritionUser4.setEnabled(true);

        NutritionUser nutritionUser5 = new NutritionUser();
        nutritionUser5.setNutritionUserId(1005);
        nutritionUser5.setFirstName("Jessica");
        nutritionUser5.setLastName("Scott");
        nutritionUser5.setAdminLevel("student");
        nutritionUser5.setEmail("jscott@gmail.com");
        nutritionUser5.setPassword("password");
        nutritionUser5.setEnabled(true);


        this.nutritionUserService.save(nutritionUser);
        this.nutritionUserService.save(nutritionUser2);
        this.nutritionUserService.save(nutritionUser3);
        this.nutritionUserService.save(nutritionUser4);
        this.nutritionUserService.save(nutritionUser5);


        this.nutritionUserRepository.save(nutritionUser);
        this.nutritionUserRepository.save(nutritionUser2);
        this.nutritionUserRepository.save(nutritionUser3);
        this.nutritionUserRepository.save(nutritionUser4);
        this.nutritionUserRepository.save(nutritionUser5);

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
        Appliance airFryer = new Appliance();
        airFryer.setName("Air Fryer");
        airFryer.setApplianceId(3000);

        Appliance crockpot = new Appliance();
        crockpot.setName("Crockpot");
        crockpot.setApplianceId(3001);

        Appliance stove = new Appliance();
        stove.setName("Stove");
        stove.setApplianceId(3002);

        Appliance oven = new Appliance();
        oven.setName("Oven");
        oven.setApplianceId(3003);

        Appliance microwave = new Appliance();
        microwave.setName("Microwave");
        microwave.setApplianceId(3004);

        Appliance blender = new Appliance();
        blender.setName("Blender");
        blender.setApplianceId(3005);

        Appliance instantPot = new Appliance();
        instantPot.setName("Instant Pot");
        instantPot.setApplianceId(3006);

        Appliance noneAppliance = new Appliance();
        noneAppliance.setName("None");
        noneAppliance.setApplianceId(3007);

        applianceRepository.save(airFryer);
        applianceRepository.save(crockpot);
        applianceRepository.save(stove);
        applianceRepository.save(oven);
        applianceRepository.save(microwave);
        applianceRepository.save(blender);
        applianceRepository.save(instantPot);
        applianceRepository.save(noneAppliance);

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
        recipe1.addAppliance(airFryer);
        recipe1.addAppliance(stove);
        recipe1.addAllergen(milk);
        recipe1.addAllergen(peanuts);
        recipe1.setStatus("pending");
//        recipe1.setRecipeOwner(nutritionUser4);

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
        recipe2.setStatus("approved");
        recipe2.addIngredient(i1);
        recipe2.addIngredient(i2);
        recipe2.setRecipeOwner(nutritionUser3);

        // Set different Protein, NutritionUser, Ingredients, and Appliances as needed for Recipe 2

        // Create Recipe 3
        Recipe recipe3 = new Recipe();
        recipe3.setTitle("Grilled Salmon");
        recipe3.setCookTime(12);
        recipe3.setIngredientsQuantity("1 lb Salmon fillets\n1 lemon\n");
        recipe3.setEstimatedCost(8.00);
        recipe3.setProtein(chicken);
        recipe3.setStatus("approved");
        recipe3.setInstructions("Preheat grill.\nSeason salmon with salt and pepper.\nGrill for 6 minutes per side...");
        recipe3.setServings(3);
        recipe3.setRecipeOwner(nutritionUser2);

        this.recipeRepository.save(recipe1);
        this.recipeRepository.save(recipe2);
        this.recipeRepository.save(recipe3);
    }
}
