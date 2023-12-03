package edu.tcu.cs.easybites.nutritionuser;

import edu.tcu.cs.easybites.nutritionuser.converter.NutritionUserDtoToNutritionUserConverter;
import edu.tcu.cs.easybites.nutritionuser.converter.NutritionUserToNutritionUserDtoConverter;
import edu.tcu.cs.easybites.nutritionuser.dto.NutritionUserDto;
import edu.tcu.cs.easybites.recipe.Recipe;
import edu.tcu.cs.easybites.recipe.dto.RecipeDto;
import edu.tcu.cs.easybites.system.Result;
import edu.tcu.cs.easybites.system.StatusCode;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.endpoint.base-url}/nutrition-user")
@CrossOrigin(origins = "http://localhost:3000")
public class NutritionUserController {

    private final NutritionUserService nutritionUserService;

    private final NutritionUserToNutritionUserDtoConverter nutritionUserToNutritionUserDtoConverter;

    private final NutritionUserDtoToNutritionUserConverter nutritionUserDtoToNutritionUserConverter;

    public NutritionUserController(NutritionUserService nutritionUserService, NutritionUserToNutritionUserDtoConverter nutritionUserToNutritionUserDtoConverter, NutritionUserDtoToNutritionUserConverter nutritionUserDtoToNutritionUserConverter) {
        this.nutritionUserService = nutritionUserService;
        this.nutritionUserToNutritionUserDtoConverter = nutritionUserToNutritionUserDtoConverter;
        this.nutritionUserDtoToNutritionUserConverter = nutritionUserDtoToNutritionUserConverter;
    }

    @GetMapping
    public Result findAllNutritionUsers(){
        List<NutritionUser> foundNutritionUsers = this.nutritionUserService.findAll();
        List<NutritionUserDto> nutritionUserDtos = nutritionUserToNutritionUserDtoConverter.convertList(foundNutritionUsers);
        return new Result(true, StatusCode.SUCCESS, "View all nutrition users successful", nutritionUserDtos);

    }

    @GetMapping("/{nutritionUserId}")
    public Result findNutritionUserById(@PathVariable Integer nutritionUserId){
        NutritionUser foundNutritionUser = this.nutritionUserService.findById(nutritionUserId);
        NutritionUserDto nutritionUserDto = this.nutritionUserToNutritionUserDtoConverter.convert(foundNutritionUser);
        return new Result(true, StatusCode.SUCCESS, "View nutrition user by id successful", nutritionUserDto);

    }

    @PostMapping
    public Result addNutritionUser(@Valid @RequestBody NutritionUser newNutritionUser) {
        newNutritionUser.setEnabled(true);
        NutritionUser savedUser = this.nutritionUserService.save(newNutritionUser);
        NutritionUserDto savedUserDto = this.nutritionUserToNutritionUserDtoConverter.convert(savedUser);
        return new Result(true, StatusCode.SUCCESS, "Add user successful", savedUserDto);
    }

    @PutMapping("/{nutritionUserId}")
    public Result updateNutritionUser(@PathVariable Integer nutritionUserId, @Valid @RequestBody NutritionUserDto nutritionUserDto){
        NutritionUser update = this.nutritionUserDtoToNutritionUserConverter.convert(nutritionUserDto);
        NutritionUser updatedNutritionUser = this.nutritionUserService.update(nutritionUserId, update);
        NutritionUserDto updatedNutritionUserDto = this.nutritionUserToNutritionUserDtoConverter.convert(updatedNutritionUser);
        return new Result(true, StatusCode.SUCCESS, "Nutrition user edited successfully", updatedNutritionUserDto);
    }

    @PutMapping("/{newStatus}/{nutritionUserId}")
    public Result updateAccountStatus(@PathVariable String newStatus, @PathVariable Integer nutritionUserId) {
        this.nutritionUserService.changeAccountStatus(newStatus, nutritionUserId);
        return new Result(true, StatusCode.SUCCESS, "Nutrition user status updated successfully.");
    }
}
