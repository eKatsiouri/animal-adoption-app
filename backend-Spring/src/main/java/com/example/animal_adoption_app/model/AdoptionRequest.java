package com.example.animal_adoption_app.model;

import com.example.animal_adoption_app.model.enums.AdoptionRequestStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "adoption_requests")
public class AdoptionRequest  extends AbstractEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "animal_id")
    private Animal animal;

    private LocalDate requestDate;


    @Column(name = "adoption_request_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private AdoptionRequestStatus adoptionRequestStatus;


}
