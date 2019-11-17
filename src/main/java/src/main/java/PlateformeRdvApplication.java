package src.main.java;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import src.main.java.dao.CategorieRepository;
import src.main.java.dao.ServiceRepository;
import src.main.java.dao.UtilisateurRepository;
import src.main.java.entities.Categorie;
import src.main.java.entities.Client;
import src.main.java.entities.Employe;
import src.main.java.entities.Service;
import src.main.java.entities.Utilisateur;
import src.main.java.metier.IPlateformeMetier;


@SpringBootApplication()
public class PlateformeRdvApplication implements CommandLineRunner {
	BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
	
	@Autowired
	private UtilisateurRepository utilisateurRepository;
	@Autowired
	private CategorieRepository categorieRepository;
	@Autowired
	private IPlateformeMetier iPlateformeMetier;
	@Autowired
	private ServiceRepository serviceRepository;
	public static void main(String[] args) {
		
		 SpringApplication.run(PlateformeRdvApplication.class, args);
		// ctx.getBean(UtilisateurRepository.class);
	}

	@Override
	public void run(String... args) throws Exception {
		

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = format.parse("2012-12-13 14:54:30");
		//Client c1= utilisateurRepository.save(new Client("MC247448", "TAHIR","j","adresse" ,date,bCryptPasswordEncoder.encode("mdp"),"a@a.com","09867643" ));
		Categorie c= categorieRepository.save(new Categorie("cat"));
		//Service s = serviceRepository.save(new Service(c,"des",2,22));
		
		//Employe e= utilisateurRepository.save(new Employe("MC247448", "TAHIR","s","adresse" ,date,"mdp","a@a.com","09867643",s));
				//Client c1= utilisatuertRepository.save(new Client("MC247448", "TAHIR","j","adresse" ,date,"mdp","a@a.com","09867643" ));
		//Employe c1= utilisatuertRepository.save(new Employe("MC247448", "TAHIR","j","adresse" ,date,"mdp","a@a.com","09867643","Sante ));
		Utilisateur u= iPlateformeMetier.getUser("j");
		
		System.out.println("****************************/n **********"+u.getPrenom());
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		System.out.println(bCryptPasswordEncoder.encode("mdp"));   
		System.out.println(bCryptPasswordEncoder.encode("123"));   
	}

}
