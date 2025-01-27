package com.example.animal_adoption_app.authentication;

import com.example.animal_adoption_app.dto.AuthenticationRequestDTO;
import com.example.animal_adoption_app.dto.AuthenticationResponseDTO;
import com.example.animal_adoption_app.exceptions.NotAuthorizedException;
import com.example.animal_adoption_app.model.User;
import com.example.animal_adoption_app.repository.UserRepository;
import com.example.animal_adoption_app.security.JwtService;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public AuthenticationResponseDTO authenticate( AuthenticationRequestDTO dto)
            throws NotAuthorizedException {
        System.out.println("UserRepository: " + userRepository);
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword())
        );

        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new NotAuthorizedException("User", "User not authorized"));

        String token = jwtService.generateToken(authentication.getName(), user.getRole().name());
        return new AuthenticationResponseDTO(user.getFirstname(), user.getLastname(), token);
    }
}
