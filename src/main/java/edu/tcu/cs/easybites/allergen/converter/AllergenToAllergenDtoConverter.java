package edu.tcu.cs.easybites.allergen.converter;

import edu.tcu.cs.easybites.allergen.Allergen;
import edu.tcu.cs.easybites.allergen.dto.AllergenDto;
import edu.tcu.cs.easybites.appliance.Appliance;
import edu.tcu.cs.easybites.appliance.dto.ApplianceDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AllergenToAllergenDtoConverter implements Converter<Allergen, AllergenDto> {
    @Override
    public AllergenDto convert(Allergen source) {
        AllergenDto allergenDto = new AllergenDto(source.getAllergenId(),
                                                  source.getName());
        return allergenDto;
    }

    public List<AllergenDto> convertList(List<Allergen> allergens) {
        return allergens.stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }
}
