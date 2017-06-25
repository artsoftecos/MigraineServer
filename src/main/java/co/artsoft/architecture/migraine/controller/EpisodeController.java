package co.artsoft.architecture.migraine.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import co.artsoft.architecture.migraine.GlobalProperties;
import co.artsoft.architecture.migraine.model.dao.EpisodeRepository;
import co.artsoft.architecture.migraine.model.entity.Food;
import co.artsoft.architecture.migraine.model.entity.Episode;
import co.artsoft.architecture.migraine.model.viewmodel.EpisodeViewModel;
import co.artsoft.architecture.migraine.model.viewmodel.FoodViewModel;

@RestController
@RequestMapping(path = "/episode")
public class EpisodeController {
	
	@Autowired
	private EpisodeRepository episodeRepository;
	private GlobalProperties global;
	 
	@Autowired
	public void setGlobal(GlobalProperties global) {
	   this.global = global;
	}
	 
	@RequestMapping(value="/register",method = RequestMethod.POST)
	public ResponseEntity<?> addEpisode(@RequestPart("data") String data, 
			@RequestPart("audioFile") MultipartFile file) throws JsonParseException, JsonMappingException, IOException {
		
		 EpisodeViewModel episode = new ObjectMapper().readValue(data, EpisodeViewModel.class);
		 
		 Episode migraine = new Episode();
		 migraine.setSleepPattern(episode.getSleepPattern());			
		 migraine.setPainLevel(episode.getPainLevel());
		 migraine.setDate(new java.sql.Date(System.currentTimeMillis()));	
			
		 Set<Food> foods = new HashSet<Food>();
		 if (episode.getFoods() != null && episode.getFoods().size() > 0) {
			for(FoodViewModel f : episode.getFoods()) {
				Food food = new Food();
				food.setName(f.getName());
				foods.add(food);			
			}			
			migraine.setFoods(foods);			
		 }					
			
		 if (!file.isEmpty()) {
		        try {
		        	//TODO: el nombre del archivo debe cambiar, porque se puede subir mas de una vez el archivo
		        	String audioPath = global.getFolderAudio() + file.getOriginalFilename();
		            byte[] bytes = file.getBytes();
		            Path path = Paths.get(audioPath);
		            Files.write(path, bytes);
		            migraine.setAudioPath(audioPath);

		        } catch (IOException e) {	
		        	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);		        	
		        }
	     }
		 		 
		 return ResponseEntity.ok(episodeRepository.save(migraine));
	}
}
