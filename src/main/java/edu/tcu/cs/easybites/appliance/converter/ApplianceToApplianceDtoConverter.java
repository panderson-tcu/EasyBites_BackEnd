package edu.tcu.cs.easybites.appliance.converter;

import edu.tcu.cs.easybites.appliance.Appliance;
import edu.tcu.cs.easybites.appliance.dto.ApplianceDto;
import edu.tcu.cs.easybites.ingredient.Ingredient;
import edu.tcu.cs.easybites.ingredient.dto.IngredientDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ApplianceToApplianceDtoConverter implements Converter<Appliance, ApplianceDto> {
    @Override
    public ApplianceDto convert(Appliance source) {
        ApplianceDto applianceDto = new ApplianceDto(source.getApplianceId(), source.getName());
        return applianceDto;
    }

    public List<ApplianceDto> convertList(List<Appliance> appliances) {
        return appliances.stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }
}
