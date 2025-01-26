package com.example.animal_adoption_app.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MoreInfoInsertDTO {

    private Long id;

    private boolean isVaccinated;

    private boolean isNeutered;

    @Size(max = 30, message = "Color should not exceed 30 characters")
    private String color;


    private boolean isMicrochipped;

    private boolean hasSpecialDiet;

    private boolean isFriendlyWithOtherPets;

    private boolean isFriendlyWithKids;

    @Size(max = 50, message = "Breed should not exceed 50 characters")
    private String breed;
}
