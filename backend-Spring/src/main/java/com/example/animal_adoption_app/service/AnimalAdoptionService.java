package com.example.animal_adoption_app.service;


import com.example.animal_adoption_app.dto.*;
import com.example.animal_adoption_app.exceptions.*;

import com.example.animal_adoption_app.model.AdoptionRequest;
import com.example.animal_adoption_app.model.Animal;
import com.example.animal_adoption_app.model.MoreInfo;
import com.example.animal_adoption_app.model.User;
import com.example.animal_adoption_app.model.enums.AdoptionRequestStatus;
import com.example.animal_adoption_app.repository.AdoptionRequestRepository;
import com.example.animal_adoption_app.repository.AnimalRepository;
import com.example.animal_adoption_app.repository.MoreInfoRepository;
import com.example.animal_adoption_app.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
public class AnimalAdoptionService implements IAnimalAdoptionService{

    @Autowired
    private final AnimalRepository animalRepository;
    @Autowired
    private final MoreInfoRepository moreInfoRepository;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final AdoptionRequestRepository adoptionRequestRepository;

    @Autowired
    public AnimalAdoptionService(AnimalRepository animalRepository,UserRepository userRepository, MoreInfoRepository moreInfoRepository, AdoptionRequestRepository adoptionRequestRepository) {
        this.animalRepository = animalRepository;
        this.moreInfoRepository = moreInfoRepository;
        this.userRepository = userRepository;
        this.adoptionRequestRepository = adoptionRequestRepository;
    }

    public List<Animal> getAnimals() throws AnimalNotFoundException {
        List<Animal> animals = animalRepository.findAll();
        if (animals.isEmpty()) {
            throw new AnimalNotFoundException("Animal not found", "Animal's List is empty" );
        }
        return animals;
    }

    @Override
    public List<Animal> getAvailableAnimals() throws AnimalNotFoundException {
        List<Animal> animals = animalRepository.findByAdopted(false);
        if (animals.isEmpty()) {
            throw new AnimalNotFoundException("not found", "There's no animal available for adoption" );
        }
        return animals;
    }



    @Override
    public Animal getAnimalById(Long id) throws AnimalNotFoundException {
        Animal animal = animalRepository.findById(id)
                .orElseThrow(() -> new AnimalNotFoundException("Animal not found  ","Animal not found with id:" + id));

        return animal;
    }

    @Override
    public AnimalDetailsDTO getAnimalDetailsById(Long id) throws AnimalNotFoundException {
        Animal animal = animalRepository.findById(id)
                .orElseThrow(() -> new AnimalNotFoundException("Animal not found  ","Animal not found with id:" + id));


        MoreInfo moreInfo = moreInfoRepository.findByAnimal(animal);

        AnimalDetailsDTO detailsDTO = new AnimalDetailsDTO();
        detailsDTO.setAnimal(animal);
        detailsDTO.setMoreInfo(moreInfo);

        return detailsDTO;
    }




@Override
    public Animal updateAnimalStatus(Long id) {
        Animal animal = animalRepository.findById(id).orElse(null);

        if (animal != null) {
            animal.setAdopted(animal.isAdopted());
            return animalRepository.save(animal);
        }

        return null;
    }

    public List<AdoptionRequest> getAdoptionRequestsByUser(Long userId) {
        return adoptionRequestRepository.findByUserId(userId);
    }

    public Animal addAnimal(Animal animal) {
        if (animal == null) {
            throw new IllegalArgumentException("Animal cannot be null");
        }

        Animal savedAnimal = animalRepository.save(animal);

        MoreInfo moreInfo = animal.getMoreInfo();
        if (moreInfo != null) {
            moreInfo.setAnimal(savedAnimal);
            moreInfo = moreInfoRepository.save(moreInfo);
            savedAnimal.setMoreInfo(moreInfo);
        }

        return savedAnimal;
    }

    @Override
    public void deleteAnimal(Long id) throws AnimalNotFoundException {
        Optional<Animal> animal = animalRepository.findById(id);
        if (animal.isEmpty()) {
            throw new AnimalNotFoundException("Animal not found", "Animal not found with ID: " + id);
        }
        animalRepository.deleteById(id);
    }

