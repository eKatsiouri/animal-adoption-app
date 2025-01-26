package com.example.animal_adoption_app.controller;

import com.example.animal_adoption_app.authentication.AuthenticationService;
import com.example.animal_adoption_app.dto.AuthenticationRequestDTO;
import com.example.animal_adoption_app.dto.AuthenticationResponseDTO;
import com.example.animal_adoption_app.dto.UserInsertDTO;
import com.example.animal_adoption_app.exceptions.NotAuthorizedException;
import com.example.animal_adoption_app.exceptions.UserAlreadyExistsException;
import com.example.animal_adoption_app.model.enums.UserRole;
import com.example.animal_adoption_app.security.JwtService;
import com.example.animal_adoption_app.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {



    private final AuthenticationService authenticationService;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtService jwtService;


    @Operation(
            summary = "Register a new user",
            description = "Creates a new user in the system. If the username or email already exists, returns an error."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "User registered successfully",
                    content = @Content(schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "409", description = "Conflict - User with this username or email already exists",
                    content =  @Content(schema = @Schema(implementation = Map.class)))
    })
    @PostMapping("/signup")
    public ResponseEntity<Map<String, String>> signUp(@RequestBody UserInsertDTO userInsertDTO) {
        if (userInsertDTO.getRole() == null) {
            userInsertDTO.setRole(UserRole.USER);
        }

        try {
            userService.createUser(userInsertDTO);
            Map<String, String> response = new HashMap<>();
            response.put("message", "User registered successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
    }


    @Operation(
            summary = "User Login",
            description = "Authenticates a user and returns an access token if the credentials are valid.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "User authenticated successfully. Access token generated.",
                            content = @Content( schema = @Schema(implementation = Map.class))
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized - Invalid credentials",
                            content = @Content( schema = @Schema(example = "{ \"error\": \"Invalid credentials\" }"))
                    )
            }
    )
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody AuthenticationRequestDTO loginRequestDTO) {
        System.out.println("Received login request for username: " + loginRequestDTO.getUsername());
        try {

            AuthenticationResponseDTO responseDTO = authenticationService.authenticate(loginRequestDTO);

            String token = responseDTO.getToken();
            System.out.println("Generated token: " + token);

            Map<String, String> response = new HashMap<>();
            response.put("access_token", token);

            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (NotAuthorizedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid credentials"));
        }
    }




}
