package co.artsoft.architecture.migraine.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "TipoUsuario", uniqueConstraints = @UniqueConstraint(
		columnNames = {"type", "document_id"}))
public class UserType {
	
	@Id
	@GeneratedValue
	@Column(name = "user_type_id", unique = true, nullable = false)
	private Integer userTypeId;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "document_id", nullable = false)
	private User user;
	
	@Column(name = "type", nullable = false, length = 45)
	private String type;
	
	public UserType(User user, String type) {
		super();
		this.user = user;
		this.type = type;
	}

	//El constructor por defecto lo usa hybernate, lo requiere
	public UserType(){
		
	}

	public Integer getUserTypeId() {
		return userTypeId;
	}

	public void setUserTypeId(Integer userTypeId) {
		this.userTypeId = userTypeId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
	
}
