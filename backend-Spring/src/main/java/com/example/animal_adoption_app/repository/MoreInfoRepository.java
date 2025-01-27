package com.example.animal_adoption_app.repository;

import com.example.animal_adoption_app.model.Animal;
import com.example.animal_adoption_app.model.MoreInfo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface MoreInfoRepository extends JpaRepository<MoreInfo, Long>, JpaSpecificationExecutor<MoreInfo> {
    MoreInfo findByAnimal(Animal animal);
}
