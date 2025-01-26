package com.example.animal_adoption_app.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @Operation(
            summary = "Home endpoint",
            description = "Returns a welcome message from the home endpoint."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Welcome message retrieved successfully")
    })
    @GetMapping("/home")
    public ResponseEntity<String> home() {
        return ResponseEntity.ok("Welcome to the Home Page!");
    }
}