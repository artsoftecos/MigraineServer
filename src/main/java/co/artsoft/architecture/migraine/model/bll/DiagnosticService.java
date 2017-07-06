package co.artsoft.architecture.migraine.model.bll;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.artsoft.architecture.migraine.model.bll.LoggerService.TYPE;
import co.artsoft.architecture.migraine.model.dao.DiagnosticRepository;
import co.artsoft.architecture.migraine.model.dao.EpisodeRepository;
import co.artsoft.architecture.migraine.model.dao.FoodRepository;
import co.artsoft.architecture.migraine.model.dao.MedicineRepository;
import co.artsoft.architecture.migraine.model.dao.PhysicalActivityRepository;
import co.artsoft.architecture.migraine.model.dao.UserRepository;
import co.artsoft.architecture.migraine.model.entity.Diagnostic;
import co.artsoft.architecture.migraine.model.entity.Episode;
import co.artsoft.architecture.migraine.model.entity.Food;
import co.artsoft.architecture.migraine.model.entity.Medicine;
import co.artsoft.architecture.migraine.model.entity.PhysicalActivity;
import co.artsoft.architecture.migraine.model.entity.User;

@Service
public class DiagnosticService {
	
	@Autowired
	private LoggerService LOGGER;
	
	/**
	 * Repository of diagnostic.
	 */
	@Autowired
	private DiagnosticRepository diagnosticRepository;
	
	/**
	 * Repository of food.
	 */
	@Autowired
	private FoodRepository foodRepository;
	
	/**
	 * Repository of medicine.
	 */
	@Autowired
	private MedicineRepository medicineRepository;
	
	/**
	 * Repository of physical activity.
	 */
	@Autowired
	private PhysicalActivityRepository physicalActivityRepository;
	
	/**
	 * Repository of users.
	 */
	@Autowired
	private UserRepository userRepository;
	
	/**
	 * Repository of episode.
	 */
	@Autowired
	private EpisodeRepository episodeRepository;
	
	/**
	 * Save diagnostic.
	 * @param diagnostic: Entity Diagnostic to be saved.
	 * @return the saved diagnostic entity.
	 */
	public Diagnostic saveRepository(Diagnostic diagnostic) {	
		LOGGER.setLog("	 Initialized storage diagnostic in DB", TYPE.INFO);
		 diagnostic.setDate(new java.sql.Timestamp(System.currentTimeMillis()));
		 
		 setDoctor(diagnostic);
		 LOGGER.setLog("	Setting Doctor", TYPE.INFO);
		 setEpisode(diagnostic);
		 LOGGER.setLog("	Setting Episode", TYPE.INFO);
		 setFoods(diagnostic);
		 LOGGER.setLog("	Setting Foods", TYPE.INFO);
		 setMedicine(diagnostic);
		 LOGGER.setLog("	Setting Medicines", TYPE.INFO);
		 setPhysicalActivities(diagnostic);
		 LOGGER.setLog("	Setting Physical activities", TYPE.INFO);
		 return diagnosticRepository.save(diagnostic);		 
	}
	
	/**
	 * Set Foods to the diagnostic.
	 * @param diagnostic: The diagnostic to add foods.
	 */
	private void setFoods(Diagnostic diagnostic) {
		Set<Food> foods = new HashSet<Food>();
		 if (diagnostic.getFoods() != null && diagnostic.getFoods().size() > 0) {
			for(Food f : diagnostic.getFoods()) {
				Food food = foodRepository.findOne(f.getId());				
				foods.add(food);			
			}	
			diagnostic.setFoods(foods);
		 }
	}
	
	/**
	 * Set Medicines to the diagnostic.
	 * @param diagnostic: The diagnostic to add medicines.
	 */
	private void setMedicine(Diagnostic diagnostic) {
		Set<Medicine> medicines = new HashSet<Medicine>();
		 if (diagnostic.getMedicines() != null && diagnostic.getMedicines().size() > 0) {
			for(Medicine f : diagnostic.getMedicines()) {
				Medicine medicine = medicineRepository.findOne(f.getId());				
				medicines.add(medicine);			
			}	
			diagnostic.setMedicines(medicines);
		 }
	}
	
	/**
	 * Set Physical activity to the diagnostic.
	 * @param diagnostic: The diagnostic to add physical activities.
	 */
	private void setPhysicalActivities(Diagnostic diagnostic) {
		Set<PhysicalActivity> physicalActivities = new HashSet<PhysicalActivity>();
		 if (diagnostic.getPhysicalActivity() != null && diagnostic.getPhysicalActivity().size() > 0) {
			for(PhysicalActivity f : diagnostic.getPhysicalActivity()) {
				PhysicalActivity physicalActivity = physicalActivityRepository.findOne(f.getId());				
				physicalActivities.add(physicalActivity);			
			}	
			diagnostic.setPhysicalActivity(physicalActivities);
		 }
	}
	
	/**
	 * Set the Doctor to the diagnostic.
	 * @param diagnostic: The diagnostic to add the doctor.
	 */
	private void setDoctor(Diagnostic diagnostic) {
		User user = userRepository.findOne(diagnostic.getUser().getDocumentNumber());
		diagnostic.setUser(user);
	}
	
	/**
	 * Set the episode to the diagnostic.
	 * @param diagnostic: The diagnostic to add the episode.
	 */
	private void setEpisode(Diagnostic diagnostic) {
		Episode episode = episodeRepository.findOne(diagnostic.getEpisode().getId());
		diagnostic.setEpisode(episode);
	}
	
	/**
	 * Get the latest diagnostic of patient.
	 * @param documentPatient: the document number of the patient.
	 * @return the latest diagnostic, otherwise null.
	 */
	public Diagnostic getLatestDiagnostic(String documentPatient) {
		Diagnostic latestDiagnostic = null;
		User user = userRepository.findOne(documentPatient);
		List<Episode> episodes = episodeRepository.findByUserAndDiagnosticsNotNullOrderByDateDesc(user);
		
		if (episodes != null && episodes.size() > 0) {
			List<Diagnostic> diagnosticList = new ArrayList<>(episodes.get(episodes.size() - 1).getDiagnostics());						
			latestDiagnostic = diagnosticList.get(diagnosticList.size() - 1);
		}		
		return latestDiagnostic;
	}
}
