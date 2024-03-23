package edu.tcu.cs.easybites.appuser;

import edu.tcu.cs.easybites.appuser.converter.AppUserDtoToAppUserConverter;
import edu.tcu.cs.easybites.appuser.converter.AppUserToAppUserDtoConverter;
import edu.tcu.cs.easybites.appuser.dto.AppUserDto;
import edu.tcu.cs.easybites.system.Result;
import edu.tcu.cs.easybites.system.StatusCode;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RequestMapping()
//Maybe need to add CORSMapping
public class AppUserController {
    private final AppUserService appUserService;
    private final AppUserDtoToAppUserConverter appUserDtoToAppUserConverter;
    private final AppUserToAppUserDtoConverter appUserToAppUserDtoConverter;

    public AppUserController(AppUserService appUserService,
                             AppUserDtoToAppUserConverter appUserDtoToAppUserConverter,
                             AppUserToAppUserDtoConverter appUserToAppUserDtoConverter) {
        this.appUserService = appUserService;
        this.appUserDtoToAppUserConverter = appUserDtoToAppUserConverter;
        this.appUserToAppUserDtoConverter = appUserToAppUserDtoConverter;
    }


    @PostMapping
    public Result addAppUser(@Valid @RequestBody AppUser newAppUser) {
        AppUser savedUser = this.appUserService.save(newAppUser);
        AppUserDto savedUserDto = this.appUserToAppUserDtoConverter.convert(savedUser);
        return new Result(true, StatusCode.SUCCESS, "Add app user successful", savedUserDto);
    }
}
