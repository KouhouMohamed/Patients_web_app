package com.kouhou.springMVC.repositories;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kouhou.springMVC.entities.Patient;



public interface PatientRepository extends JpaRepository<Patient, Long>{

	public Page<Patient> findByNameContains(String name, Pageable pageable);
	public Page<Patient> findByNameContainsAndSick(String name, boolean isSick, Pageable pageable);
	
	//Function to update patients
	@Transactional
	@Modifying
	@Query("update Patient p set p.name= :name, p.dateBirth = :dateBirth, p.score= :score, p.sick= :sick where p.id= :id")
	public void update(@Param(value = "name") String name, @Param(value="dateBirth") Date dateBirth, @Param(value="score") int score,
			@Param("sick") boolean sick, @Param(value="id") Long id); 
	
}
	
