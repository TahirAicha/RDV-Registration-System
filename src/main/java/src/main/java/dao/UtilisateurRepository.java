package src.main.java.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import src.main.java.entities.Employe;
import src.main.java.entities.RendezVous;
import src.main.java.entities.Utilisateur;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, String >{
	Utilisateur findByEmail(String email);
		
	Employe findEmpByEmail(String email);
	//Role utilisateur actuel 
	@Query(value="select ur.id_role from utilisateur u inner join user_role ur on (u.cin = ur.id_user) where u.email=?",nativeQuery=true) int role(Utilisateur u);
	//Utilisateur findByEmailIdIgnoreCase(String email);
}
