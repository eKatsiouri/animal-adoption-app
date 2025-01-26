package com.example.animal_adoption_app.repository;

import com.example.animal_adoption_app.model.AdoptionRequest;
import com.example.animal_adoption_app.model.enums.AdoptionRequestStatus;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface AdoptionRequestRepository extends JpaRepository<AdoptionRequest, Long> , JpaSpecificationExecutor<AdoptionRequest> {
    List<AdoptionRequest> findByUserId(Long userId);
    List<AdoptionRequest> findByAdoptionRequestStatus(AdoptionRequestStatus adoptionRequestStatus);
    Optional<AdoptionRequest> findByAnimalId(Long animalId);
    boolean existsByAnimalIdAndAdoptionRequestStatus( Long animalId, AdoptionRequestStatus adoptionRequestStatus);
    Optional<AdoptionRequest> findByUserIdAndAnimalId(Long userId, Long animalId);
    boolean existsByUserIdAndAnimalIdAndAdoptionRequestStatus(Long userId,  Long animalId, AdoptionRequestStatus adoptionRequestStatus);
}

