package edu.tcu.cs.easybites.nutritionuser;

import edu.tcu.cs.easybites.allergen.Allergen;
import edu.tcu.cs.easybites.appliance.Appliance;
import edu.tcu.cs.easybites.ingredient.Ingredient;
import edu.tcu.cs.easybites.protein.Protein;
import edu.tcu.cs.easybites.recipe.Recipe;
import edu.tcu.cs.easybites.system.exception.ObjectNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class NutritionUserServiceTest {

    @Mock
    NutritionUserRepository nutritionUserRepository;

    @InjectMocks
    NutritionUserService nutritionUserService;

    @Mock
    PasswordEncoder passwordEncoder;

    List<NutritionUser> nutritionUsers;

    @BeforeEach
    void setUp() {
        this.nutritionUsers = new ArrayList<>();

        NutritionUser nutritionUser1 = new NutritionUser();
        nutritionUser1.setNutritionUserId(110401715);
        nutritionUser1.setFirstName("Paige");
        nutritionUser1.setLastName("Anderson");
        nutritionUser1.setEmail("paige.anderson@tcu.edu");
        nutritionUser1.setAdminLevel("admin");
        nutritionUsers.add(nutritionUser1);

        NutritionUser nutritionUser2 = new NutritionUser();
        nutritionUser2.setNutritionUserId(110409760);
        nutritionUser2.setFirstName("Francisco");
        nutritionUser2.setLastName("Alarcon");
        nutritionUser1.setEmail("f.alarcon@tcu.edu");
        nutritionUser1.setAdminLevel("student");
        nutritionUsers.add(nutritionUser2);


        NutritionUser nutritionUser3 = new NutritionUser();
        nutritionUser3.setNutritionUserId(110408923);
        nutritionUser3.setFirstName("Anna");
        nutritionUser3.setLastName("Gadbois");
        nutritionUser3.setEmail("anna.j.gadbois@tcu.edu");
        nutritionUser3.setAdminLevel("student");
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

    @Test
    void testFindByIdSuccess() {
        // Given
        NutritionUser n = new NutritionUser();
        n.setNutritionUserId(110401715);
        n.setFirstName("Paige");
        n.setLastName("Anderson");
        n.setEmail("paige.anderson@tcu.edu");
        n.setAdminLevel("admin");

        given(nutritionUserRepository.findById(110401715)).willReturn(Optional.of(n));

        // When
        NutritionUser returnedNutritionUser = nutritionUserService.findById(110401715);

        // Then
        assertThat(returnedNutritionUser.getNutritionUserId()).isEqualTo(n.getNutritionUserId());
        assertThat(returnedNutritionUser.getFirstName()).isEqualTo(n.getFirstName());
        assertThat(returnedNutritionUser.getLastName()).isEqualTo(n.getLastName());
        assertThat(returnedNutritionUser.getEmail()).isEqualTo(n.getEmail());
        assertThat(returnedNutritionUser.getAdminLevel()).isEqualTo(n.getAdminLevel());
        verify(nutritionUserRepository, times(1)).findById(110401715);


    }

    @Test
    void testAddUserSuccess() {
        // given
        NutritionUser newUser = new NutritionUser();
        newUser.setNutritionUserId(110401715);
        newUser.setFirstName("Paige");
        newUser.setLastName("Anderson");
        newUser.setEmail("paige.anderson@tcu.edu");
        newUser.setAdminLevel("admin");

        given(this.passwordEncoder.encode(newUser.getPassword())).willReturn("Encoded Password");
        given(this.nutritionUserRepository.save(newUser)).willReturn(newUser);

        // when
        NutritionUser returnedUser = this.nutritionUserService.save(newUser);

        // then
        assertThat(returnedUser.getNutritionUserId()).isEqualTo(110401715);
        assertThat(returnedUser.getFirstName()).isEqualTo("Paige");
        assertThat(returnedUser.getLastName()).isEqualTo("Anderson");
        assertThat(returnedUser.getAdminLevel()).isEqualTo("admin");
        verify(this.nutritionUserRepository, times(1)).save(newUser);
    }

    @Test
    void testFindByIdNotFound() {
        // Given
        given(nutritionUserRepository.findById(Mockito.any(Integer.class))).willReturn(Optional.empty());

        // When
        Throwable thrown = catchThrowable(() -> {
            NutritionUser returnedNutritionUser = nutritionUserService.findById(110401715);
        });

        // Then
        assertThat(thrown)
                .isInstanceOf(ObjectNotFoundException.class)
                .hasMessage("Could not find nutrition user with ID 110401715.");
        verify(nutritionUserRepository, times(1)).findById(110401715);
    }

    @Test
    void testUpdateUserSuccess() {
        // given
        NutritionUser oldUser = new NutritionUser();
        oldUser.setNutritionUserId(110401715);
        oldUser.setFirstName("Paige");
        oldUser.setLastName("Anderson");
        oldUser.setEmail("paige.anderson@tcu.edu");
        oldUser.setAdminLevel("user");

        NutritionUser update = new NutritionUser();
        update.setNutritionUserId(110401715);
        update.setFirstName("New");
        update.setLastName("Last");
        update.setEmail("paige.anderson@tcu.edu");
        update.setAdminLevel("admin");

        given(this.nutritionUserRepository.findById(110401715)).willReturn(Optional.of(oldUser));
        given(this.nutritionUserRepository.save(oldUser)).willReturn(oldUser);

        // when
        NutritionUser updatedUser = this.nutritionUserService.update(110401715, update);

        // then
        assertThat(updatedUser.getNutritionUserId()).isEqualTo(110401715);
        assertThat(updatedUser.getAdminLevel()).isEqualTo("admin");
        assertThat(updatedUser.getFirstName()).isEqualTo("New");
        assertThat(updatedUser.getLastName()).isEqualTo("Last");
        verify(nutritionUserRepository, times(1)).findById(110401715);
        verify(nutritionUserRepository, times(1)).save(oldUser);
    }

    @Test
    void testUpdateUserNotFound() {
        // given
        NutritionUser update = new NutritionUser();
        update.setNutritionUserId(110401715);
        update.setFirstName("Paige - new");
        update.setLastName("Anderson");
        update.setEmail("paige.anderson@tcu.edu");
        update.setAdminLevel("user");

        given(this.nutritionUserRepository.findById(1)).willReturn(Optional.empty());

        // when
        Throwable thrown = assertThrows(ObjectNotFoundException.class, () -> {
            this.nutritionUserService.update(1, update);
        });

        // then
        assertThat(thrown)
                .isInstanceOf(ObjectNotFoundException.class)
                .hasMessage("Could not find nutrition user with ID 1.");
        verify(this.nutritionUserRepository, times(1)).findById(1);

    }
}
