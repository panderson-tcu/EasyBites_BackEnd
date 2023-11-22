package edu.tcu.cs.easybites.nutritionuser;

import edu.tcu.cs.easybites.nutritionuser.converter.NutritionUserDtoToNutritionUserConverter;
import edu.tcu.cs.easybites.nutritionuser.converter.NutritionUserToNutritionUserDtoConverter;
import edu.tcu.cs.easybites.nutritionuser.dto.NutritionUserDto;
import edu.tcu.cs.easybites.system.Result;
import edu.tcu.cs.easybites.system.StatusCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class NutritionUserController {

    private final NutritionUserService nutritionUserService;

    private final NutritionUserToNutritionUserDtoConverter nutritionUserToNutritionUserDtoConverter;

    private final NutritionUserDtoToNutritionUserConverter nutritionUserDtoToNutritionUserConverter;

    public NutritionUserController(NutritionUserService nutritionUserService, NutritionUserToNutritionUserDtoConverter nutritionUserToNutritionUserDtoConverter, NutritionUserDtoToNutritionUserConverter nutritionUserDtoToNutritionUserConverter) {
        this.nutritionUserService = nutritionUserService;
        this.nutritionUserToNutritionUserDtoConverter = nutritionUserToNutritionUserDtoConverter;
        this.nutritionUserDtoToNutritionUserConverter = nutritionUserDtoToNutritionUserConverter;
    }

    @GetMapping("/nutrition-user")
    public Result findAllNutritionUsers(){
        List<NutritionUser> foundNutritionUsers = this.nutritionUserService.findAll();
        List<NutritionUserDto> nutritionUserDtos = nutritionUserToNutritionUserDtoConverter.convertList(foundNutritionUsers);
        return new Result(true, StatusCode.SUCCESS, "View all nutrition users successful", nutritionUserDtos);

    }
}
