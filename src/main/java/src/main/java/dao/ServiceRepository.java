package src.main.java.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import src.main.java.entities.Categorie;
import src.main.java.entities.RendezVous;
import src.main.java.entities.Service;
import src.main.java.entities.Utilisateur;

public interface ServiceRepository extends JpaRepository<Service, Long > {
	@Query(value="SELECT * FROM Service s WHERE s.emp_id = ?",nativeQuery=true) List<Service>  mesServices(Utilisateur u);
	@Query(value="SELECT * FROM Service s WHERE s.categorie = ?",nativeQuery=true) List<Service>  ServicesParCategorie(Categorie c);
	@Query(value="SELECT * FROM Rendez_vous r WHERE r.emp_id = ?",nativeQuery=true) List<Service>  mesRdv(Utilisateur u);
	@Query(value="SELECT * FROM Service s WHERE s.designation = ? or s.categorie = ?",nativeQuery=true) List<Service> getSerivce(String designation, Categorie categorie);
	
}
