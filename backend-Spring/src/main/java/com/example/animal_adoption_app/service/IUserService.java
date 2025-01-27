package com.example.animal_adoption_app.service;

import com.example.animal_adoption_app.dto.UserInsertDTO;
import com.example.animal_adoption_app.dto.UserReadOnlyDTO;
import com.example.animal_adoption_app.dto.UserUpdateDTO;
import com.example.animal_adoption_app.exceptions.UserAlreadyExistsException;
import com.example.animal_adoption_app.exceptions.UserNotFoundException;
import com.example.animal_adoption_app.model.User;
import com.example.animal_adoption_app.model.enums.UserRole;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    User createUser(UserInsertDTO userDTO) throws UserAlreadyExistsException;
    User updateUser(Long userId, UserInsertDTO userDTO) throws UserNotFoundException;
    void deleteUser(Long userId) throws UserNotFoundException;
    Optional<User> findUserById(Long userId);
    Optional<User> loadUserByUsername(String username);
    boolean existsByUsername(String username);
    User updateUserProfile(UserUpdateDTO userProfileUpdateDTO) throws UserNotFoundException;
    UserReadOnlyDTO updateUserRole(Long id, UserRole newRole) throws UserNotFoundException;
    List<UserReadOnlyDTO> getAllUsers() throws UserNotFoundException;
}
