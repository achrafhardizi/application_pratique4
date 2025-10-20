package com.enset.keynotesvc;

import com.enset.keynotesvc.models.Keynote;
import com.enset.keynotesvc.repositories.KeynoteRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class KeynoteSvcApplication {

    public static void main(String[] args) {
        SpringApplication.run(KeynoteSvcApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(KeynoteRepository repository) {
        return args -> {
            repository.save(Keynote.builder()
                    .nom("IDRISSI")
                    .prenom("Yassine")
                    .email("yassine.idrissi@gmail.com")
                    .fonction("Data Engineer")
                    .build());

            repository.save(Keynote.builder()
                    .nom("HAMMI")
                    .prenom("Achraf")
                    .email("achraf.hammi@gmail.com")
                    .fonction("QA Engineer")
                    .build());
            repository.save(Keynote.builder()
                    .nom("KANDIL")
                    .prenom("Safouan")
                    .email("safouane.kandil@gmail.com")
                    .fonction("Cybersecurity Engineer")
                    .build());

            repository.findAll().forEach(c->{
                System.out.println("=============================");
                System.out.println(c.toString());
                System.out.println("=============================");
            });
        };
    }
}
