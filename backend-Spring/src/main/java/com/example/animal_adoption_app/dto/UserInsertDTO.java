package com.example.animal_adoption_app.dto;


import com.example.animal_adoption_app.model.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserInsertDTO {

    private Long id;

    @NotNull(message = "First name cannot be null")
    private String firstName;

    @NotNull(message = "Last name cannot be null")
    private String lastName;

    @NotNull(message = "Phone number cannot be null")
    private String phoneNumber;

    private String address;

    @NotNull(message = "SSN cannot be null")
    private String ssn;

    @NotNull(message = "Username cannot be null")
    private String username;

    @NotNull(message = "Password cannot be null")
    private String password;

    @Email(message = "Email should be valid")
    private String email;

    @NotNull(message = "Role cannot be null")
    private UserRole role;

    @JsonCreator
    public static UserRole fromString(String value) {
        return UserRole.valueOf(value.toUpperCase());
    }
}
