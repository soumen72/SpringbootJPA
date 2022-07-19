package com.udacity.jdnd.course3.critter.controllers.services;

import com.udacity.jdnd.course3.critter.entities.Employee;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.repositories.EmployeeREPO;
import com.udacity.jdnd.course3.critter.repositories.PetREPO;
import com.udacity.jdnd.course3.critter.repositories.ScheduleREPO;
import com.udacity.jdnd.course3.critter.schedule.ScheduleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


import javax.transaction.Transactional;
@Service
@Transactional
public class ScheduleService {
    @Autowired
    ScheduleREPO scheduleRepository;

    @Autowired
    EmployeeREPO employeeREPO;

    @Autowired
    PetREPO petREPO;

    public ScheduleEntity saveall(ScheduleEntity schedule, List<Long> employeeIds, List<Long> petId) {
        List<Employee> employees = employeeREPO.findAllById(employeeIds);
        List<Pet> pets = petREPO.findAllById(petId);
        schedule.setEmployees(employees);
        schedule.setPets(pets);
        return scheduleRepository.save(schedule);
    }

    public ScheduleEntity createSchedule(ScheduleEntity schedule) {
        return scheduleRepository.save(schedule);
    }

    public List<ScheduleEntity> findScheduleForPet(Pet pet) {
        return scheduleRepository.findScheduleByPets(pet);
    }

    public List<ScheduleEntity> getSchedulesByPet(List<Pet> pets) {
        return scheduleRepository.findByPetsIn(pets);
    }

    public List<ScheduleEntity> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    public List<ScheduleEntity> findScheduleForEmployee(Employee employee) { return scheduleRepository.findScheduleByEmployees(employee);


    }


}
