package com.enset.conferencesvc;

import com.enset.conferencesvc.enums.ConferenceType;
import com.enset.conferencesvc.feign.KeynoteFeignClient;
import com.enset.conferencesvc.models.Conference;
import com.enset.conferencesvc.models.Review;
import com.enset.conferencesvc.repositories.ConferenceRepository;
import com.enset.conferencesvc.repositories.ReviewRepository;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;

@SpringBootApplication
@EnableFeignClients
public class ConferenceSvcApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConferenceSvcApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(
            ConferenceRepository conferenceRepository,
            ReviewRepository reviewRepository,
            KeynoteFeignClient keynoteFeignClient
    ) {
        return args -> {
            // === Create Conferences using builder ===
            Conference conf1 = Conference.builder()
                    .type(ConferenceType.COMMERCIAL)
                    .title("DevSecOps Summit 2025")
                    .date(new Date())
                    .duration(180L)
                    .score(4.8)
                    .keynoteIds(List.of(1, 2)) // IDs from Keynote service
                    .build();

            Conference conf2 = Conference.builder()
                    .type(ConferenceType.ACADEMIC)
                    .title("Cloud Innovation Week")
                    .date(new Date())
                    .duration(240L)
                    .score(4.5)
                    .keynoteIds(List.of(3))
                    .build();

            conferenceRepository.saveAll(List.of(conf1, conf2));

            // === Create Reviews using builder ===
            Review r1 = Review.builder()
                    .date(new Date())
                    .comment("Excellent organization and speakers!")
                    .conference(conf1)
                    .build();

            Review r2 = Review.builder()
                    .date(new Date())
                    .comment("Loved the DevSecOps deep dives!")
                    .conference(conf1)
                    .build();

            Review r3 = Review.builder()
                    .date(new Date())
                    .comment("Insightful talks on cloud governance!")
                    .conference(conf2)
                    .build();

            reviewRepository.saveAll(List.of(r1, r2, r3));

            // === Print conferences and reviews ===
            conferenceRepository.findAll().forEach(conf -> {
                System.out.println("======================================");
                System.out.println(conf.getTitle() + " (" + conf.getType() + ")");
                conf.getReviews().forEach(review -> System.out.println(" - " + review.getComment()));

                // === Fetch and print full keynote info via Feign ===
                if (!conf.getKeynoteIds().isEmpty()) {
                    System.out.println("Keynotes:");
                    conf.getKeynoteIds().forEach(id -> {
                        try {
                            var keynote = keynoteFeignClient.getKeynoteById(id);
                            System.out.println(" - " + keynote.getNom() + " (" + keynote.getFonction() + ")");
                        } catch (Exception e) {
                            System.out.println(" - Keynote ID " + id + " not available");
                        }
                    });
                }

                System.out.println("======================================");
            });
        };
    }

}
