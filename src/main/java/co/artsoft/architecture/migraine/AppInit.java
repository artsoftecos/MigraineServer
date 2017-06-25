package co.artsoft.architecture.migraine;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
@RestController
public class AppInit implements CommandLineRunner {

	@Autowired
    DataSource dataSource;
	
    @RequestMapping("/")
    String home() {
        return "ArtSoft Rocks!";
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(AppInit.class, args);
    }
    
    @Override
    public void run(String... args) throws Exception {

        System.out.println("DATASOURCE = " + dataSource);

    }

}