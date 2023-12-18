package edu.tcu.cs.easybites.nutritionuser;

import edu.tcu.cs.easybites.nutritionuser.converter.NutritionUserDtoToNutritionUserConverter;
import edu.tcu.cs.easybites.nutritionuser.converter.NutritionUserToNutritionUserDtoConverter;
import edu.tcu.cs.easybites.nutritionuser.dto.NutritionUserDto;
import edu.tcu.cs.easybites.system.Result;
import edu.tcu.cs.easybites.system.StatusCode;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The NutritionUserController class handles HTTP requests
 * related to CRUD functions of NutritionUser entities.
 * Here is where we specify all the API endpoints for these functions.
 */
@RestController
@RequestMapping("${api.endpoint.base-url}/nutrition-user") // base-url is specified in application.yml file
@CrossOrigin(origins = "https://easy-bites-tcu.vercel.app") // this is the URL for the EasyBites front end server. Here we allow CORS.
public class NutritionUserController {
    private final NutritionUserService nutritionUserService;

    private final NutritionUserToNutritionUserDtoConverter nutritionUserToNutritionUserDtoConverter;

    private final NutritionUserDtoToNutritionUserConverter nutritionUserDtoToNutritionUserConverter;

    public NutritionUserController(NutritionUserService nutritionUserService, NutritionUserToNutritionUserDtoConverter nutritionUserToNutritionUserDtoConverter, NutritionUserDtoToNutritionUserConverter nutritionUserDtoToNutritionUserConverter) {
        this.nutritionUserService = nutritionUserService;
        this.nutritionUserToNutritionUserDtoConverter = nutritionUserToNutritionUserDtoConverter;
        this.nutritionUserDtoToNutritionUserConverter = nutritionUserDtoToNutritionUserConverter;
    }

    /**
     * Retrieve a list of all NutritionUsers.
     *
     * @return Result containing a list of NutritionUserDto objects.
     */
    @GetMapping
    public Result findAllNutritionUsers(){
        List<NutritionUser> foundNutritionUsers = this.nutritionUserService.findAll();
        List<NutritionUserDto> nutritionUserDtos = nutritionUserToNutritionUserDtoConverter.convertList(foundNutritionUsers);
        return new Result(true, StatusCode.SUCCESS, "View all nutrition users successful", nutritionUserDtos);

    }

    /**
     * Retrieve a NutritionUser by ID.
     *
     * @param nutritionUserId ID of the NutritionUser.
     * @return Result containing the NutritionUserDto object.
     */
    @GetMapping("/{nutritionUserId}")
    public Result findNutritionUserById(@PathVariable Integer nutritionUserId){
        NutritionUser foundNutritionUser = this.nutritionUserService.findById(nutritionUserId);
        NutritionUserDto nutritionUserDto = this.nutritionUserToNutritionUserDtoConverter.convert(foundNutritionUser);
        return new Result(true, StatusCode.SUCCESS, "View nutrition user by id successful", nutritionUserDto);

    }

    /**
     * Add a new NutritionUser.
     *
     * @param newNutritionUser NutritionUser object to be added.
     * @return Result containing the added NutritionUserDto object.
     */
    @PostMapping
    public Result addNutritionUser(@Valid @RequestBody NutritionUser newNutritionUser) {
        newNutritionUser.setEnabled(true);
        NutritionUser savedUser = this.nutritionUserService.save(newNutritionUser);
        NutritionUserDto savedUserDto = this.nutritionUserToNutritionUserDtoConverter.convert(savedUser);
        return new Result(true, StatusCode.SUCCESS, "Add user successful", savedUserDto);
    }

    /**
     * Update an existing NutritionUser.
     *
     * @param nutritionUserId   ID of the NutritionUser to be updated.
     * @param nutritionUserDto  Updated information in NutritionUserDto format.
     * @return Result containing the updated NutritionUserDto object.
     */
    @PutMapping("/{nutritionUserId}")
    public Result updateNutritionUser(@PathVariable Integer nutritionUserId, @Valid @RequestBody NutritionUserDto nutritionUserDto){
        NutritionUser update = this.nutritionUserDtoToNutritionUserConverter.convert(nutritionUserDto);
        NutritionUser updatedNutritionUser = this.nutritionUserService.update(nutritionUserId, update);
        NutritionUserDto updatedNutritionUserDto = this.nutritionUserToNutritionUserDtoConverter.convert(updatedNutritionUser);
        return new Result(true, StatusCode.SUCCESS, "Nutrition user edited successfully", updatedNutritionUserDto);
    }

    /**
     * Update the account status of a NutritionUser.
     *
     * @param newStatus         New status for the NutritionUser account.
     * @param nutritionUserId   ID of the NutritionUser whose account status is to be updated.
     * @return Result indicating the success of the operation.
     */
    @PutMapping("/{newStatus}/{nutritionUserId}")
    public Result updateAccountStatus(@PathVariable String newStatus, @PathVariable Integer nutritionUserId) {
        this.nutritionUserService.changeAccountStatus(newStatus, nutritionUserId);
        return new Result(true, StatusCode.SUCCESS, "Nutrition user status updated successfully.");
    }
}