    @Override
    public Animal updateAnimal(Long id, AnimalDetailsDTO animalDetails) throws AnimalNotFoundException {

        Optional<Animal> optionalAnimal = animalRepository.findById(id);
        if (optionalAnimal.isEmpty()) {
            throw new AnimalNotFoundException("Animal not found", "Animal not found with ID: " + id);
        }

        Animal existingAnimal = optionalAnimal.get();

        existingAnimal.setName(animalDetails.getAnimal().getName());
        existingAnimal.setAge(animalDetails.getAnimal().getAge());
        existingAnimal.setAnimalType(animalDetails.getAnimal().getAnimalType());
        existingAnimal.setGender(animalDetails.getAnimal().getGender());
        existingAnimal.setAdopted(animalDetails.getAnimal().isAdopted());
        existingAnimal.setImage(animalDetails.getAnimal().getImage());


        MoreInfo existingMoreInfo = existingAnimal.getMoreInfo();
        MoreInfo dtoMoreInfo = animalDetails.getMoreInfo();

        if (dtoMoreInfo != null) {
            if (existingMoreInfo == null) {

                existingMoreInfo = new MoreInfo();
                existingMoreInfo.setAnimal(existingAnimal);
            }

            existingMoreInfo.setColor(dtoMoreInfo.getColor());
            existingMoreInfo.setBreed(dtoMoreInfo.getBreed());
            existingMoreInfo.setMicrochipped(dtoMoreInfo.isMicrochipped());
            existingMoreInfo.setNeutered(dtoMoreInfo.isNeutered());
            existingMoreInfo.setFriendlyWithKids(dtoMoreInfo.isFriendlyWithKids());
            existingMoreInfo.setFriendlyWithOtherPets(dtoMoreInfo.isFriendlyWithOtherPets());
            existingMoreInfo.setHasSpecialDiet(dtoMoreInfo.isHasSpecialDiet());

            moreInfoRepository.save(existingMoreInfo);
            existingAnimal.setMoreInfo(existingMoreInfo);
        }

        return animalRepository.save(existingAnimal);
    }
    @Override
    @Transactional
    public AdoptionRequest submitAdoptionRequest(AdoptionRequestInsertDTO requestDTO) throws AdoptionRequestAlreadyExistsException, AdoptionRequestRejectedException, AnimalNotFoundException, UserNotFoundException {
        if (requestDTO == null || requestDTO.getAnimalId() == null || requestDTO.getUserId() == null) {
            throw new IllegalArgumentException("AnimalId or UserId cannot be null");
        }

        if (adoptionRequestRepository.existsByUserIdAndAnimalIdAndAdoptionRequestStatus(
                requestDTO.getUserId(), requestDTO.getAnimalId(), AdoptionRequestStatus.PENDING)) {
            throw new AdoptionRequestAlreadyExistsException("User has already submitted a pending adoption request for this animal");
        }

        if (adoptionRequestRepository.existsByAnimalIdAndAdoptionRequestStatus(
                requestDTO.getAnimalId(), AdoptionRequestStatus.PENDING)) {
            throw new AdoptionRequestAlreadyExistsException("There is already a pending adoption request for this animal.");
        }

        if (adoptionRequestRepository.existsByUserIdAndAnimalIdAndAdoptionRequestStatus(
                requestDTO.getUserId(), requestDTO.getAnimalId(), AdoptionRequestStatus.REJECTED)) {
            throw new AdoptionRequestRejectedException("User has already submitted an adoption request for this animal, and it was rejected.");
        }

        Animal animal = animalRepository.findById(requestDTO.getAnimalId())
                .orElseThrow(() -> new AnimalNotFoundException("Animal not found : " , " Animal not found with ID: " +requestDTO.getAnimalId()));

        User user = userRepository.findById(requestDTO.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found  ","User not found with ID: " + requestDTO.getUserId()));

        AdoptionRequest adoptionRequest = new AdoptionRequest();
        adoptionRequest.setAnimal(animal);
        adoptionRequest.setUser(user);
        adoptionRequest.setRequestDate(LocalDate.now());
        adoptionRequest.setAdoptionRequestStatus(AdoptionRequestStatus.PENDING);

        return adoptionRequestRepository.save(adoptionRequest);}
    @Override
    public boolean isRequestExist(Long animalId, Long userId) {
        if (animalId == null || userId == null) {
            System.out.println("Animal ID or User ID is null. animalId: " + animalId + ", userId: " + userId);
            return false;
        }
        Optional<AdoptionRequest> existingRequest = adoptionRequestRepository.findByUserIdAndAnimalId(userId, animalId);
        if (existingRequest.isPresent()) {
            AdoptionRequestStatus status = existingRequest.get().getAdoptionRequestStatus();
            if (status == AdoptionRequestStatus.REJECTED) {
                System.out.println("User " + userId + " had a rejected request for animal (ID: " + animalId + "). Allowing new request.");
                return false;
            } else {
                System.out.println("User " + userId + " already has a request for animal (ID: " + animalId + ") with status: " + status);
                return true;
            }
        }


        boolean pendingRequestExists = adoptionRequestRepository.existsByAnimalIdAndAdoptionRequestStatus(animalId, AdoptionRequestStatus.PENDING);
        if (pendingRequestExists) {
            System.out.println("There is already a pending request for this animal (ID: " + animalId + ")");
            return true;
        }

        return false;
    }



    @Override
    public AdoptionRequest updateAdoptionRequestStatus(Long id, AdoptionRequestStatus newStatus) throws AdoptionRequestNotFoundException {
        AdoptionRequest adoptionRequest = adoptionRequestRepository.findById(id)
                .orElseThrow(() -> new AdoptionRequestNotFoundException("Adoption request not found with ID: " + id));

        adoptionRequest.setAdoptionRequestStatus(newStatus);

        if (newStatus == AdoptionRequestStatus.APPROVED) {
            Animal animal = adoptionRequest.getAnimal();
            animal.setAdopted(true);
            animalRepository.save(animal);
        }

        return adoptionRequestRepository.save(adoptionRequest);
    }

    @Override
    public AdoptionRequest getAdoptionRequestById(Long id) {
        return adoptionRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Adoption request not found"));
    }

    @Override
    public List<AdoptionRequest> getPendingAdoptionRequests() throws AdoptionRequestNotFoundException {
            List<AdoptionRequest> adoptionRequests = adoptionRequestRepository.findByAdoptionRequestStatus(AdoptionRequestStatus.PENDING);
        if (adoptionRequests.isEmpty()) {
            throw new AdoptionRequestNotFoundException("No pending adoption requests found");
        }
        return adoptionRequests;
    }

}
