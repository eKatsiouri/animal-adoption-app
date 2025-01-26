package com.example.animal_adoption_app.service;

import com.example.animal_adoption_app.dto.UserInsertDTO;
import com.example.animal_adoption_app.dto.UserReadOnlyDTO;
import com.example.animal_adoption_app.dto.UserUpdateDTO;
import com.example.animal_adoption_app.exceptions.UserAlreadyExistsException;
import com.example.animal_adoption_app.exceptions.UserNotFoundException;
import com.example.animal_adoption_app.mapper.Mapper;
import com.example.animal_adoption_app.model.User;
import com.example.animal_adoption_app.model.enums.UserRole;
import com.example.animal_adoption_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private Mapper mapper;


    @Override
    public User createUser(UserInsertDTO userDTO) throws UserAlreadyExistsException {
        if (userRepository.existsByUsername(userDTO.getUsername())) {
            throw new UserAlreadyExistsException("User already exists.","A user with this username already exists."+ userDTO.getUsername());
        }

        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new UserAlreadyExistsException("User already exists.","A user with this email already exists."+ userDTO.getEmail());
        }
        User user = new User();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setAddress(userDTO.getAddress());
        user.setSsn(userDTO.getSsn());
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setEmail(userDTO.getEmail());
        user.setRole(userDTO.getRole());

        return userRepository.save(user);
    }

    @Override
    public User updateUser(Long userId, UserInsertDTO userDTO) throws UserNotFoundException {
        Optional<User> existingUser = userRepository.findById(userId);
        if (existingUser.isEmpty()) {
            throw new UserNotFoundException("User not found", "User with id: " + userId);
        }
        User user = existingUser.get();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setAddress(userDTO.getAddress());
        user.setSsn(userDTO.getSsn());
        user.setUsername(userDTO.getUsername());
        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }
        user.setEmail(userDTO.getEmail());
        user.setRole(user.getRole());

        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long userId) throws UserNotFoundException {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException("User not found with ID: " ,"User not found with ID: " + userId);
        }
        userRepository.deleteById(userId);
    }

    @Override
    public Optional<User> findUserById(Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public Optional<User> loadUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }


    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public User updateUserProfile(UserUpdateDTO userProfileUpdateDTO) throws UserNotFoundException {
        Optional<User> existingUser = userRepository.findByUsername(userProfileUpdateDTO.getUsername());
        if (existingUser.isEmpty()) {
            throw new UserNotFoundException("User not found", "User with username: " + userProfileUpdateDTO.getUsername());
        }
        User user = existingUser.get();

        user.setFirstName(userProfileUpdateDTO.getFirstName());
        user.setLastName(userProfileUpdateDTO.getLastName());
        user.setEmail(userProfileUpdateDTO.getEmail());


        user.setPhoneNumber(userProfileUpdateDTO.getPhoneNumber());
        user.setAddress(userProfileUpdateDTO.getAddress());

        User updatedUser = userRepository.save(user);

        return updatedUser;
    }

    @Override
    public UserReadOnlyDTO updateUserRole(Long id, UserRole newRole) throws UserNotFoundException {
        Optional<User> userOptional = userRepository.findById(id);

    if (userOptional.isEmpty()) {
        throw new UserNotFoundException("User not found", "User with id: " + id);
    }

    User user = userOptional.get();


    user.setRole(newRole);
    userRepository.save(user);


    return mapper.mapToUserReadOnlyDTO(user);
}



    @Override
    public List<UserReadOnlyDTO> getAllUsers() throws UserNotFoundException {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            throw new UserNotFoundException("No users found","Users not found");
        }


        return users.stream()
                .map(mapper::mapToUserReadOnlyDTO)
                .collect(Collectors.toList());
    }

}

