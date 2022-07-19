package com.udacity.jdnd.course3.critter.controllers.services;

import com.udacity.jdnd.course3.critter.dataTransfer_Objects.EmployeeDTO;
import com.udacity.jdnd.course3.critter.dataTransfer_Objects.EmployeeRequestDTO;
import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.entities.Employee;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.repositories.CustomerREPO;
import com.udacity.jdnd.course3.critter.repositories.EmployeeREPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserService {

    @Autowired
    private CustomerREPO customerREPO;
    @Autowired
    private EmployeeREPO employeeREPO;

    public Customer saveCustomer(Customer customer) {
        return customerREPO.save(customer);
    }

    public List<Customer> getAllCustomers() {
        return customerREPO.findAll();
    }

    public Customer getOwnerByPet(Pet pet) {
        return customerREPO.findOwnerByPets(pet);

    }

    public Employee saveEmployee(Employee employee) {
        return employeeREPO.save(employee);

    }

    public Employee getEmployeeById(Long employeeId) throws UserException {
        return employeeREPO.findById(employeeId).orElseThrow(() -> new UserException());
    }

    public Customer getCustomerById(Long customerId)  {
        try{
            return customerREPO.findById(customerId).orElseThrow(() -> new UserException());
        }catch (Exception e){
            return  null;
        }

    }

    public void setAvailability(Set<DayOfWeek> daysAvailable, Long employeeId)  {
        try{
            Employee employee = employeeREPO.findById(employeeId).<UserException>orElseThrow(() -> new UserException());
            //avalability
            employee.setAvailability(daysAvailable);
            employeeREPO.save(employee);
        } catch (UserException e) {
            //e.printStackTrace();
        }
    }

    public List<Employee> findAvailableEmployeesForService(EmployeeRequestDTO employeeRequestDTO) {
        List<Employee> employeess = employeeREPO.findEmployeesByDaysAvailableAndSkillsIn(employeeRequestDTO.getDate().getDayOfWeek(), employeeRequestDTO.getSkills());
        List<Employee> AvailableEmployees = new ArrayList<>();
        employeess.forEach(employee -> {
            if(employee.getSkills().containsAll(employeeRequestDTO.getSkills())) {
                AvailableEmployees.add(employee);
            }
        });
        return AvailableEmployees;
    }
}
