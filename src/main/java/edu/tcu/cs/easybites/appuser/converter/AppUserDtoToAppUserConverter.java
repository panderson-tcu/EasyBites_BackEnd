package edu.tcu.cs.easybites.appuser.converter;

import edu.tcu.cs.easybites.appuser.AppUser;
import edu.tcu.cs.easybites.appuser.dto.AppUserDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AppUserDtoToAppUserConverter implements Converter<AppUserDto, AppUser> {
    @Override
    public AppUser convert(AppUserDto source) {
        AppUser appUser = new AppUser();
        appUser.setEmail(source.email());
        appUser.setFirstName(source.firstName());
        appUser.setLastName(source.lastName());

        return appUser;
    }

    public List<AppUser> convertList(List<AppUserDto> appUserDtos) {
        return appUserDtos.stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }
}
