package src.main.java.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
@Entity
@DiscriminatorValue("ROLE_EMP")
public class Employe extends Utilisateur implements Serializable {
	
	@ManyToOne
    @JoinColumn(name="serviceId")
	private Service service;
	private String matricule;
	public Employe(Service service) {
		 
	}

	public Service getService() {
		return service;
	}

	public void setService(Service service) {
		this.service = service;
	}

	public String getMatricule() {
		return matricule;
	}

	public void setMatricule(String matricule) {
		this.matricule = matricule;
	}

	public Employe() {
	}

	public Employe(String nom, String prenom, String cin, String adresse, LocalDate dateDeNaissance, String mdp,
			String email, String tel,Service service) {
		super(nom, prenom, cin, adresse, dateDeNaissance, mdp, email, tel);
		this.service = service;
		
	}
	
	

}
