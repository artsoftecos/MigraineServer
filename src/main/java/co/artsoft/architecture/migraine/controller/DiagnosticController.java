package co.artsoft.architecture.migraine.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.artsoft.architecture.migraine.model.bll.DiagnosticService;
import co.artsoft.architecture.migraine.model.bll.LoggerService;
import co.artsoft.architecture.migraine.model.bll.LoggerService.TYPE;
import co.artsoft.architecture.migraine.model.entity.Diagnostic;

/**
 * Handle every request associated with diagnostics.
 * @author ArtSoft
 *
 */
@RestController
@RequestMapping(path = "/diagnostic")
public class DiagnosticController {
	
	@Autowired
	private LoggerService LOGGER;
	
	@Autowired
	private DiagnosticService diagnosticService;
	
	/**
	 * Register diagnostic of episode.
	 * @param diagnostic: Object Json with the information of diagnostic.
	 * @return The diagnostic.
	 */
	@PostMapping(value="/register")
	public ResponseEntity<?> addEpisode(@RequestBody Diagnostic diagnostic) {
		LOGGER.initLogger("Diagnostic - /register");
		LOGGER.setLog("Init registering diagnostic", TYPE.INFO);
		Diagnostic savedDiagnostic = null;	 
		 try {
			 savedDiagnostic =  diagnosticService.saveRepository(diagnostic);
		 } catch (Exception e) {
			 LOGGER.setLog("Finish ERROR registering diagnostic "+e.getMessage(), TYPE.ERROR);
			 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
		}	 
		 LOGGER.setLog("Finish registering diagnostic ", TYPE.INFO);
		 return ResponseEntity.ok(savedDiagnostic);
	}
}
