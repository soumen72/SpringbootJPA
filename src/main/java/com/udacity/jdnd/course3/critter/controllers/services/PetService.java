package com.udacity.jdnd.course3.critter.controllers.services;

import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.repositories.CustomerREPO;
import com.udacity.jdnd.course3.critter.repositories.PetREPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import javax.transaction.Transactional;

@Service
@Transactional
public class PetService {

    @Autowired
    private PetREPO petRepository;

    @Autowired
    private CustomerREPO customerRepository;


    public List<Pet> getAllPets(){
        return petRepository.findAll();
    }

    public Pet savePet(Pet pet) {
        return petRepository.save(pet);
    }

    public Pet getPetById(Long petId)  {

        try{
            return petRepository.findById(petId).orElseThrow(() -> new PetException());
        }catch (Exception e){
            return  null;
        }

    }

    public List<Pet> findPetsByOwner(Customer customer) {
        return petRepository.findPetsByCustomer(customer);
    }


}
