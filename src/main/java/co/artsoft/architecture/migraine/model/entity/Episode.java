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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "Episodio")
public class Episode  {
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;	
	@JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
	@Column(name = "Fecha")
	private Timestamp date;	
	@Column(name = "nivelDolor")
	private int painLevel;
	@Column(name = "urlAudio")
	private String audioPath;
	@Column(name = "patronSuenio")
    private String sleepPattern;
    
    @ManyToMany(cascade = CascadeType.ALL)    
	@JoinTable(name = "AlimentoPorEpisodio",
		joinColumns = @JoinColumn(name = "idEpisodio", referencedColumnName = "id"),
		inverseJoinColumns = @JoinColumn(name = "idAlimento", referencedColumnName = "id"))
	private Set<Food> foods = new HashSet<Food>();
    
    @ManyToMany(cascade = CascadeType.ALL)   	
   	@JoinTable(name = "LocalizacionPorEpisodio",
   		joinColumns = @JoinColumn(name = "idEpisodio", referencedColumnName = "id"),
   		inverseJoinColumns = @JoinColumn(name = "idLocalizacion", referencedColumnName = "id"))
   	private Set<Location> locations = new HashSet<Location>();

    @ManyToMany(cascade = CascadeType.ALL)   	
   	@JoinTable(name = "MedicamentoPorEpisodio",
   		joinColumns = @JoinColumn(name = "idEpisodio", referencedColumnName = "id"),
   		inverseJoinColumns = @JoinColumn(name = "idMedicamento", referencedColumnName = "id"))
   	private Set<Medicine> medicines = new HashSet<Medicine>();
    
    @ManyToMany(cascade = CascadeType.ALL)   	
   	@JoinTable(name = "ActividadFisicaPorEpisodio",
   		joinColumns = @JoinColumn(name = "idEpisodio", referencedColumnName = "id"),
   		inverseJoinColumns = @JoinColumn(name = "idActividadFisica", referencedColumnName = "id"))
   	private Set<PhysicalActivity> physicalActivity = new HashSet<PhysicalActivity>();
    
    @ManyToOne
	@JoinColumn (name="idUsuario")
	@JsonBackReference
	private User user;
    
    @OneToMany(mappedBy = "episode")
	private Set<Diagnostic> diagnostics = new HashSet<Diagnostic>();
    
    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Timestamp  getDate() {
		return date;
	}

	public void setDate(Timestamp fecha) {
		this.date = fecha;
	}

	public int getPainLevel() {
		return painLevel;
	}

	public void setPainLevel(int nivelDolor) {
		this.painLevel = nivelDolor;
	}

	public String getAudioPath() {
		return audioPath;
	}

	public void setAudioPath(String audioPath) {
		this.audioPath = audioPath;
	}

	public String getSleepPattern() {
		return sleepPattern;
	}

	public void setSleepPattern(String sleepPattern) {
		this.sleepPattern = sleepPattern;
	}

	public Set<Food> getFoods() {
		return foods;
	}

	public void setFoods(Set<Food> alimentos) {
		this.foods = alimentos;
	}
	
	public Set<Location> getLocations() {
		return locations;
	}

	public void setLocations(Set<Location> locations) {
		this.locations = locations;
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
