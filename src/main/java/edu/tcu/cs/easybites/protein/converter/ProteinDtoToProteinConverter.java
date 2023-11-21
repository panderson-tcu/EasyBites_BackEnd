package edu.tcu.cs.easybites.protein.converter;

import edu.tcu.cs.easybites.protein.Protein;
import edu.tcu.cs.easybites.protein.dto.ProteinDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ProteinDtoToProteinConverter implements Converter<ProteinDto, Protein> {
    @Override
    public Protein convert(ProteinDto source) {
        Protein protein = new Protein();
        protein.setProteinId(source.proteinId());
        protein.setProteinName(source.proteinName());
        return protein;
    }
}
