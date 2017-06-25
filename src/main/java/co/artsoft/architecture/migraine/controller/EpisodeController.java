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
import co.artsoft.architecture.migraine.model.dao.EpisodeService;
import co.artsoft.architecture.migraine.model.dao.FileService;
import co.artsoft.architecture.migraine.model.entity.Food;
import co.artsoft.architecture.migraine.model.entity.Episode;
import co.artsoft.architecture.migraine.model.viewmodel.EpisodeViewModel;
import co.artsoft.architecture.migraine.model.viewmodel.FoodViewModel;

@RestController
@RequestMapping(path = "/episode")
public class EpisodeController {
		 
	@Autowired
	private FileService fileService;
	@Autowired
	private EpisodeService episodeService;
	
	@RequestMapping(value="/register",method = RequestMethod.POST)
	public ResponseEntity<?> addEpisode(@RequestPart("data") String data, 
			@RequestPart("audioFile") MultipartFile file) throws JsonParseException, JsonMappingException, IOException {
		
		 EpisodeViewModel episode = new ObjectMapper().readValue(data, EpisodeViewModel.class);
		 
		 try {
			 if (!file.isEmpty()) {
				 episode.setUrlAudioFile(fileService.storageFile(file));
			 }
		 } catch (IOException e) {
			 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}	 
		 		 
		 return ResponseEntity.ok(episodeService.saveRepository(episode));
	}
}
