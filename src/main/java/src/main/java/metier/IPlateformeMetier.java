package src.main.java.metier;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import src.main.java.entities.Categorie;
import src.main.java.entities.Employe;
import src.main.java.entities.RendezVous;
import src.main.java.entities.Service;
import src.main.java.entities.Utilisateur;

public interface IPlateformeMetier {
	//User
	
		public Utilisateur getUser(String cin);
		public Utilisateur findUserByEmail(String email);
		public List<Utilisateur> getAllUsers();
		public void SupprimerUtilisateur(String cin);
		void addUser(String cin, String nom, String prenom, String adresse, LocalDate dn, String mdp, Boolean active,
				String email, String tel);
		public Utilisateur findAllBytype(String type);
		public int getUserRole();
		String currentUser();
	//Services	
		public Service getService(long id);
		public List<Service> getAllServices();
		public List<Service> getServices();
		
		public void SupprimerService(long id);
		void addService(Categorie cat, String designation, int duree,double  prix);
		public List<Service> mesServices(); 
		public List<Service> getService(String designation, Categorie categorie);
				
	//RDV
		public RendezVous getRdv(long id);
		public List<RendezVous> getAllRdv();
		public void SupprimerRdv(long id);
		public List<RendezVous> rdvParEmpC();
		public List<RendezVous> rdvParEmpNC();
		List<RendezVous> mesRdvConfirmes();
		public List<RendezVous> mesRdv();
		void addRdv(Utilisateur u, Service service,  Date dr );
		void ConfirmerRdv(long id);
		
	//Categorie
		public Categorie getCat(long id);
		public List<Categorie> getAllCat();
		public void SupprimerCat(long id);
		void addCat(String designation );
		public void addUser(@Valid Utilisateur user);
		public void addEmploye(@Valid Employe user);
		
		
		
		
		
		
		
}
