package edu.tcu.cs.easybites.protein.converter;

import edu.tcu.cs.easybites.protein.Protein;
import edu.tcu.cs.easybites.protein.dto.ProteinDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ProteinToProteinDtoConverter implements Converter<Protein, ProteinDto> {
    @Override
    public ProteinDto convert(Protein source) {
        ProteinDto proteinDto = new ProteinDto(source.getProteinId(), source.getProteinName());
        return proteinDto;
    }
}
