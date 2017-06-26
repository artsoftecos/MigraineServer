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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import co.artsoft.architecture.migraine.GlobalProperties;
import co.artsoft.architecture.migraine.model.bll.DiagnosticService;
import co.artsoft.architecture.migraine.model.bll.EpisodeService;
import co.artsoft.architecture.migraine.model.bll.FileService;
import co.artsoft.architecture.migraine.model.entity.Diagnostic;
import co.artsoft.architecture.migraine.model.entity.Episode;

/**
 * Handle every request associated with migraine's episodes.
 * @author ArtSoft
 *
 */
@RestController
@RequestMapping(path = "/episode")
public class EpisodeController {
		 
	/**
	 * Service to handle files.
	 */
	@Autowired
	private FileService fileService;
	/**
	 * Service to handle information of episodes.
	 */
	@Autowired
	private EpisodeService episodeService;
	
	/**
	 * Service to handle information of diagnostics.
	 */
	@Autowired
	private DiagnosticService diagnosticService;
	
	/**
	 * Singleton to handle unique number of audio files.
	 */
	AtomicLong identifierAudio = new AtomicLong();
	
	/**
	 * Global properties.
	 */
	@Autowired
	private GlobalProperties global;
	
	/**
	 * Register episode of migraine
	 * @param data: Data of the episode
	 * @param file: the audio file of the episode.
	 * @return The diagnostic with the latest information.
	 * @throws JsonParseException: Exception to handle possible error parsing Json.
	 * @throws JsonMappingException: Exception to handle possible error parsing Entities. 
	 * @throws IOException: Exception to handle possible error getting or storing files.
	 */
	@PostMapping("/register")
	public ResponseEntity<?> addEpisode(@RequestPart("data") String data, 
			@RequestPart("audioFile") MultipartFile file) throws JsonParseException, JsonMappingException, IOException {
		
		Episode episode = new ObjectMapper().readValue(data, Episode.class);		 		 
		 try {
			 if (!file.isEmpty()) {
				 episode.setAudioPath(fileService.storageFile(file, identifierAudio.getAndIncrement()));
			 }
		 } catch (IOException e) {
			 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
		}	 
		 
		 episodeService.saveRepository(episode);
		 Diagnostic diagnostic = diagnosticService.getLatestDiagnostic(episode.getUser().getDocumentNumber());
		 if (diagnostic != null)
			 return ResponseEntity.ok(diagnostic);
		 
		 return ResponseEntity.ok("Episode saved, Doctor will diagnostic this");
	}
	
	/**
	 * Get the episodes of specific patient by his/her document number.
	 * @param documentNumber: the document number of the patient.
	 * @return The list of episodes of the patient.
	 */
	@GetMapping("/patient/{documentNumber}")
	public ResponseEntity<?> getEpisodesPatient(@PathVariable("documentNumber") String documentNumber) {
		List<Episode> episodes = null;	 
		 try {
			 episodes = episodeService.getEpisodesPatient(documentNumber);
			 
		 } catch (Exception e) {
			 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
		}	 
		 		 
		 return ResponseEntity.ok(episodes);
	} 
	
	/**
	 * Get specific episode of migraine
	 * @param idEpisode: Identifier of the episode to be requested.
	 * @return the episode requested.
	 */
	@GetMapping("/{id}")
	public ResponseEntity<?> getEpisode(@PathVariable("id") int idEpisode) {
		Episode episode = null;	 
		 try {
			 episode = episodeService.getEpisode(idEpisode);
			 
		 } catch (Exception e) {
			 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
		}	 
		 		 
		 return ResponseEntity.ok(episode);
	} 
	
	/**
	 * Download the audio file associated to the episode.
	 * @param idEpisode: Identifier of the episode.
	 * @return the audio file of the episode.
	 * @throws IOException: Handle possible error with the audio file.
	 */
	@GetMapping("/download/{idEpisode}")
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
