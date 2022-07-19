package com.udacity.jdnd.course3.critter.repositories;

import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.entities.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
@Repository
@Transactional
public interface PetREPO extends JpaRepository<Pet, Long> {
//    Pet findPetById(Long id);


    Pet findPetById(Long id);




    List<Pet> findPetsByCustomer(Customer customer);

    @Override
    List<Pet> findAll();
}
