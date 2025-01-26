package com.example.animal_adoption_app.dto;

import com.example.animal_adoption_app.model.User;
import com.example.animal_adoption_app.model.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserReadOnlyDTO {

    private Long id;
    private String firstname;
    private String lastname;
    private String phoneNumber;
    private String address;
    private String ssn;
    private String username;
    private String email;
    private UserRole role;
    private List<AdoptionRequestDTO> adoptionRequests;

}
