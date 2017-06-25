package co.artsoft.architecture.migraine.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
import co.artsoft.architecture.migraine.model.viewmodel.EpisodeViewModel;

@RestController
@RequestMapping(path = "/episode")
public class EpisodeController {
	
	 private GlobalProperties global;
	 
	 @Autowired
	 public void setGlobal(GlobalProperties global) {
	    this.global = global;
	 }
	 
	@RequestMapping(value="/register",method = RequestMethod.POST)
	public ResponseEntity<?> addEpisode(@RequestPart("data") String data, 
			@RequestPart("audioFile") MultipartFile file) throws JsonParseException, JsonMappingException, IOException {
		
		 EpisodeViewModel episode = new ObjectMapper().readValue(data, EpisodeViewModel.class);
		 
		 if (!file.isEmpty()) {
		        try {
		            byte[] bytes = file.getBytes();
		            Path path = Paths.get(global.getFolderAudio() + file.getOriginalFilename());
		            Files.write(path, bytes);

		        } catch (IOException e) {	
		        	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);		        	
		        }
	     }

		 return ResponseEntity.ok(episode);
	}
}
