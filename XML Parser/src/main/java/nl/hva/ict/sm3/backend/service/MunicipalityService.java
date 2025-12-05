package nl.hva.ict.sm3.backend.service;

import nl.hva.ict.sm3.backend.model.*;
import nl.hva.ict.sm3.backend.utils.xml.*;
import nl.hva.ict.sm3.backend.utils.xml.transformers.*;
import nl.hva.ict.sm3.backend.utils.PathUtils;

import org.springframework.stereotype.Service;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * the municipality service
 */
@Service
public class MunicipalityService {
    // Helper method to create a DutchElectionParser with all necessary transformers
    private DutchElectionParser createDutchElectionParser(Election election) {
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
     * Builds the resource path for the election data based on location and municipality
     * @param location - location string (e.g., "TK2023")
     * @param municipality - municipality name
     * @return - the constructed Path object
     */
    private Path buildResourcePath(String location, String municipality) {
        if (municipality != null) {
            return Path.of(PathUtils.getResourcePath(
                    "/%s_HvA_UvA/Municipality/Telling_%s_gemeente_%s.eml.xml".formatted(location, location, municipality))
            );
        } else {
            return Path.of(PathUtils.getResourcePath("/%s_HvA_UvA/Municipality".formatted(location)));
        }
    }

    /**
     * Get party votes for a specific municipality and year
     *
     * @param municipality - the name of the municipality
     * @param year         - the year of the election, defaults to 2023
     * @return - a list of PartyVote objects
     */
    public Election getPartyVotes(String municipality, int year) {
        String location = "TK" + year;
        Election election = new Election(location);
        DutchElectionParser electionParser = createDutchElectionParser(election);

        try {
            String definitionPath = "/%s_HvA_UvA/Verkiezingsdefinitie_%s.eml.xml".formatted(location, location);
            electionParser.parseResults(location, PathUtils.getResourcePath(definitionPath));
            electionParser.parseResults(location, buildResourcePath(location, municipality).toString());

            return election;
        } catch (IOException | XMLStreamException | NullPointerException | ParserConfigurationException |
                 SAXException e) {
            throw new RuntimeException("Failed to process the elections data" + e);
        }
    }
}