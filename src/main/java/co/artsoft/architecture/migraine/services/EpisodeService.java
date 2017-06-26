package co.artsoft.architecture.migraine.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.artsoft.architecture.migraine.model.dao.EpisodeRepository;
import co.artsoft.architecture.migraine.model.dao.FoodRepository;
import co.artsoft.architecture.migraine.model.dao.LocationRepository;
import co.artsoft.architecture.migraine.model.dao.MedicineRepository;
import co.artsoft.architecture.migraine.model.dao.PhysicalActivityRepository;
import co.artsoft.architecture.migraine.model.dao.UserRepository;
import co.artsoft.architecture.migraine.model.entity.Episode;
import co.artsoft.architecture.migraine.model.entity.Food;
import co.artsoft.architecture.migraine.model.entity.Location;
import co.artsoft.architecture.migraine.model.entity.Medicine;
import co.artsoft.architecture.migraine.model.entity.PhysicalActivity;
import co.artsoft.architecture.migraine.model.entity.User;

@Service
public class EpisodeService {
	@Autowired
	private EpisodeRepository episodeRepository;
	@Autowired
	private FoodRepository foodRepository;
	@Autowired
	private LocationRepository locationRepository;
	@Autowired
	private MedicineRepository medicineRepository;
	@Autowired
	private PhysicalActivityRepository physicalActivityRepository;
	@Autowired
	private UserRepository userRepository;
		
	public Episode saveRepository(Episode episode) {	
		
		 episode.setDate(new java.sql.Date(System.currentTimeMillis()));
		 
		 setPatient(episode);
		 setFoods(episode);
		 setLocations(episode);
		 setMedicine(episode);
		 setPhysicalActivities(episode);
		 
		 return episodeRepository.save(episode);		 
	}
	
	public List<Episode> getEpisodesPatient(String documentNumber) {
		return episodeRepository.findByUser(userRepository.findOne(documentNumber));		
	}
	
	public Episode getEpisode(int id) {
		return episodeRepository.findOne(id);
	}
	
	public String getPathAudio(int id){
		return episodeRepository.findOne(id).getAudioPath();		
	}
	
	private void setFoods(Episode episode) {
		Set<Food> foods = new HashSet<Food>();
		 if (episode.getFoods() != null && episode.getFoods().size() > 0) {
			for(Food f : episode.getFoods()) {
				Food food = foodRepository.findOne(f.getId());				
				foods.add(food);			
			}	
			episode.setFoods(foods);
		 }
	}
	
	private void setLocations(Episode episode) {
		Set<Location> locations = new HashSet<Location>();
		 if (episode.getLocations() != null && episode.getLocations().size() > 0) {
			for(Location f : episode.getLocations()) {
				Location location = locationRepository.findOne(f.getId());				
				locations.add(location);			
			}	
			episode.setLocations(locations);
		 }
	}
	
	private void setMedicine(Episode episode) {
		Set<Medicine> medicines = new HashSet<Medicine>();
		 if (episode.getMedicines() != null && episode.getMedicines().size() > 0) {
			for(Medicine f : episode.getMedicines()) {
				Medicine medicine = medicineRepository.findOne(f.getId());				
				medicines.add(medicine);			
			}	
			episode.setMedicines(medicines);
		 }
	}
	
	private void setPhysicalActivities(Episode episode) {
		Set<PhysicalActivity> physicalActivities = new HashSet<PhysicalActivity>();
		 if (episode.getPhysicalActivity() != null && episode.getPhysicalActivity().size() > 0) {
			for(PhysicalActivity f : episode.getPhysicalActivity()) {
				PhysicalActivity physicalActivity = physicalActivityRepository.findOne(f.getId());				
				physicalActivities.add(physicalActivity);			
			}	
			episode.setPhysicalActivity(physicalActivities);
		 }
	}
	
	private void setPatient(Episode episode) {
		User user = userRepository.findOne(episode.getUser().getDocumentNumber());
		episode.setUser(user);
	}
}
