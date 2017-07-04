package co.artsoft.architecture.migraine.model.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "Usuario")
public class User {

	@Id
	@Column(name = "document_id", unique = true, nullable = false, length = 45)
	private String documentId;
	
	@Column(name = "password", nullable = false, length = 60)
	private String password;
	
	@Column(name = "key", nullable = false, length = 45)
	private String key;
	
	@Column(name = "enabled", nullable = false)
	private boolean enabled;
	
	@OneToOne(fetch = FetchType.EAGER, mappedBy = "user")
	private Set<UserType> userType = new HashSet<UserType>();

	public User(String documentId, String password, String key, boolean enabled, Set<UserType> userType) {
		super();
		this.documentId = documentId;
		this.password = password;
		this.key = key;
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

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Set<UserType> getUserType() {
		return userType;
	}

	public void setUserType(Set<UserType> userType) {
		this.userType = userType;
	}
	
	

}
