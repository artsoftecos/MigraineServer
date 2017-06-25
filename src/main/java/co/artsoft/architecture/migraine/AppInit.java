package co.artsoft.architecture.migraine;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
@RestController
public class AppInit {

    @RequestMapping("/")
    String home() {
        return "ArtSoft Rocks!";
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(AppInit.class, args);
    }

}