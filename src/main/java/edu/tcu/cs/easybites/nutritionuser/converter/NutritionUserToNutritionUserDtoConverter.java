package edu.tcu.cs.easybites.nutritionuser.converter;

import edu.tcu.cs.easybites.nutritionuser.NutritionUser;
import edu.tcu.cs.easybites.nutritionuser.dto.NutritionUserDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * This class converts a NutritionUser object to a NutritionUserDTO (Data Transfer Object).
 */
@Component
public class NutritionUserToNutritionUserDtoConverter implements Converter<NutritionUser, NutritionUserDto> {
    /**
     * Convert method:
     * We will take in a NutritionUser object as source and set the fields of a new instance of a NutritionUserDTO
     * to the corresponding field of the NutritionUser object.
     *
     * @param source - a NutritionObject instance
     * @return nutritionUser - DTO of a NutritionUser. This will be sent as a JSON format
     */
    @Override
    public NutritionUserDto convert(NutritionUser source) {
        NutritionUserDto nutritionUserDto = new NutritionUserDto(source.getNutritionUserId(),
                                                                source.getFirstName(),
                                                                source.getLastName(),
                                                                source.getAdminLevel(),
                                                                source.getEmail(),
                                                                source.isEnabled()
        );
        return nutritionUserDto;
    }

    /**
     * ConvertList() method:
     * This method takes in a list of NutritionUser and calls the convert() method
     * to convert each isntance of NutritionUser to NutritionUserDto.
     * @param nutritionUsers - a list of NutritionUser
     * @return - List of NutritionUserDto
     */
    public List<NutritionUserDto> convertList(List<NutritionUser> nutritionUsers) {
        return nutritionUsers.stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }
}
