package com.example.animal_adoption_app.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "more_info")
public class MoreInfo extends AbstractEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String color;

    @JsonProperty("isVaccinated")
    private boolean isVaccinated;

    @JsonProperty("isNeutered")
    private boolean isNeutered;

    @JsonProperty("isMicrochipped")
    private boolean isMicrochipped;

    @JsonProperty("hasSpecialDiet")
    private boolean hasSpecialDiet;

    @JsonProperty("isFriendlyWithOtherPets")
    private boolean isFriendlyWithOtherPets;

    @JsonProperty("isFriendlyWithKids")
    private boolean isFriendlyWithKids;

    private String breed;

    @OneToOne
    @JoinColumn(name = "animal_id", nullable = false)
    @JsonBackReference
    private Animal animal;

}
