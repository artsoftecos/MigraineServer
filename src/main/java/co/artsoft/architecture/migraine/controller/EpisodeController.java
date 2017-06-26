package co.artsoft.architecture.migraine.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import co.artsoft.architecture.migraine.GlobalProperties;
import co.artsoft.architecture.migraine.model.entity.Diagnostic;
import co.artsoft.architecture.migraine.model.entity.Episode;
import co.artsoft.architecture.migraine.services.DiagnosticService;
import co.artsoft.architecture.migraine.services.EpisodeService;
import co.artsoft.architecture.migraine.services.FileService;

@RestController
@RequestMapping(path = "/episode")
public class EpisodeController {
		 
	@Autowired
	private FileService fileService;
	@Autowired
	private EpisodeService episodeService;
	@Autowired
	private DiagnosticService diagnosticService;
	AtomicLong identifierAudio = new AtomicLong();
	@Autowired
	private GlobalProperties global;
	
	@RequestMapping(value="/register",method = RequestMethod.POST)
	public ResponseEntity<?> addEpisode(@RequestPart("data") String data, 
			@RequestPart("audioFile") MultipartFile file) throws JsonParseException, JsonMappingException, IOException {
		
		Episode episode = new ObjectMapper().readValue(data, Episode.class);
		// EpisodeViewModel episode = new ObjectMapper().readValue(data, EpisodeViewModel.class);
		 		 
		 try {
			 if (!file.isEmpty()) {
				 episode.setAudioPath(fileService.storageFile(file, identifierAudio.getAndIncrement()));
			 }
		 } catch (IOException e) {
			 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
		}	 
		 		
		 //TODO: Analizar informacion
		 episodeService.saveRepository(episode);
		 Diagnostic diagnostic = diagnosticService.getLatestDiagnostic(episode.getUser().getDocumentNumber());
		 return ResponseEntity.ok(diagnostic);
		 //return ResponseEntity.ok(episodeService.saveRepository(episode));
	}
	
	@RequestMapping(value="/patient/{documentNumber}",method = RequestMethod.GET)
	public ResponseEntity<?> getEpisodesPatient(@PathVariable("documentNumber") String documentNumber) {
		List<Episode> episodes = null;	 
		 try {
			 episodes = episodeService.getEpisodesPatient(documentNumber);
			 
		 } catch (Exception e) {
			 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
		}	 
		 		 
		 return ResponseEntity.ok(episodes);
	} 
	
	@RequestMapping(value="/{id}",method = RequestMethod.GET)
	public ResponseEntity<?> getEpisode(@PathVariable("id") int idEpisode) {
		Episode episode = null;	 
		 try {
			 episode = episodeService.getEpisode(idEpisode);
			 
		 } catch (Exception e) {
			 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
		}	 
		 		 
		 return ResponseEntity.ok(episode);
	} 
	
	@RequestMapping(value="/download/{idEpisode}",method = RequestMethod.GET)
	public ResponseEntity<?> downloadEpisode(@PathVariable("idEpisode") int idEpisode) 
			throws IOException {	
		String nameAudio = episodeService.getPathAudio(idEpisode);
				
		if(nameAudio.isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Not contain Audio File");
		}
		
	    File file = new File(global.getFolderAudio() + nameAudio);
	    HttpHeaders respHeaders = new HttpHeaders();
	    respHeaders.setContentDispositionFormData("attachment", nameAudio);

	    InputStreamResource isr = new InputStreamResource(new FileInputStream(file));
	    return new ResponseEntity<InputStreamResource>(isr, respHeaders, HttpStatus.OK);	    
	}
}
