package com.example.electionbackend.config;

import com.example.electionbackend.seeder.ElectionDefinitionSeeder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SeederConfig {

    private final ElectionDefinitionSeeder electionDefinitionSeeder;

    /**
     * Constructor method
     * @param electionDefinitionSeeder ElectionSeeder
     */
    public SeederConfig(ElectionDefinitionSeeder electionDefinitionSeeder) {
        this.electionDefinitionSeeder = electionDefinitionSeeder;
    }

    /**
     * Runs after Spring Boot startup, ensuring JPA repositories are ready.
     * Seeds election data for the given years.
     */
    @Bean
    public CommandLineRunner runSeeder() {
        return args -> {
            int[] electionYears = {2021, 2023, 2025}; // add more years if needed
            for (int year : electionYears) {
                System.out.println("Running election seeder for year: " + year);
                electionDefinitionSeeder.seedElectionDefinition(year);
            }
        };
    }
}
