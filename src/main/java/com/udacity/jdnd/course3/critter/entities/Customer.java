package com.udacity.jdnd.course3.critter.entities;


import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Task 3: Design Entities To Represent Your Data
 * You’ll need to decide how to persist your information. To complete this project, you will need to store the following:
 *
 * Two different kinds of users - Employees and Customers.
 */

@Entity
@Table(name = "CUSTOMER")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Customer extends ParentHumanoid{


    @Nationalized
    private String notes;


    @OneToMany(mappedBy = "customer")
    private List<Pet> pets;

//    @Override
//    public String toString() {
//        return "Customer{" +
//                "notes='" + notes + '\'' +
//                ", pets=" + pets +
//                "} " + super.toString();
//    }
    public void addingPet(Pet pet) {

        if (pets == null){
            pets = new ArrayList<>();
        }
        pets.add(pet);
    }


    //getter settrr
    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }


}
