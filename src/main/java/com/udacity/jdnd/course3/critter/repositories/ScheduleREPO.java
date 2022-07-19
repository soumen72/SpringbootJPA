package com.udacity.jdnd.course3.critter.repositories;

import com.udacity.jdnd.course3.critter.entities.Employee;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.schedule.ScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface ScheduleREPO extends JpaRepository<ScheduleEntity,Long> {

    List<ScheduleEntity> findScheduleByPets(Pet pet);

    // List<ScheduleEntity> findScheduleByPet(Pet pet);
    List<ScheduleEntity> findScheduleByEmployees(Employee employee);
    List<ScheduleEntity> findByPetsIn(List<Pet> pets);
}
