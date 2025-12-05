package com.example.electionbackend.seeder;

import com.example.electionbackend.dto.ElectionDTO;
import com.example.electionbackend.model.Election;
import com.example.electionbackend.service.SeederService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * The ElectionDefinitionSeeder component
 */
@Component
public class ElectionDefinitionSeeder {
    @Value("${app.parser.url}")
    private String url;

    private final RestTemplate restTemplate = new RestTemplate();

    private final SeederService seederService;

    /**
     * Constructor for the ElectionDefinitionSeeder class.
     *
     * @param seederService The service responsible for handling election-related operations.
     */
    public ElectionDefinitionSeeder(SeederService seederService) {
        this.seederService = seederService;
    }

    /**
     * Retrieves election data for the specified year and saves it using the election service.
     * Once saved, the resulting election object is logged for confirmation.
     *
     * @param year The year of the election data to be retrieved and saved.
     */
    public void seedElectionDefinition(int year) {
        ElectionDTO electionDTO = this.getElectionData(year);
        Election response = seederService.persistElection(electionDTO);
        System.out.println(ResponseEntity.ok(response));
    }

    /**
     * Retrieves election data for the specified year by making a REST API call and mapping
     * the response to an Election object.
     *
     * @param year The year of the election data to be fetched.
     * @return An Election object containing the data for the specified year,
     *         or null if the data could not be fetched or mapped.
     */
    public ElectionDTO getElectionData(int year) {
        String uri = UriComponentsBuilder.fromUriString(url).path("/elections/TK" + year).queryParam("folderName", "TK" + year + "_HvA_UvA").build().toUriString();
        Object requestBody = null;

        // Make the REST API call and map the response to an Election object.
        ResponseEntity<String> electionResponse = restTemplate.postForEntity(uri, requestBody, String.class);
        String json = electionResponse.getBody();
        ObjectMapper mapper = new ObjectMapper();

        // Log the election data if it was successfully mapped.
        try {
            return mapper.readValue(json, ElectionDTO.class);
        } catch (Exception ex) {
            System.out.println("Election extraction failed: " +  ex.getMessage());
        }
        return null;
    }
}
