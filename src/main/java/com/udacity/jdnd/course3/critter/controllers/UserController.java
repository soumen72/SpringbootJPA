package com.udacity.jdnd.course3.critter.controllers;

import com.udacity.jdnd.course3.critter.controllers.services.PetService;
import com.udacity.jdnd.course3.critter.controllers.services.UserService;
import com.udacity.jdnd.course3.critter.dataTransfer_Objects.CustomerDTO;
import com.udacity.jdnd.course3.critter.dataTransfer_Objects.EmployeeDTO;
import com.udacity.jdnd.course3.critter.dataTransfer_Objects.EmployeeRequestDTO;
import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.entities.Employee;
import com.udacity.jdnd.course3.critter.entities.Pet;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {


    @Autowired
    private UserService userService;
    @Autowired
    private PetService petService;





    //convertions
    public static EmployeeDTO toEmployeeDTO(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();

        employeeDTO.setId(employee.getId());
        employeeDTO.setName(employee.getName());
        employeeDTO.setSkills(employee.getSkills());
        employeeDTO.setDaysAvailable(employee.getAvailability());

        return employeeDTO;


    }
    private CustomerDTO CustomerDTO(Customer customer) {

        CustomerDTO customer_DTO = null;
        if ( customer != null ) {

            customer_DTO = new CustomerDTO();

            customer_DTO.setId(customer.getId());
            customer_DTO.setName(customer.getName());
            customer_DTO.setPhoneNumber(customer.getPhone());
            customer_DTO.setNotes(customer.getNotes());

            if (customer.getPets() == null) {
                customer_DTO.setPetIds(null);
            }
            else {
                customer_DTO.setPetIds(customer.getPets().stream().map(p -> {
                    Long id = p.getId();
                    return id;
                }).collect(Collectors.toList()));

            }
        }
        return customer_DTO;
    }
    //convertions



    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        //throw new UnsupportedOperationException();
        //Customer customerE = DTOToCustomer(customerDTO);

        Customer customerE = new Customer();
        customerE.setName(customerDTO.getName());
        customerE.setPhone(customerDTO.getPhoneNumber());
        customerE.setNotes(customerDTO.getNotes());
        ////////////////////////////
        //List<Long> petId = customerDTO.getPetIds();
        List<Pet> pets;
        if (customerDTO.getPetIds() == null) {
            pets = null;
        }
        else {


            Stream<Pet> petStream = customerDTO.getPetIds().stream().map(id -> {
                Pet pet = petService.getPetById(id);
                return pet;
            });

            pets = petStream.collect(Collectors.toList());

        }

        customerE.setPets(pets);
        customerE = userService.saveCustomer(customerE);
        customerDTO.setId(customerE.getId());
        return customerDTO;
    }



    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        List<Customer> customers = userService.getAllCustomers();
        List<CustomerDTO> res;

        Stream<CustomerDTO> ans1 = customers.stream().map((Customer c) -> {
            CustomerDTO customerDTO = CustomerDTO(c);
            return customerDTO;
        });

        res=ans1.collect(Collectors.toList());

        return res;
        //throw new UnsupportedOperationException();
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){

        //error
        Pet pet = null;
        try {
            pet = petService.getPetById(petId);
            Customer ownerOfPet = userService.getOwnerByPet(pet);
            CustomerDTO customerDTO = CustomerDTO(ownerOfPet);
            return  customerDTO;


        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Owner id not found ***********" + petId, e);
        }
    }



    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {

        //Employee employee = new Employee();
//
//        //throw new UnsupportedOperationException();
        Employee employee = new Employee();
        employee.setName(employeeDTO.getName());
        employee.setSkills(employeeDTO.getSkills());
        employee.setAvailability(employeeDTO.getDaysAvailable());

        EmployeeDTO employee_DTO = toEmployeeDTO(userService.saveEmployee(employee));
        return  employee_DTO;


//
//
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {

        Employee employee = null;
        try {
            employee = userService.getEmployeeById(employeeId);
            return toEmployeeDTO(employee);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Employee id not found " + employeeId, e);
        }
        //throw new UnsupportedOperationException();
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
       // throw new UnsupportedOperationException();

        userService.setAvailability(daysAvailable, employeeId);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeRequestDTO) {
        //throw new UnsupportedOperationException();

        List<Employee> employees = userService.findAvailableEmployeesForService(employeeRequestDTO);
        List<EmployeeDTO> list = new ArrayList<>();
        for (Employee employee : employees) {
            //toEmployeeDTO
            EmployeeDTO emp_dto = toEmployeeDTO(employee);
            list.add(emp_dto);
        }
         return list;

//       List<Employee> employees = userService.findEmployeesForService(employeeRequestDTO);
//        List<EmployeeDTO> list = new ArrayList<>();

//        for (Employee employee : employees) {
//            EmployeeDTO emp_dto = toEmployeeDTO(employee);
//            list.add(emp_dto);
//        }
//        return list;

    }



}
