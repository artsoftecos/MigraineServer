package co.artsoft.architecture.migraine.model.bll;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.artsoft.architecture.migraine.model.dao.DiagnosticRepository;
import co.artsoft.architecture.migraine.model.dao.DoctorRepository;
import co.artsoft.architecture.migraine.model.dao.EpisodeRepository;
import co.artsoft.architecture.migraine.model.dao.FoodRepository;
import co.artsoft.architecture.migraine.model.dao.MedicineRepository;
import co.artsoft.architecture.migraine.model.dao.PatientRepository;
import co.artsoft.architecture.migraine.model.dao.PhysicalActivityRepository;
import co.artsoft.architecture.migraine.model.entity.Diagnostic;
import co.artsoft.architecture.migraine.model.entity.Doctor;
import co.artsoft.architecture.migraine.model.entity.Episode;
import co.artsoft.architecture.migraine.model.entity.Food;
import co.artsoft.architecture.migraine.model.entity.Medicine;
import co.artsoft.architecture.migraine.model.entity.Patient;
import co.artsoft.architecture.migraine.model.entity.PhysicalActivity;

@Service
public class DiagnosticService {
	
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
	 * Repository of doctors.
	 */
	@Autowired
	private DoctorRepository doctorRepository;
	
	/**
	 * Repository of patients.
	 */
	@Autowired
	private PatientRepository patientRepository;
		
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
		
		 diagnostic.setDate(new java.sql.Timestamp(System.currentTimeMillis()));
		 
		 setDoctor(diagnostic);	
		 setEpisode(diagnostic);
		 setFoods(diagnostic);
		 setMedicine(diagnostic);
		 setPhysicalActivities(diagnostic);
		 
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
		Doctor doctor = doctorRepository.findOne(diagnostic.getDoctor().getCode());
		diagnostic.setDoctor(doctor);
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
		//TODO: get latest diagnostic
		Diagnostic latestDiagnostic = null;
		Patient patient = patientRepository.findOne(documentPatient);
		List<Episode> episodes = episodeRepository.findByPatientAndDiagnosticsNotNullOrderByDateDesc(patient);
		
		if (episodes != null && episodes.size() > 0) {
			List<Diagnostic> diagnosticList = new ArrayList<>(episodes.get(episodes.size() - 1).getDiagnostics());						
			latestDiagnostic = diagnosticList.get(diagnosticList.size() - 1);
		}		
		return latestDiagnostic;
	}
}
