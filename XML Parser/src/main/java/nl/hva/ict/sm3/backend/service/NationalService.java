package nl.hva.ict.sm3.backend.service;

import nl.hva.ict.sm3.backend.model.*;
import nl.hva.ict.sm3.backend.utils.PathUtils;
import nl.hva.ict.sm3.backend.utils.xml.DutchElectionParser;
import nl.hva.ict.sm3.backend.utils.xml.transformers.*;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.nio.file.Path;

/**
 * Service class that handles the retrieval of national election results,
 * candidate results, and election metadata.
 */
@Service
public class NationalService {

    /**
     * Creates a DutchElectionParser with all the necessary transformers.
     *
     * @param election the Election object to store the parsed results
     * @return a DutchElectionParser instance configured with all transformers
     */
    private DutchElectionParser createElectionParser(Election election) {
        return new DutchElectionParser(
                new DutchDefinitionTransformer(election),
                new DutchCandidateTransformer(election),
                new DutchResultTransformer(election),
                new DutchNationalVotesTransformer(election),
                new DutchConstituencyVotesTransformer(election),
                new DutchMunicipalityVotesTransformer(election)
        );
    }

    /**
     * Builds the resource path for the national election data.
     *
     * @param location the election location string (e.g., "TK2023")
     * @return the constructed Path object for the national results file
     */
    private Path buildResourcePath(String location) {
        return Path.of(PathUtils.getResourcePath(
                "/%s_HvA_UvA/National/Totaaltelling_%s.eml.xml".formatted(location, location)
        ));
    }

    /**
     * Retrieves the national election results (party votes, candidates, metadata)
     * for a specific year.
     *
     * @param year the election year (e.g., 2023)
     * @return an Election object containing the parsed national results
     */
    public Election getNationalPartyResults(int year) {
        String location = "TK" + year;
        Election election = new Election(location);
        DutchElectionParser electionParser = createElectionParser(election);

        try {
            // Parse the election definition file
            String definitionPath = "/%s_HvA_UvA/Verkiezingsdefinitie_%s.eml.xml".formatted(location, location);
            electionParser.parseResults(location, PathUtils.getResourcePath(definitionPath));

            // Parse the national total results
            electionParser.parseResults(location, buildResourcePath(location).toString());

            return election;
        } catch (IOException | XMLStreamException | NullPointerException |
                 ParserConfigurationException | SAXException e) {
            throw new RuntimeException("Failed to process national election data: " + e.getMessage(), e);
        }
    }
}
