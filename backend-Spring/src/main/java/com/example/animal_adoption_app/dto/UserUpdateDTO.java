package com.example.animal_adoption_app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserUpdateDTO {

    private String username;

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String address;
    private String email;


}
