package com.example.animal_adoption_app.controller;

import com.example.animal_adoption_app.dto.AdoptionRequestDTO;
import com.example.animal_adoption_app.dto.AdoptionRequestInsertDTO;
import com.example.animal_adoption_app.exceptions.AdoptionRequestAlreadyExistsException;
import com.example.animal_adoption_app.exceptions.AdoptionRequestRejectedException;
import com.example.animal_adoption_app.exceptions.AnimalNotFoundException;
import com.example.animal_adoption_app.exceptions.UserNotFoundException;
import com.example.animal_adoption_app.mapper.Mapper;
import com.example.animal_adoption_app.model.AdoptionRequest;
import com.example.animal_adoption_app.service.AnimalAdoptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/adoption-request")
@AllArgsConstructor
public class AdoptionController {

    private final AnimalAdoptionService animalAdoptionService;
    private final Mapper mapper;


    @Operation(
            summary = "Submit an adoption request",
            description = "Submits a new adoption request for an animal",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Adoption request created successfully",
                            content = @Content(schema = @Schema(implementation = Map.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "User or animal not found",
                            content = @Content(schema = @Schema(implementation = Map.class))
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Adoption request already exists or was rejected",
                            content = @Content(schema = @Schema(implementation = Map.class))
                    )
            }
    )

    @PostMapping()
    public ResponseEntity<Map<String,String>> submitAdoptionRequest(@RequestBody AdoptionRequestInsertDTO request) {

        try {
            System.out.println("Received AdoptionRequestInsertDTO: " + request);


            AdoptionRequest adoptionRequest = animalAdoptionService.submitAdoptionRequest(request);
            AdoptionRequestDTO adoptionRequestDTO = mapper.mapToAdoptionRequestDTO(adoptionRequest);

            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("Created", adoptionRequestDTO.toString()));

        } catch (AdoptionRequestRejectedException | AdoptionRequestAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error",e.getMessage()));
        } catch (UserNotFoundException | AnimalNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error",e.getMessage()));
        }
    }

}