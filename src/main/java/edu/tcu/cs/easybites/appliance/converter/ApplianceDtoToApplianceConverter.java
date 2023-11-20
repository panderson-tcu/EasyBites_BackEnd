package edu.tcu.cs.easybites.appliance.converter;

import edu.tcu.cs.easybites.appliance.Appliance;
import edu.tcu.cs.easybites.appliance.dto.ApplianceDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ApplianceDtoToApplianceConverter implements Converter<ApplianceDto, Appliance> {
    @Override
    public Appliance convert(ApplianceDto source) {
        Appliance appliance = new Appliance();
        appliance.setApplianceId(source.applianceId());
        appliance.setName(source.name());
        return appliance;
    }

    public List<Appliance> convertList(List<ApplianceDto> applianceDtos) {
        return applianceDtos.stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }
}
