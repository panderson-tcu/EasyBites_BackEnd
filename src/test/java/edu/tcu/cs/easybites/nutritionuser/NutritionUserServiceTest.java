package edu.tcu.cs.easybites.nutritionuser;

import edu.tcu.cs.easybites.allergen.Allergen;
import edu.tcu.cs.easybites.appliance.Appliance;
import edu.tcu.cs.easybites.ingredient.Ingredient;
import edu.tcu.cs.easybites.protein.Protein;
import edu.tcu.cs.easybites.recipe.Recipe;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class NutritionUserServiceTest {

    @Mock
    NutritionUserRepository nutritionUserRepository;

    @InjectMocks
    NutritionUserService nutritionUserService;

    List<NutritionUser> nutritionUsers;

    @BeforeEach
    void setUp() {
        this.nutritionUsers = new ArrayList<>();

        NutritionUser nutritionUser1 = new NutritionUser();
        nutritionUser1.setNutritionUserId(110401715);
        nutritionUser1.setFirstName("Paige");
        nutritionUser1.setLastName("Anderson");
        nutritionUsers.add(nutritionUser1);

        NutritionUser nutritionUser2 = new NutritionUser();
        nutritionUser2.setNutritionUserId(110409760);
        nutritionUser2.setFirstName("Francisco");
        nutritionUser2.setLastName("Alarcon");
        nutritionUsers.add(nutritionUser2);


        NutritionUser nutritionUser3 = new NutritionUser();
        nutritionUser3.setNutritionUserId(110408923);
        nutritionUser3.setFirstName("Anna");
        nutritionUser3.setLastName("Gadbois");
        nutritionUsers.add(nutritionUser3);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testFindAllSuccess() {
        // given
        given(nutritionUserRepository.findAll()).willReturn(this.nutritionUsers);

        // when
        List<NutritionUser> actualNutritionUsers = nutritionUserService.findAll();

        // then
        assertThat(actualNutritionUsers.size()).isEqualTo(this.nutritionUsers.size());
        verify(nutritionUserRepository, times(1)).findAll();


    }
}
