package edu.tcu.cs.easybites.appuser;

import edu.tcu.cs.easybites.appuser.converter.AppUserDtoToAppUserConverter;
import edu.tcu.cs.easybites.appuser.converter.AppUserToAppUserDtoConverter;
import edu.tcu.cs.easybites.appuser.dto.AppUserDto;
import edu.tcu.cs.easybites.recipe.Recipe;
import edu.tcu.cs.easybites.recipe.converter.RecipeToApprovedRecipeDtoConverter;
import edu.tcu.cs.easybites.recipe.dto.ApprovedRecipeDto;
import edu.tcu.cs.easybites.recipe.dto.RecipeDto;
import edu.tcu.cs.easybites.system.Result;
import edu.tcu.cs.easybites.system.StatusCode;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.endpoint.base-url}/app-user") // base-url is specified in application.yml file
//Maybe need to add CORSMapping
public class AppUserController {
    private final AppUserService appUserService;
    private final AppUserDtoToAppUserConverter appUserDtoToAppUserConverter;
    private final AppUserToAppUserDtoConverter appUserToAppUserDtoConverter;
    private final RecipeToApprovedRecipeDtoConverter recipeToApprovedRecipeDtoConverter;

    public AppUserController(AppUserService appUserService,
                             AppUserDtoToAppUserConverter appUserDtoToAppUserConverter,
                             AppUserToAppUserDtoConverter appUserToAppUserDtoConverter, RecipeToApprovedRecipeDtoConverter recipeToApprovedRecipeDtoConverter) {
        this.appUserService = appUserService;
        this.appUserDtoToAppUserConverter = appUserDtoToAppUserConverter;
        this.appUserToAppUserDtoConverter = appUserToAppUserDtoConverter;
        this.recipeToApprovedRecipeDtoConverter = recipeToApprovedRecipeDtoConverter;
    }

    @PostMapping
    public Result addAppUser(@Valid @RequestBody AppUser newAppUser) {
        AppUser savedUser = this.appUserService.save(newAppUser);
        AppUserDto savedUserDto = this.appUserToAppUserDtoConverter.convert(savedUser);
        return new Result(true, StatusCode.SUCCESS, "Add app user successful", savedUserDto);
    }

    @GetMapping
    public Result findAllAppUsers() {
        List<AppUser> foundAppUsers = this.appUserService.findAll();
        List<AppUserDto> appUserDtos = this.appUserToAppUserDtoConverter.convertList(foundAppUsers);
        return new Result(true, StatusCode.SUCCESS, "View all app users successful", appUserDtos);
    }

    @GetMapping("/{appUserId}")
    public Result findAppUserById(@PathVariable String appUserId) {
        AppUser foundAppUser = this.appUserService.findById(appUserId);
        AppUserDto appUserDto = this.appUserToAppUserDtoConverter.convert(foundAppUser);
        return new Result(true, StatusCode.SUCCESS, "Find app user by id successful", appUserDto);
    }

    @GetMapping("/liked/{appUserId}")
    public Result findLikedRecipes(@PathVariable String appUserId) {
        List<Recipe> likedRecipes = this.appUserService.findLikedRecipes(appUserId);
        List<ApprovedRecipeDto> likedRecipeDtos = this.recipeToApprovedRecipeDtoConverter.convertList(likedRecipes);
        return new Result(true, StatusCode.SUCCESS, "Find liked recipes by id successful", likedRecipeDtos);
    }

    @GetMapping("/shoppingCart/{appUserId}")
    public Result findShoppingCartRecipes(@PathVariable String appUserId) {
        List<Recipe> shoppingCartRecipes = this.appUserService.findShoppingCartRecipes(appUserId);
        List<ApprovedRecipeDto> shoppingCartRecipesDtos = this.recipeToApprovedRecipeDtoConverter.convertList(shoppingCartRecipes);
        return new Result(true, StatusCode.SUCCESS, "Find liked recipes by id successful", shoppingCartRecipesDtos);
    }
}
