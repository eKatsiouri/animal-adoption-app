package com.example.animal_adoption_app.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AdoptionRequestInsertDTO {


    @NotNull(message = "User information is required")
    private Long userId;

    @NotNull(message = "Animal information is required")
    private Long animalId;

    }
