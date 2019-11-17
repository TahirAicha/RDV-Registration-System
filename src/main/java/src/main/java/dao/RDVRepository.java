package src.main.java.dao;

import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import src.main.java.entities.Employe;
import src.main.java.entities.RendezVous;
import src.main.java.entities.Service;
import src.main.java.entities.Utilisateur;

public interface RDVRepository extends JpaRepository<RendezVous, Long >{
	//lels rendez vous de chaque client (ou utilisateur)
	@Query(value="SELECT * FROM Rendez_vous rv WHERE rv.uti_id = ? and confirmation=0",nativeQuery=true) List<RendezVous> mesRdv(Utilisateur u);
	@Query(value="SELECT * FROM Rendez_vous rv WHERE rv.uti_id = ? and confirmation=1",nativeQuery=true) List<RendezVous> mesRdvConfirmes(Utilisateur u);
	
	//les RDV de chaque prestataire de service( ou chaque employe)
	@Query(value="SELECT * FROM Rendez_vous rv INNER JOIN Service s ON(rv.service_id=s.id)WHERE s.emp_id = ? and confirmation=1",nativeQuery=true) List<RendezVous> rdvParEmpC(Employe u);
	@Query(value="SELECT * FROM Rendez_vous rv INNER JOIN Service s ON(rv.service_id=s.id)WHERE s.emp_id = ? and confirmation=0",nativeQuery=true) List<RendezVous> rdvParEmpNC(Employe u);
	//Les services de chaque employe
	@Query(value="SELECT * FROM Rendez_vous rv WHERE rv.service_id = ?",nativeQuery=true) Service rdvParEmp(Service u);
	
}
