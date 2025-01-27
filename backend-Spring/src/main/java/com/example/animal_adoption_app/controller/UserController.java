package com.example.animal_adoption_app.controller;

import com.example.animal_adoption_app.dto.AdoptionRequestDTO;
import com.example.animal_adoption_app.dto.UserReadOnlyDTO;
import com.example.animal_adoption_app.dto.UserUpdateDTO;
import com.example.animal_adoption_app.exceptions.UserNotFoundException;
import com.example.animal_adoption_app.mapper.Mapper;
import com.example.animal_adoption_app.model.User;
import com.example.animal_adoption_app.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("login/user-details")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final Mapper mapper;


    @Operation(
            summary = "Update user profile",
            description = "Updates the profile of a user by their username."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User profile updated successfully"),
            @ApiResponse(responseCode = "404", description = "User not found ")
    })

    @PutMapping("/{username}")
    public ResponseEntity<UserReadOnlyDTO> updateUserProfile(@RequestBody UserUpdateDTO userProfileUpdateDTO) {
        try {

            User updatedUser = userService.updateUserProfile(userProfileUpdateDTO);
            UserReadOnlyDTO userReadOnlyDTO = mapper.mapToUserReadOnlyDTO(updatedUser);
            return ResponseEntity.ok(userReadOnlyDTO);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    @Operation(
            summary = "Get user details",
            description = "Retrieves user details by username, including their adoption requests."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User details retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/{username}")
    public ResponseEntity<UserReadOnlyDTO> getUserByUsername(@PathVariable String username) {
        System.out.println("Fetching user with username: " + username);

        Optional<User> userOptional = userService.loadUserByUsername(username);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        UserReadOnlyDTO userReadOnlyDTO = mapper.mapToUserReadOnlyDTO(userOptional.get());
        List<AdoptionRequestDTO> adoptionRequests = userOptional.get().getAdoptionRequests() != null
                ? userOptional.get().getAdoptionRequests()
                .stream()
                .map(request -> new AdoptionRequestDTO(
                        request.getId(),
                        request.getUser().getId(),
                        request.getUser().getUsername(),
                        request.getAnimal() != null ? request.getAnimal().getId() : null,
                        request.getAnimal().getName(),
                        request.getRequestDate(),
                        request.getAdoptionRequestStatus()

                ))
                .collect(Collectors.toList())
                : Collections.emptyList();

        userReadOnlyDTO.setAdoptionRequests(adoptionRequests);
        return ResponseEntity.ok(userReadOnlyDTO);
    }
    }



