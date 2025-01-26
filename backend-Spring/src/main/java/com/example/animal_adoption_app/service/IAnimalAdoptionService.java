package com.example.animal_adoption_app.service;

import com.example.animal_adoption_app.dto.AdoptionRequestInsertDTO;
import com.example.animal_adoption_app.dto.AnimalDetailsDTO;
import com.example.animal_adoption_app.exceptions.*;
import com.example.animal_adoption_app.model.AdoptionRequest;
import com.example.animal_adoption_app.model.Animal;
import com.example.animal_adoption_app.model.enums.AdoptionRequestStatus;
import com.example.animal_adoption_app.repository.MoreInfoRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IAnimalAdoptionService {


    List<Animal> getAnimals() throws AnimalNotFoundException;

    List<Animal> getAvailableAnimals() throws AnimalNotFoundException;

    Animal getAnimalById(Long id) throws AnimalNotFoundException;

    AnimalDetailsDTO getAnimalDetailsById(Long id) throws AnimalNotFoundException;

    Animal updateAnimalStatus(Long id);

    Animal addAnimal(Animal animal);

    void deleteAnimal(Long id) throws AnimalNotFoundException;

    Animal updateAnimal(Long id, AnimalDetailsDTO animalDetails) throws AnimalNotFoundException;

    AdoptionRequest submitAdoptionRequest(AdoptionRequestInsertDTO requestDTO)
            throws AdoptionRequestAlreadyExistsException, AdoptionRequestRejectedException, AnimalNotFoundException, UserNotFoundException;

    boolean isRequestExist(Long animalId, Long userId);

    AdoptionRequest updateAdoptionRequestStatus(Long id, AdoptionRequestStatus newStatus)
            throws AdoptionRequestNotFoundException;

    AdoptionRequest getAdoptionRequestById(Long id);

    List<AdoptionRequest> getPendingAdoptionRequests() throws AdoptionRequestNotFoundException;

    List<AdoptionRequest> getAdoptionRequestsByUser(Long userId);


    }

