package com.example.animal_adoption_app.controller;

import com.example.animal_adoption_app.dto.AnimalDetailsDTO;
import com.example.animal_adoption_app.dto.AnimalReadOnlyDTO;
import com.example.animal_adoption_app.exceptions.AnimalNotFoundException;
import com.example.animal_adoption_app.mapper.Mapper;
import com.example.animal_adoption_app.model.Animal;

import com.example.animal_adoption_app.repository.AnimalRepository;
import com.example.animal_adoption_app.service.AnimalAdoptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/animals")
public class AnimalController {


    private AnimalRepository animalRepository;
    private AnimalAdoptionService animalAdoptionService;

    private Mapper mapper;

    @Autowired
    public AnimalController(AnimalAdoptionService animalAdoptionService, AnimalRepository animalRepository, Mapper mapper) {
        this.animalAdoptionService = animalAdoptionService;
        this.animalRepository = animalRepository;
        this.mapper = mapper;
    }

    @Operation(
            summary = "Retrieve all animals",
            description = "Returns a list of all animals in the system."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of animals retrieved successfully",
                    content = @Content( schema = @Schema(implementation = AnimalReadOnlyDTO.class))),
            @ApiResponse(responseCode = "404", description = "No animals found")
    })
    @GetMapping
    public ResponseEntity<List<AnimalReadOnlyDTO>> getAnimals() {
        try {
            List<Animal>  animals = animalAdoptionService.getAnimals();
            List<AnimalReadOnlyDTO> animalDTOs = animals.stream()
                    .map(mapper:: mapToAnimalReadOnlyDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(animalDTOs);
        } catch (AnimalNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

    }


    @Operation(
            summary = "Retrieve animals available for adoption",
            description = "Returns a list of animals that are currently available for adoption."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of animals available for adoption retrieved successfully",
                    content = @Content( schema = @Schema(implementation = AnimalReadOnlyDTO.class))),
            @ApiResponse(responseCode = "404", description = "No animals available for adoption")
    })

    @GetMapping("/adoption")
    public ResponseEntity<List<AnimalReadOnlyDTO>> getAnimalsForAdoption() {
        try {
            List<Animal>   availableAnimals = animalAdoptionService.getAvailableAnimals();
            List<AnimalReadOnlyDTO> animalDTOs = availableAnimals.stream()
                    .map(mapper::mapToAnimalReadOnlyDTO)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(animalDTOs);
        } catch (AnimalNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

    }

    @Operation(
            summary = "Find animal details by ID",
            description = "Returns the details of a specific animal by its ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Animal details retrieved successfully",
                    content = @Content( schema = @Schema(implementation = AnimalDetailsDTO.class))),
            @ApiResponse(responseCode = "404", description = "Animal not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<AnimalDetailsDTO> getAnimalById(@PathVariable Long id) {

        try {
            AnimalDetailsDTO   animalDetails = animalAdoptionService.getAnimalDetailsById(id);
            return ResponseEntity.ok(animalDetails);
        } catch (AnimalNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

    }

}
