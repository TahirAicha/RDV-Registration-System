package src.main.java.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.mapping.Collection;



@Entity
public class Categorie implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;

	private String designation;
	
	@OneToMany(mappedBy="categorie",fetch=FetchType.LAZY)
    private java.util.Collection<Service> servces;
	public Categorie() {
		
	}
	public Categorie(String designation) {
		
		this.designation = designation;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	
	

}
