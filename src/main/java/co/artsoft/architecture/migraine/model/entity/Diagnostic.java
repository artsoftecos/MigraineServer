package co.artsoft.architecture.migraine.model.entity;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "Diagnostico")
public class Diagnostic {
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;	
	@JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")	
	@Column(name = "Fecha")
	private Timestamp date;	
	
	 @ManyToMany(cascade = CascadeType.ALL)
	 @JoinTable(name = "AlimentoDiagnostico",
	 joinColumns = @JoinColumn(name = "idDiagnostico", referencedColumnName = "id"),
	 inverseJoinColumns = @JoinColumn(name = "idAlimento", referencedColumnName = "id"))
	 private Set<Food> foods = new HashSet<Food>();
	 	
	 @ManyToMany(cascade = CascadeType.ALL)
	 @JoinTable(name = "MedicamentoDiagnostico",
	   joinColumns = @JoinColumn(name = "idDiagnostico", referencedColumnName = "id"),
	   		inverseJoinColumns = @JoinColumn(name = "idMedicamento", referencedColumnName = "id"))
	 private Set<Medicine> medicines = new HashSet<Medicine>();
	 
	 @ManyToMany(cascade = CascadeType.ALL)	 
	 @JoinTable(name = "ActividadFisicaDiagnostico",
	 	joinColumns = @JoinColumn(name = "idDiagnostico", referencedColumnName = "id"),
	 	inverseJoinColumns = @JoinColumn(name = "idActividadFisica", referencedColumnName = "id"))
	 private Set<PhysicalActivity> physicalActivity = new HashSet<PhysicalActivity>();
	    
	 @ManyToOne
	 @JoinColumn (name="idEpisodio")
	 @JsonBackReference(value = "Episodio-Diagnostico")
	 private Episode episode;
	 
	@ManyToOne
	@JsonBackReference
	@JoinColumn (name="idUsuario")
	private User user;
	 
	 public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}
	
	public Set<Food> getFoods() {
		return foods;
	}

	public void setFoods(Set<Food> foods) {
		this.foods = foods;
	}

	public Set<Medicine> getMedicines() {
		return medicines;
	}

	public void setMedicines(Set<Medicine> medicines) {
		this.medicines = medicines;
	}

	public Set<PhysicalActivity> getPhysicalActivity() {
		return physicalActivity;
	}

	public void setPhysicalActivity(Set<PhysicalActivity> physicalActivity) {
		this.physicalActivity = physicalActivity;
	}

	public Episode getEpisode() {
		return episode;
	}

	public void setEpisode(Episode episode) {
		this.episode = episode;
	}
}
