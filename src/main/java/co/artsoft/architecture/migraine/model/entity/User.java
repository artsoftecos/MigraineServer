package co.artsoft.architecture.migraine.model.entity;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;



@Entity
@Table(name = "Usuario")
public class User {

	@Id
	@Column(name = "document_id", unique = true, nullable = false, length = 45)
	private String documentId;
	
	@Column(name = "password", nullable = false, length = 60)
	private String password;
	
	@Column(name = "token", nullable = false, length = 45)
	private String token;
	
	@Column(name = "enabled", nullable = false)
	private boolean enabled;
	
	@OneToOne(fetch = FetchType.EAGER, mappedBy = "user")
	private UserType userType;
	
	@OneToOne(mappedBy="user")
	private Patient patient;
	
	@OneToOne(mappedBy="user")
	private Doctor doctor;

	public User(String documentId, String password, String token, boolean enabled, UserType userType) {
		super();
		this.documentId = documentId;
		this.password = password;
		this.token = token;
		this.enabled = enabled;
		this.userType = userType;
	}

	public User(String documentId, String password, boolean enabled) {
		super();
		this.documentId = documentId;
		this.password = password;
		this.enabled = enabled;
	}

	public User() {
	}

	public String getDocumentId() {
		return documentId;
	}

	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getToken() {
		return token;
	}

	public void setKey(String token) {
		this.token = token;
	}
	
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
    
	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}
	
	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}
}
