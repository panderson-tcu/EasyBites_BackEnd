package edu.tcu.cs.easybites.nutritionuser;

import edu.tcu.cs.easybites.nutritionuser.converter.NutritionUserDtoToNutritionUserConverter;
import edu.tcu.cs.easybites.nutritionuser.converter.NutritionUserToNutritionUserDtoConverter;
import edu.tcu.cs.easybites.nutritionuser.dto.NutritionUserDto;
import edu.tcu.cs.easybites.system.Result;
import edu.tcu.cs.easybites.system.StatusCode;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.endpoint.base-url}")
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

    @PostMapping("/nutrition-user")
    public Result addNutritionUser(@Valid @RequestBody NutritionUser newNutritionUser) {
        NutritionUser savedUser = this.nutritionUserService.save(newNutritionUser);
        NutritionUserDto savedUserDto = this.nutritionUserToNutritionUserDtoConverter.convert(savedUser);
        return new Result(true, StatusCode.SUCCESS, "Add user successful", savedUserDto);
    }
}
