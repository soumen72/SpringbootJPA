package com.udacity.jdnd.course3.critter.controllers;


import com.udacity.jdnd.course3.critter.controllers.services.PetService;
import com.udacity.jdnd.course3.critter.controllers.services.UserService;
import com.udacity.jdnd.course3.critter.dataTransfer_Objects.PetDTO;
import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.entities.Pet;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {
    @Autowired
    private PetService petService;
    @Autowired
    private UserService userService;

    public static PetDTO toPetDTO(Pet pet) {
        //can copy beanutils also

        PetDTO pet_DTO = new PetDTO();

        pet_DTO.setId(pet.getId());
        pet_DTO.setName(pet.getName());
        pet_DTO.setType(pet.getType());
        pet_DTO.setOwnerId(pet.getCustomer().getId());
        pet_DTO.setBirthDate(pet.getBIRTHDATE());
        pet_DTO.setNotes(pet.getNotes());

        return pet_DTO;
    }

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {

        Pet pet = new Pet();

        pet.setType(petDTO.getType());
        pet.setName(petDTO.getName());
        //
        pet.setBIRTHDATE(petDTO.getBirthDate());
        pet.setNotes(petDTO.getNotes());

        long ownerId = petDTO.getOwnerId();
        Customer owner = userService.getCustomerById(ownerId);
        //setCustomer
        pet.setCustomer(owner);



        try {

            pet = petService.savePet(pet);
            //Adding pet in owner
            owner.addingPet(pet);
            userService.saveCustomer(owner);

            PetDTO petObj = toPetDTO(pet);
            petObj.setOwnerId(owner.getId());
            return petObj;

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "unable to save , check again ***********", e);
        }
//        throw new UnsupportedOperationException();
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {

        Pet pet = null;
        try {
            pet = petService.getPetById(petId);
            PetDTO petDTO = toPetDTO(pet);
            return petDTO;

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Pet id error ***********" + petId, e);
        }

    }

    @GetMapping
    public List<PetDTO> getPets(){
        List<Pet> pets = petService.getAllPets();

        List<PetDTO> list = new ArrayList<>();
        for (Pet pet : pets) {
            PetDTO petDTO = toPetDTO(pet);
            list.add(petDTO);
        }
        return list;
        //throw new UnsupportedOperationException();
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        List<Pet> all_pets = null;
        //throw new UnsupportedOperationException();
        try {
            Customer customer = userService.getCustomerById(ownerId);
            all_pets = petService.findPetsByOwner(customer);

            List<PetDTO> PetDTO_list = new ArrayList<>();
            for (Pet pet : all_pets) {
                PetDTO petDTO = toPetDTO(pet);
                PetDTO_list.add(petDTO);
            }
            return PetDTO_list;

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Owner id error *********** " + ownerId, e);
        }
    }
}
