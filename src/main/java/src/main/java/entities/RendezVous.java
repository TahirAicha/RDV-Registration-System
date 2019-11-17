package src.main.java.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.type.descriptor.sql.NumericTypeDescriptor;
import org.springframework.format.annotation.DateTimeFormat;
@Entity
public class RendezVous implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	@ManyToOne
    @JoinColumn(name="utiId")
	private Utilisateur utilisateur;
	@ManyToOne
    @JoinColumn(name="serviceId")
	private Service service;
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'hh:mm")
	private Date dateRdv;
	private NumericTypeDescriptor heure;
	private int statut;
	private int confirmation;
	public RendezVous() {
		super();
	}
	public RendezVous(int id, Utilisateur utilisateur, Service service, Date dateRdv,
			int statut, int confirmation) {
		super();
		this.id = id;
		this.utilisateur = utilisateur;
		this.service = service;
		this.dateRdv = dateRdv;

		this.statut = statut;
		this.confirmation = confirmation;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Utilisateur getUtilisateur() {
		return utilisateur;
	}
	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}
	public Service getService() {
		return service;
	}
	public void setService(Service service) {
		this.service = service;
	}
	public Date getDateRdv() {
		return dateRdv;
	}
	public void setDateRdv(Date dateRdv) {
		this.dateRdv = dateRdv;
	}
	
	public int getStatut() {
		return statut;
	}
	public void setStatut(int statut) {
		this.statut = statut;
	}
	public int getConfirmation() {
		return confirmation;
	}
	public void setConfirmation(int confirmation) {
		this.confirmation = confirmation;
	}
	
	
	
	

}
