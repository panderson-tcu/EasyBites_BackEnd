package edu.tcu.cs.easybites.nutritionuser.converter;

import edu.tcu.cs.easybites.nutritionuser.NutritionUser;
import edu.tcu.cs.easybites.nutritionuser.dto.NutritionUserDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class NutritionUserDtoToNutritionUserConverter implements Converter<NutritionUserDto, NutritionUser> {
    @Override
    public NutritionUser convert(NutritionUserDto source) {
        NutritionUser nutritionUser = new NutritionUser();
        nutritionUser.setFirstName(source.firstName());
        nutritionUser.setLastName(source.lastName());
        nutritionUser.setNutritionUserId(source.nutritionUserId());
        nutritionUser.setAdminLevel(source.adminLevel());
        return nutritionUser;
    }
}
