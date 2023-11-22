package edu.tcu.cs.easybites.nutritionuser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.tcu.cs.easybites.nutritionuser.dto.NutritionUserDto;
import edu.tcu.cs.easybites.system.StatusCode;
import edu.tcu.cs.easybites.system.exception.ObjectNotFoundException;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.eq;


import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@SpringBootTest
@AutoConfigureMockMvc
public class NutritionUserControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Mock
    NutritionUserRepository nutritionUserRepository;

    @MockBean
    NutritionUserService nutritionUserService;

    @Autowired
    ObjectMapper objectMapper;

    List<NutritionUser> nutritionUsers;

    @Value("${api.endpoint.base-url}")
    String baseUrl;

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
    void testFindAllSuccess() throws Exception {
        // given
        given(nutritionUserService.findAll()).willReturn(this.nutritionUsers);

        // when and then
        this.mockMvc.perform(get(this.baseUrl + "/nutrition-user").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("View all nutrition users successful"))
                .andExpect(jsonPath("$.data").value(Matchers.hasSize(this.nutritionUsers.size())))
                .andExpect(jsonPath("$.data[0].nutritionUserId").value(110401715))
                .andExpect(jsonPath("$.data[0].firstName").value("Paige"))
                .andExpect(jsonPath("$.data[1].nutritionUserId").value(110409760))
                .andExpect(jsonPath("$.data[1].firstName").value("Francisco"));

    }

    @Test
    void testFindByIdSuccess() throws Exception {
        // Given
        given(this.nutritionUserService.findById(110401715)).willReturn(this.nutritionUsers.get(0));

        // When and then
        this.mockMvc.perform(get(this.baseUrl + "/nutrition-user/110401715").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("View nutrition user by id successful"))
                .andExpect(jsonPath("$.data.nutritionUserId").value(110401715))
                .andExpect(jsonPath("$.data.firstName").value("Paige"));
    }

    @Test
    void testFindByIdNotFound() throws Exception {
        // Given
        given(this.nutritionUserService.findById(110401715)).willThrow(new ObjectNotFoundException("nutrition user", 110401715));

        // When and then
        this.mockMvc.perform(get(this.baseUrl + "/nutrition-user/110401715").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Could not find nutrition user with ID 110401715."))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    void testAddUserSuccess() throws Exception {
        // given
        NutritionUser newUser = new NutritionUser();
        newUser.setNutritionUserId(110401715);
        newUser.setFirstName("Paige");
        newUser.setLastName("Anderson");
        newUser.setEmail("paige.anderson@tcu.edu");
        newUser.setAdminLevel("admin");
        newUser.setPassword("password");

        String json = this.objectMapper.writeValueAsString(newUser);

        newUser.setNutritionUserId(110401715);

        given(this.nutritionUserService.save(Mockito.any(NutritionUser.class))).willReturn(newUser);

        // when and then
        this.mockMvc.perform(post(this.baseUrl + "/nutrition-user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json).accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Add user successful"))
                .andExpect(jsonPath("$.data.nutritionUserId").value(110401715))
                .andExpect(jsonPath("$.data.firstName").value("Paige"))
                .andExpect(jsonPath("$.data.adminLevel").value("admin"));
    }

    @Test
    void testUpdateSuccess() throws Exception {
        // Given
//        NutritionUserDto nutritionUserDto = new NutritionUserDto(110401715, "Paige", "Anderson", "admin", "paige.anderson@tcu.edu");
//
//        String json = this.objectMapper.writeValueAsString(nutritionUserDto);

        NutritionUser updatedNutritionUser = new NutritionUser();
        updatedNutritionUser.setNutritionUserId(110401715);
        updatedNutritionUser.setFirstName("Paige");
        updatedNutritionUser.setLastName("Barber");
        updatedNutritionUser.setEmail("paige.anderson@tcu.edu");
        updatedNutritionUser.setAdminLevel("admin");

        String json = this.objectMapper.writeValueAsString(updatedNutritionUser);


        given(this.nutritionUserService.update(110401715, Mockito.any(NutritionUser.class))).willReturn(updatedNutritionUser);

        // When and then
        this.mockMvc.perform(put(this.baseUrl + "/nutrition-user/110401715").contentType(MediaType.APPLICATION_JSON).content(json).accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Update Success"))
                .andExpect(jsonPath("$.data.nutritionUserId").value(110401715))
                .andExpect(jsonPath("$.data.lastName").value("Barber"));
    }
}
