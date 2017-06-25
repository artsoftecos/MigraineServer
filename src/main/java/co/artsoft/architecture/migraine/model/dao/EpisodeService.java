package co.artsoft.architecture.migraine.model.dao;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.artsoft.architecture.migraine.model.entity.Episode;
import co.artsoft.architecture.migraine.model.entity.Food;
import co.artsoft.architecture.migraine.model.viewmodel.EpisodeViewModel;
import co.artsoft.architecture.migraine.model.viewmodel.FoodViewModel;

@Service
public class EpisodeService {
	@Autowired
	private EpisodeRepository episodeRepository;
	@Autowired
	private FoodRepository foodRepository;
	
	public Episode saveRepository(EpisodeViewModel episode) {
		 Episode migraine = new Episode();
		 migraine.setSleepPattern(episode.getSleepPattern());			
		 migraine.setPainLevel(episode.getPainLevel());
		 migraine.setDate(new java.sql.Date(System.currentTimeMillis()));
		 migraine.setAudioPath(episode.getUrlAudioFile());
		 
		 Set<Food> foods = new HashSet<Food>();
		 if (episode.getFoods() != null && episode.getFoods().size() > 0) {
			for(FoodViewModel f : episode.getFoods()) {
				Food food = foodRepository.findOne(f.getId());				
				foods.add(food);			
			}			
			migraine.setFoods(foods);			
		 }	
		 
		 return episodeRepository.save(migraine);
	}
}
