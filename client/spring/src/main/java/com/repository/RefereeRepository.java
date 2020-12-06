package com.repository;

import com.model.Referee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RefereeRepository extends JpaRepository<Referee, Long>{
	Referee findBySurname(String surname);
	Referee findByName(String name);
	
	@Query("SELECT r FROM Referee r WHERE LOWER(r.name) = LOWER(:name) AND LOWER(r.surname) = LOWER(:surname)") 
	Referee findByFullName(@Param("name") String name, @Param("surname") String surname);
}
