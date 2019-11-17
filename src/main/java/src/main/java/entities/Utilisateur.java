package src.main.java.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.persistence.JoinColumn;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="TYPE_CPTE")

public class Utilisateur implements Serializable {
	
	@Id 
	@NotNull(message="CIN est obligatoire ")
	private String cin;
	@NotNull(message="Nom est obligatoire ")
	private String nom;
	@NotNull(message="Prenom est obligatoire ")
	private String prenom;
	@NotNull(message="Adresse email invalide ")
	private String email;
	@NotNull(message="Numero de telephone invalide ")
	private String tel;
	@NotNull(message="Adresse invalide ")
	private String adresse;
	//@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate DateDeNaissance;
	@NotNull(message="Le mot de passe doit contenir au mois 5 caracteres")
	private String mdp;
	public Boolean active;
	@OneToMany(mappedBy="utilisateur", fetch=FetchType.LAZY)
	private Collection<RendezVous> rendezvous;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "id_user"), inverseJoinColumns = @JoinColumn(name = "id_role"))
	private Set<Role> roles;
	
	
	public Utilisateur() {
		
	}
	public Utilisateur(String nom, String prenom, String cin, String adresse, LocalDate dateDeNaissance, String mdp,String email,String tel) {
		
		this.cin = cin;
		this.nom = nom;
		this.prenom = prenom;
		
		this.adresse = adresse;
		this.DateDeNaissance = dateDeNaissance;
		this.mdp = mdp;
	}
	
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getCin() {
		return cin;
	}
	public void setCin(String cin) {
		this.cin = cin;
	}
	public String getAdresse() {
		return adresse;
	}
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	
	public String getMdp() {
		return mdp;
	}
	public void setMdp(String mdp) {
		this.mdp = mdp;
	}
	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public LocalDate getDateDeNaissance() {
		return DateDeNaissance;
	}
	public void setDateDeNaissance(LocalDate dateDeNaissance) {
		DateDeNaissance = dateDeNaissance;
	}
	
	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
	
	
	

}
