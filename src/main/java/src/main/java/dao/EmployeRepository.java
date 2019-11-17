package src.main.java.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import src.main.java.entities.Employe;

public interface EmployeRepository extends JpaRepository<Employe, String >{
	Employe findByEmail(String email);
		
	
}