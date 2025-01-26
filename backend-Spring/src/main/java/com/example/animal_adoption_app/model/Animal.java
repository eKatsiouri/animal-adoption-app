package com.example.animal_adoption_app.model;

import com.example.animal_adoption_app.model.enums.AnimalType;
import com.example.animal_adoption_app.model.enums.GenderType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "animal")
public class Animal extends AbstractEntity{
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String name;

        @Enumerated(EnumType.STRING)
        private GenderType gender;

        @Enumerated(EnumType.STRING)
        private AnimalType animalType;

        private int age;

        @ColumnDefault("false")
        @Column(name = "is_adopted")
        @JsonProperty("adopted")
        private boolean adopted;

        @JsonManagedReference
        @OneToOne(mappedBy = "animal",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
        private MoreInfo moreInfo;

        private String image;

    }
