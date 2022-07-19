package com.udacity.jdnd.course3.critter.entities;

import com.udacity.jdnd.course3.critter.user.EmployeeSkill;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.util.Set;



@Entity
@Table(name = "EMPLOYEE")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Employee extends  ParentHumanoid{


    //inside user accesing EmployeeSkill enum value

    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = EmployeeSkill.class)
    @JoinColumn(name = "employee_id")
    @Column(name = "Employee_skills")
    private Set<EmployeeSkill> skills;


    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = DayOfWeek.class)
    @JoinColumn(name = "employee_id")
    @Column(name = "days")
    private Set<DayOfWeek> daysAvailable;

    public Employee() {
    }

    public Set<EmployeeSkill> getSkills() {
        return skills;
    }

    public void setSkills(Set<EmployeeSkill> skills) {
        this.skills = skills;
    }

    public Set<DayOfWeek> getAvailability() {
        return daysAvailable;
    }

    public void setAvailability(Set<DayOfWeek> daysAvailable) {
        this.daysAvailable = daysAvailable;
    }
    //add
}
