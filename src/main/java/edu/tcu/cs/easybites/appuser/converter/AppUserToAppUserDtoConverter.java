package edu.tcu.cs.easybites.appuser.converter;

import edu.tcu.cs.easybites.appliance.Appliance;
import edu.tcu.cs.easybites.appliance.dto.ApplianceDto;
import edu.tcu.cs.easybites.appuser.AppUser;
import edu.tcu.cs.easybites.appuser.dto.AppUserDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AppUserToAppUserDtoConverter implements Converter<AppUser, AppUserDto> {
    @Override
    public AppUserDto convert(AppUser source) {
        AppUserDto appUserDto = new AppUserDto(source.getUserId(),
                                               source.getEmail(),
                                               source.getFirstName(),
                                               source.getLastName()
        );
        return appUserDto;
    }

    public List<AppUserDto> convertList(List<AppUser> appUsers) {
        return appUsers.stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }
}
