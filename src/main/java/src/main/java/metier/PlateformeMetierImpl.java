package src.main.java.metier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import src.main.java.dao.CategorieRepository;
import src.main.java.dao.EmployeRepository;
import src.main.java.dao.RDVRepository;
import src.main.java.dao.RoleRepository;
import src.main.java.dao.ServiceRepository;
import src.main.java.dao.UtilisateurRepository;
import src.main.java.entities.Categorie;
import src.main.java.entities.Employe;
import src.main.java.entities.RendezVous;
import src.main.java.entities.Role;
import src.main.java.entities.Utilisateur;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.validation.Valid;
@Service
@Transactional
public  class PlateformeMetierImpl implements IPlateformeMetier {

	//Users
	BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
	
	@Autowired
	private UtilisateurRepository utilisateurRepository;
	@Autowired
	private EmployeRepository employeRepository;
	@Autowired
	private RDVRepository rdvRepository;
	@Autowired 
	ServiceRepository serviceRepository;
	@Autowired 
	CategorieRepository catRepository;
	@Autowired
	RoleRepository roleRepository;
	
	//Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	@Override
	public String currentUser() {
		 String username = null;
		 Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		 if (principal instanceof UserDetails) {
		  username = ((UserDetails)principal).getUsername();
		 } else {
		    username = principal.toString();
		 }
		 return username;
	}
	
	@Override
	public Utilisateur getUser(String cin) {
		Utilisateur user= utilisateurRepository.findById(cin).orElse(null);
		if(user==null) throw new RuntimeException("Utilisateur introuvable");
		return user;
	}

	
	@Override
	@Query("SELECT u FROM Utilisateur u WHERE u.active = 'oui'")
	public List<Utilisateur> getAllUsers() {
		
		return utilisateurRepository.findAll();
	}

	@Override
	public void SupprimerUtilisateur(String cin) {
		utilisateurRepository.deleteById(cin);
		
	}

	@Override
	public void addUser(String cin, String nom, String prenom, String adresse, LocalDate dn, String mdp, Boolean active,
			String email, String tel) {
		
		
		Utilisateur u = new Utilisateur();
		
		Role userRole = roleRepository.findByRole("ADMIN");
		u.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
		u.setCin(cin);
		u.setNom(nom);
		u.setPrenom(prenom);
		u.setAdresse(adresse);
		u.setMdp(bCryptPasswordEncoder.encode(mdp));
		u.setDateDeNaissance(dn);
		
		u.setActive(active);
		u.setTel(tel);
		u.setEmail(email);
		
		utilisateurRepository.save(u);
		
		
	}

	//Services
	
	@Override
	public src.main.java.entities.Service getService(long id) {
		src.main.java.entities.Service service= serviceRepository.findById(id).orElse(null);
		if(service==null) throw new RuntimeException("service introuvable");
		return service;
	}


	@Override
	public List<src.main.java.entities.Service> getAllServices() {
		return serviceRepository.findAll();
	}


	@Override
	public void SupprimerService(long id) {
		serviceRepository.deleteById(id);
		
	}


	@Override
	public void addService(Categorie cat, String designation, int duree, double prix) {
		
		src.main.java.entities.Service service = new src.main.java.entities.Service();
		System.out.println("********new service");
		
		service.setCategorie(cat);
		service.setDesignation(designation);
		service.setDuree(duree);
		service.setPrix(prix);
		
		Employe u= employeRepository.findByEmail(currentUser());
		service.setEmploye(u);
		u.setService(service);
		serviceRepository.saveAndFlush(service);
		utilisateurRepository.save(u);
		
		
		
	}
	//multi fields search bar
	@Override
	public List<src.main.java.entities.Service> getService(String designation, Categorie categorie) {
		return serviceRepository.getSerivce(designation , categorie);
	}


  ///RDV
	
	
	@Override
	public RendezVous getRdv(long id) {
		RendezVous rdv = rdvRepository.findById(id).orElse(null);
		return rdv;
	}


	@Override
	public List<RendezVous> getAllRdv() {
		
		return rdvRepository.findAll();
	}
	
	@Override
	public void SupprimerRdv(long id) {
		rdvRepository.deleteById(id);
		
	}
	@Override
	public void ConfirmerRdv(long id) {
		RendezVous rdv= getRdv(id);
		rdv.setConfirmation(1);
		rdvRepository.save(rdv);
		
	}


	@Override
	public void addRdv(Utilisateur utilisateur, src.main.java.entities.Service service, Date dr) {
		RendezVous rdv = new RendezVous();
		
		System.out.println("RDV ");
		utilisateur= utilisateurRepository.findByEmail(currentUser());
		rdv.setUtilisateur(utilisateur);
		rdv.setService(service);
		rdv.setDateRdv(dr);
		rdv.setStatut(1);
		rdv.setConfirmation(0);
		
		rdvRepository.saveAndFlush(rdv);
		System.out.println("RDV SAVED");
		
		
	}
	
//Categorie


	@Override
	public Categorie getCat(long id) {
		return catRepository.findById(id).orElse(null);
	}


	@Override
	public List<Categorie> getAllCat() {
		return catRepository.findAll(); 
	}


	@Override
	public void SupprimerCat(long id) {
		catRepository.deleteById(id);
		
	}


	@Override
	public void addCat(String designation) {
		Categorie cat = new Categorie();
		cat.setDesignation(designation);
		catRepository.save(cat);
		
	}


	@Override
	public Utilisateur findAllBytype(String type) {
		return   null;
	}


	@Override
	public Utilisateur findUserByEmail(String email) {
		return utilisateurRepository.findByEmail(email);
	}


	@Override
	public void addUser(@Valid Utilisateur u) {
		
		Role userRole = roleRepository.findByRole("CLIENT");
		u.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
		
		
		utilisateurRepository.save(u);
		
	}

	@Override
	public void addEmploye(@Valid Employe emp) {
		Role userRole = roleRepository.findByRole("EMPLOYE");
		emp.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
		
		
		utilisateurRepository.save(emp);
		
	}
	//RDV de chaque client
	@Override
	public List<RendezVous> mesRdv() {
		Utilisateur u= utilisateurRepository.findByEmail(currentUser());

		return rdvRepository.mesRdv(u);
	}
	@Override
	public List<RendezVous> mesRdvConfirmes() {
		Utilisateur u= utilisateurRepository.findByEmail(currentUser());

		return rdvRepository.mesRdvConfirmes(u);
	}
	//RDV de chaque emp
	@Override
	public List<RendezVous> rdvParEmpC() {
		// TODO Auto-generated method stub
		Employe u= utilisateurRepository.findEmpByEmail(currentUser());
		return  rdvRepository.rdvParEmpC(u);
	}

	@Override
	public List<RendezVous> rdvParEmpNC() {
		Employe u= utilisateurRepository.findEmpByEmail(currentUser());


		return rdvRepository.rdvParEmpNC(u); 
	}

	@Override
	public List<src.main.java.entities.Service> mesServices() {
		Utilisateur u= utilisateurRepository.findByEmail(currentUser());
		return serviceRepository.mesServices(u);
	}

	@Override
	public int getUserRole() {
		Utilisateur u= utilisateurRepository.findByEmail(currentUser());
		return utilisateurRepository.role(u);
	}

	@Override
	public List<src.main.java.entities.Service> getServices() {
		// TODO Auto-generated method stub
		return serviceRepository.findAll();
	}

	
	

	
	
	
 
	

}
