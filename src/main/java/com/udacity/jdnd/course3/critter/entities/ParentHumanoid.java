package com.udacity.jdnd.course3.critter.entities;


import com.sun.istack.NotNull;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;

@Entity
@Table(name = "parentHumanoid")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class ParentHumanoid {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Nationalized
    private String name;


    @Column(name = "phone_number")
    private String phone;


    @Column(name = "address")
    @Nationalized
    private String address;



    public ParentHumanoid() {

    }

    @Override
    public String toString() {
        return "ParentHumanoid{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }



}
