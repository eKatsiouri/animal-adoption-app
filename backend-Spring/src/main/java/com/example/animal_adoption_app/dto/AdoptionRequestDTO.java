package com.example.animal_adoption_app.dto;

import com.example.animal_adoption_app.model.enums.AdoptionRequestStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AdoptionRequestDTO {

    private  Long id;
    private Long userId;
    private String username;
    private Long animalId;
    private String animalName;
    private LocalDate requestDate;
    private AdoptionRequestStatus adoptionRequestStatus;
}
