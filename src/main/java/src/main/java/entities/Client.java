package src.main.java.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
@Entity
@DiscriminatorValue("ROLE_CLIENT")
public class Client extends Utilisateur implements Serializable {

	public Client() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Client(String nom, String prenom, String cin, String adresse, LocalDate dateDeNaissance, String mdp, String email,
			String tel) {
		super(nom, prenom, cin, adresse, dateDeNaissance, mdp, email, tel);
		// TODO Auto-generated constructor stub
	}
	
	

}
