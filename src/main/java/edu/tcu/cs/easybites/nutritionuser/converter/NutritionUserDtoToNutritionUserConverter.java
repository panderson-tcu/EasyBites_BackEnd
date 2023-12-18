package edu.tcu.cs.easybites.nutritionuser.converter;

import edu.tcu.cs.easybites.nutritionuser.NutritionUser;
import edu.tcu.cs.easybites.nutritionuser.dto.NutritionUserDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * This class converts a NutritionUser DTO (Data Transfer Object) to a NutritionUser object.
 */
@Component
public class NutritionUserDtoToNutritionUserConverter implements Converter<NutritionUserDto, NutritionUser> {

    /**
     * Convert method:
     * We will take in a source and set the fields of a new instance of a NutritionUser
     * to the corresponding key of the DTO object.
     *
     * @param source - DTO of a NutritionUser. This will be sent as a JSON format
     * @return nutritionUser - a NutritionObject instance
     */
    @Override
    public NutritionUser convert(NutritionUserDto source) {
        NutritionUser nutritionUser = new NutritionUser();
        nutritionUser.setNutritionUserId(source.nutritionUserId());
        nutritionUser.setFirstName(source.firstName());
        nutritionUser.setLastName(source.lastName());
        nutritionUser.setAdminLevel(source.adminLevel());
        nutritionUser.setEmail(source.email());
        return nutritionUser;
    }
}
