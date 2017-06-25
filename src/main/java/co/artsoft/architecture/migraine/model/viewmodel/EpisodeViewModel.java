package co.artsoft.architecture.migraine.model.viewmodel;

import java.util.HashSet;
import java.util.Set;

public class EpisodeViewModel {
	private Integer id;	
	private Integer painLevel;
	private String sleepPattern;
	private String urlAudioFile;

	public String getUrlAudioFile() {
		return urlAudioFile;
	}

	public void setUrlAudioFile(String urlAudioFile) {
		this.urlAudioFile = urlAudioFile;
	}

	private Set<FoodViewModel> foods = new HashSet<FoodViewModel>();  
    
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPainLevel() {
		return painLevel;
	}

	public void setPainLevel(Integer painLevel) {
		this.painLevel = painLevel;
	}

	public String getSleepPattern() {
		return sleepPattern;
	}

	public void setSleepPattern(String sleepPattern) {
		this.sleepPattern = sleepPattern;
	}

	public Set<FoodViewModel> getFoods() {
		return foods;
	}

	public void setFoods(Set<FoodViewModel> foods) {
		this.foods = foods;
	}
}
