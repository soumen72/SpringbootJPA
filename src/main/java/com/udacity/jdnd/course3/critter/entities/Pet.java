package com.udacity.jdnd.course3.critter.entities;


import com.udacity.jdnd.course3.critter.pet.PetType;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "PET")
public class Pet {

    //need to
    //implement type , name,customer,birthdatye,notes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private PetType type;

    @Nationalized
    private String name;

    @Nationalized
    private String notes;

    //which customer per
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    //changes
    private LocalDate BIRTHDATE;


    public Pet() {
    }

    // paramaterised
    public Pet(Long id, PetType type, String name, String notes, Customer customer, LocalDate BIRTHDATE) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.notes = notes;
        this.customer = customer;
        this.BIRTHDATE = BIRTHDATE;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PetType getType() {
        return type;
    }

    public void setType(PetType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    //

    public void setName(String name) {
        this.name = name;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public LocalDate getBIRTHDATE() {
        return BIRTHDATE;
    }

    public void setBIRTHDATE(LocalDate BIRTHDATE) {
        this.BIRTHDATE = BIRTHDATE;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
