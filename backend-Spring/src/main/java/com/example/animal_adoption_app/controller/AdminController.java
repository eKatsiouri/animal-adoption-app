package com.example.animal_adoption_app.controller;

import com.example.animal_adoption_app.dto.*;
import com.example.animal_adoption_app.exceptions.AdoptionRequestNotFoundException;
import com.example.animal_adoption_app.exceptions.AnimalNotFoundException;
import com.example.animal_adoption_app.exceptions.UserNotFoundException;
import com.example.animal_adoption_app.mapper.Mapper;
import com.example.animal_adoption_app.model.AdoptionRequest;
import com.example.animal_adoption_app.model.Animal;
import com.example.animal_adoption_app.service.AnimalAdoptionService;
import com.example.animal_adoption_app.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/login/admin")
@AllArgsConstructor
public class AdminController {
    private final AnimalAdoptionService animalService;
    private final Mapper mapper;
    private final UserService userService;

    @Operation(
            summary = "Create a new animal",
            description = "Adds a new animal to the database",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Animal created successfully",
                            content = @Content(schema = @Schema(implementation = AnimalReadOnlyDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input")
            }
    )
    @PostMapping("/animals")
    public ResponseEntity<AnimalReadOnlyDTO> createAnimal(@RequestBody AnimalDetailsDTO animal) {

        Animal animalEntity = mapper.mapToAnimalEntity(animal);

        Animal newAnimal = animalService.addAnimal(animalEntity);

        AnimalReadOnlyDTO newAnimalDTO = mapper.mapToAnimalReadOnlyDTO(newAnimal);

        return ResponseEntity.status(HttpStatus.CREATED).body(newAnimalDTO);
    }


    @Operation(
            summary = "Update an animal",
            description = "Updates the details of an existing animal by ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Animal updated successfully",
                            content = @Content(schema = @Schema(implementation = Animal.class))),
                    @ApiResponse(responseCode = "404", description = "Animal not found")
            }
    )
    @PutMapping("/animals/{id}")
    public ResponseEntity<Animal> updateAnimalDetails(@PathVariable Long id,@RequestBody AnimalDetailsDTO animalDetailsDTO) throws AnimalNotFoundException {
        if (animalDetailsDTO == null || animalDetailsDTO.getAnimal() == null) {
            return ResponseEntity.badRequest().body(null);
        }

        Animal updatedAnimal = animalService.updateAnimal(id, animalDetailsDTO);
        return ResponseEntity.ok(updatedAnimal);
    }

    @Operation(
            summary = "Get an adoption request by ID",
            description = "Retrieves the details of a specific adoption request",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Adoption request found",
                            content = @Content(schema = @Schema(implementation = AdoptionRequestDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Adoption request not found")
            }
    )

    @GetMapping("/adoptions/{id}")
    public ResponseEntity<AdoptionRequestDTO> getAdoptionRequestById(@PathVariable Long id) {
        try {
            AdoptionRequest adoptionRequest = animalService.getAdoptionRequestById(id);
            AdoptionRequestDTO adoptionRequestDTO = mapper.mapToAdoptionRequestDTO(adoptionRequest);
            return ResponseEntity.ok(adoptionRequestDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @Operation(
            summary = "Delete an animal",
            description = "Deletes an animal and its associated adoption requests",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Animal deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Animal not found")
            }
    )
    @DeleteMapping("/animals/{id}")
    public ResponseEntity<Void> deleteAnimal(@PathVariable Long id) {
        try {
            animalService.deleteAnimal(id);
            return ResponseEntity.noContent().build();
        } catch (AnimalNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    @Operation(
            summary = "Update adoption status",
            description = "Updates the status of an adoption request",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Adoption status updated successfully",
                            content = @Content(schema = @Schema(implementation = AdoptionRequestDTO.class))),
                    @ApiResponse(responseCode = "404", description = " Adoption request not found")
            }
    )
    @PatchMapping("/adoptions/{id}")
    public ResponseEntity<AdoptionRequestDTO> updateAdoptionStatus(
            @PathVariable Long id, @RequestBody AdoptionRequestDTO adoptionRequestDTO) {

        try {
            AdoptionRequest updatedRequest = animalService.updateAdoptionRequestStatus(id, adoptionRequestDTO.getAdoptionRequestStatus());
            AdoptionRequestDTO adoptionRequest = mapper.mapToAdoptionRequestDTO(updatedRequest);
            return ResponseEntity.ok(adoptionRequest);
        } catch (AdoptionRequestNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @Operation(
            summary = "Get all adoption requests",
            description = "Retrieves all pending adoption requests",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of adoption requests retrieved successfully",
                    content = @Content(schema = @Schema(implementation = AdoptionRequestDTO.class))),
                    @ApiResponse(responseCode = "404", description = "No pending adoption requests found")
            }
    )
    @GetMapping("/adoptions")
    public ResponseEntity<List<AdoptionRequestDTO>> getAllAdoptionRequests() {
        try{
        List<AdoptionRequest> adoptionRequests = animalService.getPendingAdoptionRequests();
        List<AdoptionRequestDTO> adoptionRequestDTOS = mapper.mapToAdoptionRequestsDTO(adoptionRequests);
        return ResponseEntity.ok(adoptionRequestDTOS);
        } catch (AdoptionRequestNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        }

    @Operation(
            summary = "Get all users",
            description = "Retrieves all users",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of users retrieved successfully",
                            content = @Content(schema = @Schema(implementation = UserReadOnlyDTO.class))),
                            @ApiResponse(responseCode = "404", description = "No users found")}
    )
    @GetMapping("/users")
    public ResponseEntity<List<UserReadOnlyDTO>> getAllUsers() {
        try {
            List<UserReadOnlyDTO> users =  userService.getAllUsers();
            return ResponseEntity.ok(users);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

    }


    @Operation(
            summary = "Delete a user",
            description = "Deletes a user by ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "User deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "User not found")
            }
    )
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        }catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


}
