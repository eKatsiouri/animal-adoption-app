package com.example.animal_adoption_app.mapper;

import com.example.animal_adoption_app.dto.*;
import com.example.animal_adoption_app.model.AdoptionRequest;
import com.example.animal_adoption_app.model.Animal;
import com.example.animal_adoption_app.model.MoreInfo;
import com.example.animal_adoption_app.model.User;
import com.example.animal_adoption_app.model.enums.AdoptionRequestStatus;
import com.example.animal_adoption_app.repository.AnimalRepository;
import com.example.animal_adoption_app.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class Mapper {


    private final UserRepository userRepository;
    private final AnimalRepository animalRepository;

    public Animal mapToAnimalEntity(AnimalDetailsDTO animalInsertDTO) {
        Animal animal = new Animal();
        if (animalInsertDTO.getAnimal() != null) {
            animal.setAnimalType(animalInsertDTO.getAnimal().getAnimalType());
            animal.setName(animalInsertDTO.getAnimal().getName());
            animal.setGender(animalInsertDTO.getAnimal().getGender());
            animal.setAge(animalInsertDTO.getAnimal().getAge());
            animal.setAdopted(animalInsertDTO.getAnimal().isAdopted());
            animal.setImage(animalInsertDTO.getAnimal().getImage());
        }
        MoreInfo moreInfo = new MoreInfo();
        moreInfo.setAnimal(animal);
        if (animalInsertDTO.getMoreInfo() != null) {
            moreInfo.setColor(animalInsertDTO.getMoreInfo().getColor());
            moreInfo.setBreed(animalInsertDTO.getMoreInfo().getBreed());
            moreInfo.setMicrochipped(animalInsertDTO.getMoreInfo().isMicrochipped());
            moreInfo.setNeutered(animalInsertDTO.getMoreInfo().isNeutered());
            moreInfo.setFriendlyWithOtherPets(animalInsertDTO.getMoreInfo().isFriendlyWithOtherPets());
            moreInfo.setFriendlyWithKids(animalInsertDTO.getMoreInfo().isFriendlyWithKids());
            moreInfo.setVaccinated(animalInsertDTO.getMoreInfo().isVaccinated());
        }

        animal.setMoreInfo(moreInfo);
        return animal;
    }

    public User mapToUserEntity(UserInsertDTO userInsertDTO) {
        User user = new User();
        user.setAddress(userInsertDTO.getAddress());
        user.setEmail(userInsertDTO.getEmail());
        user.setFirstName(userInsertDTO.getFirstName());
        user.setLastName(userInsertDTO.getLastName());
        user.setPhoneNumber(String.valueOf(userInsertDTO.getPhoneNumber()));
        user.setUsername(userInsertDTO.getUsername());
        user.setPassword(userInsertDTO.getPassword());
        user.setSsn(userInsertDTO.getSsn());
        return user;
    }

    public MoreInfo mapToMoreInfoEntity(MoreInfoInsertDTO moreInfoInsertDTO) {
        MoreInfo moreInfo = new MoreInfo();
        moreInfo.setId(moreInfoInsertDTO.getId());
        moreInfo.setBreed(moreInfoInsertDTO.getBreed());
        moreInfo.setColor(moreInfoInsertDTO.getColor());
        moreInfo.setFriendlyWithKids(moreInfoInsertDTO.isFriendlyWithKids());
        moreInfo.setNeutered(moreInfoInsertDTO.isNeutered());
        moreInfo.setMicrochipped(moreInfoInsertDTO.isMicrochipped());
        moreInfo.setFriendlyWithOtherPets(moreInfoInsertDTO.isFriendlyWithOtherPets());
        moreInfo.setHasSpecialDiet(moreInfoInsertDTO.isHasSpecialDiet());
        return moreInfo;
    }

    public AnimalReadOnlyDTO mapToAnimalReadOnlyDTO(Animal animal){
        AnimalReadOnlyDTO animalReadOnlyDTO = new AnimalReadOnlyDTO();
        animalReadOnlyDTO.setId(animal.getId());
        animalReadOnlyDTO.setName(animal.getName());
        animalReadOnlyDTO.setGender(animal.getGender());
        animalReadOnlyDTO.setAge(animal.getAge());
        animalReadOnlyDTO.setAnimalType(animal.getAnimalType());
        if (animal.getMoreInfo() != null) {
            MoreInfo moreInfo = new MoreInfo();
            moreInfo.setHasSpecialDiet(animal.getMoreInfo().isHasSpecialDiet());
            moreInfo.setFriendlyWithOtherPets(animal.getMoreInfo().isFriendlyWithOtherPets());
            moreInfo.setFriendlyWithKids(animal.getMoreInfo().isFriendlyWithKids());
            moreInfo.setNeutered(animal.getMoreInfo().isNeutered());
            moreInfo.setMicrochipped(animal.getMoreInfo().isMicrochipped());
            moreInfo.setColor(animal.getMoreInfo().getColor());
            moreInfo.setBreed(animal.getMoreInfo().getBreed());
            animalReadOnlyDTO.setMoreInfo(moreInfo);
        } else {
            animalReadOnlyDTO.setMoreInfo(null);
        }
        return animalReadOnlyDTO;
    }

    public UserReadOnlyDTO mapToUserReadOnlyDTO(User user){
        UserReadOnlyDTO userReadOnlyDTO = new UserReadOnlyDTO();
        userReadOnlyDTO.setId(user.getId());
        userReadOnlyDTO.setAddress(user.getAddress());
        userReadOnlyDTO.setEmail(user.getEmail());
        userReadOnlyDTO.setUsername(user.getUsername());
        userReadOnlyDTO.setFirstname(user.getFirstname());
        userReadOnlyDTO.setLastname(user.getLastname());
        userReadOnlyDTO.setPhoneNumber(user.getPhoneNumber());
        userReadOnlyDTO.setSsn(user.getSsn());
        userReadOnlyDTO.setRole(user.getRole());
        return userReadOnlyDTO;
    }
    public AdoptionRequest mapToAdoptionRequestEntity(AdoptionRequestInsertDTO adoptionRequestInsertDTO) {
        AdoptionRequest adoptionRequest = new AdoptionRequest();

        User user = userRepository.findById(adoptionRequestInsertDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + adoptionRequestInsertDTO.getUserId()));
        adoptionRequest.setUser(user);

        Animal animal = animalRepository.findById(adoptionRequestInsertDTO.getAnimalId())
                .orElseThrow(() -> new RuntimeException("Animal not found with ID: " + adoptionRequestInsertDTO.getAnimalId()));
        adoptionRequest.setAnimal(animal);

        adoptionRequest.setAdoptionRequestStatus(AdoptionRequestStatus.PENDING);
        adoptionRequest.setRequestDate(LocalDate.now());

        return adoptionRequest;
    }

    public AdoptionRequestDTO mapToAdoptionRequestDTO(AdoptionRequest adoptionRequest) {
        AdoptionRequestDTO adoptionRequestDTO = new AdoptionRequestDTO();
        adoptionRequestDTO.setId(adoptionRequest.getId());
        adoptionRequestDTO.setRequestDate(adoptionRequest.getRequestDate());
        adoptionRequestDTO.setUsername(adoptionRequest.getUser().getUsername());
        adoptionRequestDTO.setUserId(adoptionRequest.getUser().getId());
        adoptionRequestDTO.setAdoptionRequestStatus(adoptionRequest.getAdoptionRequestStatus());
        adoptionRequestDTO.setAnimalId(adoptionRequest.getAnimal().getId());
        adoptionRequestDTO.setAnimalName(adoptionRequest.getAnimal().getName());
        return adoptionRequestDTO;
    }
    public List<AdoptionRequestDTO> mapToAdoptionRequestsDTO(List<AdoptionRequest> adoptionRequests) {
        return adoptionRequests.stream()
                .map(request -> new AdoptionRequestDTO(
                        request.getId(),
                        request.getUser().getId(),
                        request.getUser().getUsername(),
                        request.getAnimal().getId() ,
                        request.getAnimal().getName(),
                        request.getRequestDate(),
                        request.getAdoptionRequestStatus()
                ))
                .collect(Collectors.toList());
    }

    public MoreInfoReadOnlyDTO mapToMoreInfoDTO(MoreInfo moreInfo){
        MoreInfoReadOnlyDTO moreInfoReadOnlyDTO = new MoreInfoReadOnlyDTO();
        moreInfoReadOnlyDTO.setId(moreInfo.getId());
        moreInfoReadOnlyDTO.setBreed(moreInfo.getBreed());
        moreInfoReadOnlyDTO.setColor(moreInfo.getColor());
        moreInfoReadOnlyDTO.setFriendlyWithKids(moreInfo.isFriendlyWithKids());
        moreInfoReadOnlyDTO.setNeutered(moreInfo.isNeutered());
        moreInfoReadOnlyDTO.setMicrochipped(moreInfo.isMicrochipped());
        moreInfoReadOnlyDTO.setFriendlyWithOtherPets(moreInfo.isFriendlyWithOtherPets());
        moreInfoReadOnlyDTO.setHasSpecialDiet(moreInfo.isHasSpecialDiet());
        return moreInfoReadOnlyDTO;
    }

    public AnimalDetailsDTO mapToAnimalDetailsDTO(Animal animal) {
        AnimalDetailsDTO animalDetailsDTO = new AnimalDetailsDTO();
        animalDetailsDTO.setAnimal(animal);
        MoreInfo moreInfo = animal.getMoreInfo();

        animalDetailsDTO.setMoreInfo(moreInfo);

        return animalDetailsDTO;
    }

    public User mapToUserEntity(UserUpdateDTO userUpdateDTO) {

        User user = new User();
        user.setAddress(userUpdateDTO.getAddress());
        user.setEmail(userUpdateDTO.getEmail());
        user.setFirstName(userUpdateDTO.getFirstName());
        user.setLastName(userUpdateDTO.getLastName());
        user.setPhoneNumber(String.valueOf(userUpdateDTO.getPhoneNumber()));
        return user;   }


}
