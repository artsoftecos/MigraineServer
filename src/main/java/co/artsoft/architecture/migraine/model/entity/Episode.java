package co.artsoft.architecture.migraine.model.entity;

import java.sql.Date;
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
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "Episodio")
public class Episode {
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;	
	@JsonFormat(pattern = "YYYY-MM-dd")
	@Column(name = "Fecha")
	private Date date;	
	@Column(name = "nivelDolor")
	private int painLevel;
	@Column(name = "urlAudio")
	private String audioPath;
	@Column(name = "patronSuenio")
    private String sleepPattern;
    
    @ManyToMany(cascade = CascadeType.ALL)
	@JsonBackReference
	@JoinTable(name = "AlimentoPorEpisodio",
		joinColumns = @JoinColumn(name = "idEpisodio", referencedColumnName = "id"),
		inverseJoinColumns = @JoinColumn(name = "idAlimento", referencedColumnName = "id"))
	private Set<Food> foods = new HashSet<Food>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date fecha) {
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
    
    /*@ManyToMany(cascade = CascadeType.ALL)
   	@JsonBackReference
   	@JoinTable(name = "migraine_foods",
   		joinColumns = @JoinColumn(name = "migraine_id", referencedColumnName = "id"),
   		inverseJoinColumns = @JoinColumn(name = "food_id", referencedColumnName = "id"))
   	private Set<Location> locations = new HashSet<Location>();

    @ManyToMany(cascade = CascadeType.ALL)
   	@JsonBackReference
   	@JoinTable(name = "migraine_meds",
   		joinColumns = @JoinColumn(name = "migraine_id", referencedColumnName = "id"),
   		inverseJoinColumns = @JoinColumn(name = "med_id", referencedColumnName = "id"))
   	private Set<Medicine> medicines = new HashSet<Medicine>();
    
    @ManyToMany(cascade = CascadeType.ALL)
   	@JsonBackReference
   	@JoinTable(name = "migraine_physical_activities",
   		joinColumns = @JoinColumn(name = "migraine_id", referencedColumnName = "id"),
   		inverseJoinColumns = @JoinColumn(name = "physicalActivity_id", referencedColumnName = "id"))
   	private Set<PhysicalActivity> PhysicalActivity = new HashSet<PhysicalActivity>();
    */
}
