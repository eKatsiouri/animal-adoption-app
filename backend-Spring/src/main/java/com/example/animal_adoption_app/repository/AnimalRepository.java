package com.example.animal_adoption_app.repository;

import com.example.animal_adoption_app.dto.AnimalDetailsDTO;
import com.example.animal_adoption_app.model.Animal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface AnimalRepository extends JpaRepository<Animal, Long>, JpaSpecificationExecutor<Animal> {


    List<Animal> findByAdopted(Boolean adopted);
}
