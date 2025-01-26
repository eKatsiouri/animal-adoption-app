package com.example.animal_adoption_app.dto;

import com.example.animal_adoption_app.model.Animal;
import com.example.animal_adoption_app.model.MoreInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class AnimalDetailsDTO {
    private Animal animal;
    private MoreInfo moreInfo;
}
