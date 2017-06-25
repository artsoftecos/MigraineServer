package co.artsoft.architecture.migraine.model.entity;

import java.util.HashSet;
import java.util.Set;

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

@Entity
@Table(name = "Localizacion")
public class Location {

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
		
	@Column(name = "nombre")
	private String name;
		
	@ManyToMany
	@JsonBackReference(value = "Localizacion-Episodio")
	@JoinTable(name = "LocalizacionPorEpisodio", 
		joinColumns = @JoinColumn(name = "idLocalizacion", referencedColumnName = "id"), 
		inverseJoinColumns = @JoinColumn(name = "idEpisodio", referencedColumnName = "id"))
	private Set<Episode> episode = new HashSet<Episode>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Episode> getEpisode() {
		return episode;
	}

	public void setEpisode(Set<Episode> episode) {
		this.episode = episode;
	}	
}
