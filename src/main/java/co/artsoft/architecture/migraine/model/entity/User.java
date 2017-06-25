package co.artsoft.architecture.migraine.model.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "Usuario")
public class User {
	
	@Id
	@Column(name = "NumeroDocumento")
	private String documentNumber;
	
	@Column(name = "Nombre")
	private String name;
	
	@OneToMany(mappedBy = "user")
	private Set<Episode> episodes = new HashSet<Episode>();
	 
	
	@OneToMany(mappedBy = "user")
	private Set<Diagnostic> diagnostics = new HashSet<Diagnostic>();
		
	
	 @ManyToOne
	 @JoinColumn (name="idTipoUsuario")
	 @JsonBackReference(value = "Usuario-TipoUsuario")
	 private UserType userType;
	
	public Set<Diagnostic> getDiagnostics() {
		return diagnostics;
	}

	public void setDiagnostics(Set<Diagnostic> diagnostics) {
		this.diagnostics = diagnostics;
	}

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

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
}
