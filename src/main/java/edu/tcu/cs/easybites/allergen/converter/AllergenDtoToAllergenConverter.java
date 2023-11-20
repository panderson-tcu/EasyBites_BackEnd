package edu.tcu.cs.easybites.allergen.converter;

import edu.tcu.cs.easybites.allergen.Allergen;
import edu.tcu.cs.easybites.allergen.dto.AllergenDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AllergenDtoToAllergenConverter implements Converter<AllergenDto, Allergen> {
    @Override
    public Allergen convert(AllergenDto source) {
        Allergen allergen = new Allergen();
        allergen.setAllergenId(source.allergenId());
        allergen.setName(source.name());
        return allergen;
    }

    public List<Allergen> convertList(List<AllergenDto> allergenDtos) {
        return allergenDtos.stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }
}
