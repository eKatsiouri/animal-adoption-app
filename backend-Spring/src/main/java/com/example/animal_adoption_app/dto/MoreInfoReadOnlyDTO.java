package com.example.animal_adoption_app.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MoreInfoReadOnlyDTO {

    private Long id;
    private boolean isVaccinated;
    private boolean isNeutered;
    private String color;
    private boolean isMicrochipped;
    private boolean hasSpecialDiet;
    private boolean isFriendlyWithOtherPets;
    private boolean isFriendlyWithKids;
    private String breed;
}
