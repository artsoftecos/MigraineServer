package co.artsoft.architecture.migraine.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class test the availability of the application
 * @author ArtSoft
 *
 */

@RestController
public class AvailabilityController {

	/**
	 * Home rest service
	 * @return
	 */
    @GetMapping("/ping")
    String home() {
        return "Up";
    }

}