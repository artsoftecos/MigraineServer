package co.artsoft.architecture.migraine.model.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "Paciente")
public class Patient {
	
	@Id
	@Column(name = "NumeroDocumento")
	private String documentNumber;
	
	@Column(name = "Nombre")
	private String name;
	
	@Column(name = "Telefono")
	private String telephone;
	
	@OneToOne
	@JoinColumn(name="idUsuario")
	@JsonBackReference(value = "Paciente-Usuario")
	private User user;
	
	@OneToMany(mappedBy = "patient")
	private Set<Episode> episodes = new HashSet<Episode>();
		
	public String getDocumentNumber() {
		return documentNumber;
	}

	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Episode> getEpisodes() {
		return episodes;
	}

	public void setEpisodes(Set<Episode> episodes) {
		this.episodes = episodes;
	}
	
	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
