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
@Table(name = "Doctor")
public class Doctor {
	
	@Id
	@Column(name = "Codigo")
	private String code;
		
	@Column(name = "Nombre")
	private String name;
	
	@Column(name = "TelefonoOficina")
	private String telephoneOffice;
	
	@OneToOne
	@JoinColumn(name="NumeroDocumento", unique = true)
	@JsonBackReference(value = "Doctor-Usuario")
	private User user;
	
	@OneToMany(mappedBy = "doctor")
	private Set<Diagnostic> diagnostics = new HashSet<Diagnostic>();
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTelephoneOffice() {
		return telephoneOffice;
	}

	public void setTelephoneOffice(String telephoneOffice) {
		this.telephoneOffice = telephoneOffice;
	}
	
	public Set<Diagnostic> getDiagnostics() {
		return diagnostics;
	}

	public void setDiagnostics(Set<Diagnostic> diagnostics) {
		this.diagnostics = diagnostics;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
