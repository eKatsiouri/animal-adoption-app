package com.example.animal_adoption_app.dto;

import com.example.animal_adoption_app.model.MoreInfo;
import com.example.animal_adoption_app.model.enums.AnimalType;
import com.example.animal_adoption_app.model.enums.GenderType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AnimalReadOnlyDTO {

    private Long id;
    private String name;
    private GenderType gender;
    private AnimalType animalType;
    private int age;
    private boolean adopted;
    private String image;
    private MoreInfo moreInfo;
}
