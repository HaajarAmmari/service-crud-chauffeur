package ma.emsi.springsecurity;
import ma.emsi.springsecurity.entities.Chauffeur;
import ma.emsi.springsecurity.repositories.ChauffeurRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

@SpringBootApplication
public class SpringSecurityApplication {

    public static void main(String[] args) {

        SpringApplication.run(SpringSecurityApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(ChauffeurRepository chauffeurRepository){
        return args -> {
            chauffeurRepository.save(new Chauffeur(null, "Hamza", new Date(),"Casablanca"));
            chauffeurRepository.save(new Chauffeur(null, "Mohamed", new Date(),"Rabat"));
            chauffeurRepository.save(new Chauffeur(null, "Reda", new Date(),"Tanger"));
            chauffeurRepository.save(new Chauffeur(null, "Amine", new Date(),"Fes"));
            chauffeurRepository.findAll().forEach(
                    p->{
                        System.out.println(p.getNom());
                    }
            );
        };
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}