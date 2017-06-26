package co.artsoft.architecture.migraine.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	private DiagnosticRepository diagnosticRepository;
	@Autowired
	private FoodRepository foodRepository;
	@Autowired
	private MedicineRepository medicineRepository;
	@Autowired
	private PhysicalActivityRepository physicalActivityRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private EpisodeRepository episodeRepository;
	
	public Diagnostic saveRepository(Diagnostic diagnostic) {	
		
		 diagnostic.setDate(new java.sql.Timestamp(System.currentTimeMillis()));
		 
		 setDoctor(diagnostic);	
		 setEpisode(diagnostic);
		 setFoods(diagnostic);
		 setMedicine(diagnostic);
		 setPhysicalActivities(diagnostic);
		 
		 return diagnosticRepository.save(diagnostic);		 
	}
	
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
	
	private void setDoctor(Diagnostic diagnostic) {
		User user = userRepository.findOne(diagnostic.getUser().getDocumentNumber());
		diagnostic.setUser(user);
	}
	
	private void setEpisode(Diagnostic diagnostic) {
		Episode episode = episodeRepository.findOne(diagnostic.getEpisode().getId());
		diagnostic.setEpisode(episode);
	}
	
	public Diagnostic getLatestDiagnostic(String documentPatient) {		
		//TODO: get latest diagnostic
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
