package com.udacity.jdnd.course3.critter.repositories;

import com.udacity.jdnd.course3.critter.entities.Employee;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;


@Repository
@Transactional
public interface EmployeeREPO extends JpaRepository<Employee, Long> {


    List<Employee> findByIdIn(List<Long> employeeIds);

    List<Employee> findEmployeesByDaysAvailableAndSkillsIn(DayOfWeek daysAvailable, Set<EmployeeSkill> skills);

}
