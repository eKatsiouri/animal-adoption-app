package com.example.animal_adoption_app.repository;

import com.example.animal_adoption_app.model.Animal;
import com.example.animal_adoption_app.model.MoreInfo;
import jakarta.persistence.metamodel.SingularAttribute;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.io.Serializable;
import java.util.Optional;

public interface MoreInfoRepository extends JpaRepository<MoreInfo, Long>, JpaSpecificationExecutor<MoreInfo> {
    MoreInfo findByAnimal(Animal animal);
}
