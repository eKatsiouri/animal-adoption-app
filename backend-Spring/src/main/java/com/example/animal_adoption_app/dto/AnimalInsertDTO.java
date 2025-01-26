package com.example.animal_adoption_app.dto;

import com.example.animal_adoption_app.model.MoreInfo;
import com.example.animal_adoption_app.model.enums.AnimalType;
import com.example.animal_adoption_app.model.enums.GenderType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AnimalInsertDTO {


    @NotNull(message = "Name cannot be null")
    private String name;

    private GenderType gender;
    @Min(value = 0, message = "Age must be a positive number")
    private int age;

    private boolean adopted;

    @NotNull
    private AnimalType animalType;

    private String image;

    private MoreInfo moreInfo;


}
