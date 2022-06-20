package org.fasttrackit.person.server;

import org.fasttrackit.person.server.entity.PersonEntity;
import org.fasttrackit.person.server.repository.PersonRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class PersonServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(PersonServerApplication.class, args);
    }

    @Bean
    CommandLineRunner atStartup(PersonRepository repo) {
        return args -> {
            repo.saveAll(List.of(
                    new PersonEntity("Ana", 32),
                    new PersonEntity("Maria", 52),
                    new PersonEntity("George", 62),
                    new PersonEntity("Ionel", 37),
                    new PersonEntity("Mihai", 12),
                    new PersonEntity("Dragos", 42)
            ));
        };
    }
}
