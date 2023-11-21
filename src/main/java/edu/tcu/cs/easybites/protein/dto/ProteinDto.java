package edu.tcu.cs.easybites.protein.dto;

import edu.tcu.cs.easybites.nutritionuser.dto.NutritionUserDto;
import jakarta.validation.constraints.NotEmpty;

public record ProteinDto(
        @NotEmpty(message = "proteinId is required.")
        Integer proteinId,
        @NotEmpty(message = "proteinName is required.")
        String proteinName) {

}
