package com.udacity.jdnd.course3.critter.controllers;

import com.udacity.jdnd.course3.critter.controllers.services.PetService;
import com.udacity.jdnd.course3.critter.controllers.services.ScheduleService;
import com.udacity.jdnd.course3.critter.controllers.services.UserService;
import com.udacity.jdnd.course3.critter.dataTransfer_Objects.ScheduleDTO;
import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.entities.Employee;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.schedule.ScheduleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    ScheduleService scheduleService;
    @Autowired
    PetService petService;
    @Autowired
    UserService userService;


    public static ScheduleDTO ScheduleDTO(ScheduleEntity schedule) {
        //reuseable convertion
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        //
        scheduleDTO.setId(schedule.getId());
        scheduleDTO.setEmployeeIds(schedule.getEmployees().stream().map(Employee::getId).collect(Collectors.toList()));
        scheduleDTO.setPetIds(schedule.getPets().stream().map(Pet::getId).collect(Collectors.toList()));
        scheduleDTO.setDate(schedule.getDate());
//        scheduleDTO.setDate(schedule.());
        scheduleDTO.setActivities(schedule.getActivities());
        return scheduleDTO;
    }


    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        ScheduleEntity scheduleEntity = new ScheduleEntity();

        scheduleEntity.setDate(scheduleDTO.getDate());
        scheduleEntity.setActivities(scheduleDTO.getActivities());

        ScheduleDTO scheduleObj = null;
        try {
            //scheduleService.
            ScheduleEntity save = scheduleService.saveall(scheduleEntity, scheduleDTO.getEmployeeIds(), scheduleDTO.getPetIds());
            scheduleObj = ScheduleDTO(save);
            return scheduleObj;

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Schedule does not save, an error occurred", e);
        }
        //throw new UnsupportedOperationException();
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<ScheduleEntity> schedules = scheduleService.getAllSchedules();
        List<ScheduleDTO> list = new ArrayList<>();

        for (ScheduleEntity schedule : schedules) {
            ScheduleDTO scheduleDTO = ScheduleDTO(schedule);
            list.add(scheduleDTO);
        }
        return list;
        //throw new UnsupportedOperationException();
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {

        List<ScheduleEntity> schedules = null;
        try {
            Pet petById = petService.getPetById(petId);
            schedules = scheduleService.findScheduleForPet(petById);

            List<ScheduleDTO> ScheduleDTO_list = new ArrayList<>();
            for (ScheduleEntity schedule : schedules) {
                ScheduleDTO scheduleDTO = ScheduleDTO(schedule);
                ScheduleDTO_list.add(scheduleDTO);
            }
            return ScheduleDTO_list;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Pet schedule does not exists with id " + petId, e);
        }


        //throw new UnsupportedOperationException();
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        List<ScheduleEntity> all_schedules = null;
        try {
            Employee employeeById = userService.getEmployeeById(employeeId);
            all_schedules = scheduleService.findScheduleForEmployee(employeeById);

            List<ScheduleDTO> ScheduleDTO_list = new ArrayList<>();
            for (ScheduleEntity schedule : all_schedules) {
                ScheduleDTO scheduleDTO = ScheduleDTO(schedule);
                ScheduleDTO_list.add(scheduleDTO);
            }
            return ScheduleDTO_list;

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Employee schedule does not exists with id " + employeeId, e);
        }

//        return list;
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {

        Customer customer = userService.getCustomerById(customerId);
        List<Pet> pets = petService.findPetsByOwner(customer);

        List<ScheduleEntity> all_schedules = null;
        try {
            all_schedules = scheduleService.getSchedulesByPet(pets);

            List<ScheduleDTO> ScheduleDTO_list = new ArrayList<>();
            for (ScheduleEntity schedule : all_schedules) {
                ScheduleDTO scheduleDTO = ScheduleDTO(schedule);
                ScheduleDTO_list.add(scheduleDTO);
            }
            return ScheduleDTO_list;

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Owner schedule does not exists with id " + customerId, e);
        }

        //throw new UnsupportedOperationException();
    }
}
