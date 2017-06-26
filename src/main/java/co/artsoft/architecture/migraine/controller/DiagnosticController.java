package co.artsoft.architecture.migraine.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import co.artsoft.architecture.migraine.model.entity.Diagnostic;
import co.artsoft.architecture.migraine.services.DiagnosticService;

@RestController
@RequestMapping(path = "/diagnostic")
public class DiagnosticController {
	
	@Autowired
	private DiagnosticService diagnosticService;
	
	@RequestMapping(value="/register",method = RequestMethod.POST)
	public ResponseEntity<?> addEpisode(@RequestBody Diagnostic diagnostic) {		
		Diagnostic savedDiagnostic = null;	 
		 try {
			 savedDiagnostic =  diagnosticService.saveRepository(diagnostic);
		 } catch (Exception e) {
			 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
		}	 
		 return ResponseEntity.ok(savedDiagnostic);
	}
}
