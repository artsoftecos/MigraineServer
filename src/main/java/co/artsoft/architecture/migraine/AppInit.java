/**
 * MigraineServer by ArtSoft
 * @CopyRight ArtSoft
 */
package co.artsoft.architecture.migraine;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.web.bind.annotation.*;

/**
 * Main of the application
 * @author ArtSoft
 *
 */
@SpringBootApplication
@RestController
public class AppInit implements CommandLineRunner {

	/**
	 * Datasource that the application is using.
	 */
	@Autowired
    DataSource dataSource;
	
	/**
	 * Home rest service
	 * @return
	 */
    @RequestMapping("/")
    String home() {
        return "ArtSoft Rocks!";
    }

    /**
     * Main of the application
     * @param args: arguments of the application.
     * @throws Exception: Throw possible exception.
     */
    public static void main(String[] args) throws Exception {
    	//System.setProperty("server.tomcat.accept-count","100");
    	System.setProperty("server.tomcat.max-threads","300");
		System.setProperty("server.connection-timeout","60000");
		//TimeZone.setDefault(TimeZone.getTimeZone("GMT"));
        SpringApplication.run(AppInit.class, args);
    }
    
    /**
     * Indicate the datasource that application has configured.
     */
    @Override
    public void run(String... args) throws Exception {

        System.out.println("DATASOURCE = " + dataSource);

    }

}