package co.artsoft.architecture.migraine;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.web.bind.annotation.*;


@SpringBootApplication
@RestController
public class AppInit extends SpringBootServletInitializer{

	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(AppInit.class);
    }
	
    @RequestMapping("/")
    String home() {
        return "ArtSoft Rocks!";
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(AppInit.class, args);
    }

}