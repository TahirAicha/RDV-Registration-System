package src.main.java.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.ManyToAny;
@Entity
public class Service implements Serializable {
	@Id @GeneratedValue
	private Long id;
	@ManyToOne
    @JoinColumn(name="catId")
	private Categorie categorie;
	private String designation;
	private int duree;
	private double prix;
	@ManyToOne
    @JoinColumn(name="empId")
	private Employe employe;
	
	public Service() {}
	public Service(Categorie categorie, String designation, int duree, double prix) {
		
		this.categorie = categorie;
		this.designation=designation;
		this.duree = duree;
		this.prix = prix;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Categorie getCategorie() {
		return categorie;
	}
	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public int getDuree() {
		return duree;
	}
	public void setDuree(int duree) {
		this.duree = duree;
	}
	public double getPrix() {
		return prix;
	}
	public void setPrix(double prix) {
		this.prix = prix;
	}
	public Employe getEmploye() {
		return employe;
	}
	public void setEmploye(Employe employe) {
		this.employe = employe;
	}
	

}
