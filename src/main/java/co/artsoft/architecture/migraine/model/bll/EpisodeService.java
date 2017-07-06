package co.artsoft.architecture.migraine.model.bll;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.artsoft.architecture.migraine.model.bll.LoggerService.TYPE;
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
	private LoggerService LOGGER;
	
	/**
	 * Repository of episode.
	 */
	@Autowired
	private EpisodeRepository episodeRepository;
	
	/**
	 * Repository of food
	 */
	@Autowired
	private FoodRepository foodRepository;
	
	/**
	 * Repository of location
	 */
	@Autowired
	private LocationRepository locationRepository;
	
	/**
	 * Repository of medicine
	 */
	@Autowired
	private MedicineRepository medicineRepository;
	
	/**
	 * Repository of physical activities
	 */
	@Autowired
	private PhysicalActivityRepository physicalActivityRepository;
	
	/**
	 * Repository of Users
	 */
	@Autowired
	private UserRepository userRepository;
		
	/**
	 * Save episode
	 * @param episode: Entity Episode to be saved.
	 * @return the saved episode entity.
	 */
	public Episode saveRepository(Episode episode) {	
		LOGGER.setLog("	Start save episode in db", TYPE.INFO);
		 episode.setDate(new java.sql.Timestamp(System.currentTimeMillis()));
		  
		 setPatient(episode);
		 LOGGER.setLog("	Finish assign patient to episode", TYPE.INFO);		 
		 setFoods(episode);
		 LOGGER.setLog("	Finish assign foods to episode", TYPE.INFO);
		 setLocations(episode);
		 LOGGER.setLog("	Finish assign locations to episode", TYPE.INFO);
		 setMedicine(episode);
		 LOGGER.setLog("	Finish assign medicines to episode", TYPE.INFO);
		 setPhysicalActivities(episode);
		 LOGGER.setLog("	Finish assign physical activities to episode", TYPE.INFO);
		 return episodeRepository.save(episode);
	}
	
	/**
	 * Get the episodes of patient.
	 * @param documentNumber: the document number of the patient to request.
	 * @return the episodes of the patient.
	 */
	public List<Episode> getEpisodesPatient(String documentNumber) {
		return episodeRepository.findByUser(userRepository.findOne(documentNumber));		
	}
	
	/**
	 * Get episode by its Identifier.
	 * @param id: Id of the episode.
	 * @return the requested episode.
	 */
	public Episode getEpisode(int id) {
		return episodeRepository.findOne(id);
	}
	
	/**
	 * Get the name of the audio file.
	 * @param id: Id of the episode to request the audio file.
	 * @return the name of the audio file.
	 */
	public String getPathAudio(int id){
		return episodeRepository.findOne(id).getAudioPath();		
	}
	
	/**
	 * Set Food to the episode.
	 * @param episode: The episode to add foods.
	 */
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
	
	/**
	 * Set locations to the episode.
	 * @param episode: The episode to add locations.
	 */
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
	
	/**
	 * Set Medicine to the episode.
	 * @param episode: The episode to add medicines.
	 */
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
	
	/**
	 * Set physical activities to the episode.
	 * @param episode: The episode to add physical activities.
	 */
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
	
	/**
	 * Set The patient to the episode.
	 * @param episode: The episode to add the patient.
	 */
	private void setPatient(Episode episode) {
		User user = userRepository.findOne(episode.getUser().getDocumentNumber());
		episode.setUser(user);
	}
}
