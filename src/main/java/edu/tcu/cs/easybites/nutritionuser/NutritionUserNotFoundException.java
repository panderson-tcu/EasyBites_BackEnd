package edu.tcu.cs.easybites.nutritionuser;

public class NutritionUserNotFoundException extends RuntimeException{

    public NutritionUserNotFoundException(Integer nutritionUserId) {
        super("Could not find Nutrition User with ID " + nutritionUserId + ".");
    }

}
