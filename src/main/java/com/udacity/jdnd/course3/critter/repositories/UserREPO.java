package com.udacity.jdnd.course3.critter.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import com.udacity.jdnd.course3.critter.entities.ParentHumanoid;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface UserREPO extends JpaRepository<ParentHumanoid, Long> {

}
