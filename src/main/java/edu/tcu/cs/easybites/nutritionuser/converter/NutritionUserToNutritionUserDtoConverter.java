package edu.tcu.cs.easybites.nutritionuser.converter;

import edu.tcu.cs.easybites.nutritionuser.NutritionUser;
import edu.tcu.cs.easybites.nutritionuser.dto.NutritionUserDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class NutritionUserToNutritionUserDtoConverter implements Converter<NutritionUser, NutritionUserDto> {
    @Override
    public NutritionUserDto convert(NutritionUser source) {
        NutritionUserDto nutritionUserDto = new NutritionUserDto(source.getFirstName(),
                                                                    source.getLastName(),
                                                                    source.getNutritionUserId(),
                                                                    source.getAdminLevel());
        return nutritionUserDto;
    }
}
