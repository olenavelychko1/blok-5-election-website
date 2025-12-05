package nl.hva.ict.sm3.backend.service;


import nl.hva.ict.sm3.backend.model.*;
import nl.hva.ict.sm3.backend.utils.PathUtils;
import nl.hva.ict.sm3.backend.utils.types.KieskringType;
import nl.hva.ict.sm3.backend.utils.xml.DutchElectionParser;
import nl.hva.ict.sm3.backend.utils.xml.transformers.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Constituency service used to load the constituency party votes and candidates from the backend.
 * Provides methods to get the data for a specific constituency and election year.
 */
@Service
public class ConstituencyService {
    private static final Logger logger = LoggerFactory.getLogger(ConstituencyService.class);


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
     * Builds the full resource path to the XML files for the specified constituency and election location.
     * If no constituency is specified, returns the path to the Constituency folder, instead of a specific file.
     *
     * @param constituency the name of the constituency
     * @param location the electionId (for example, "TK2023")
     * @return a path to the election data files
     * @throws IOException if the resource path cannot be found
     */
    private Path buildResourcePath(KieskringType constituency, String location) throws IOException {
        String basePath = "/%s_HvA_UvA/Constituency".formatted(location);
        if (constituency != null) {
            // Specific constituency, use the full path to the file
            basePath += "/Telling_%s_kieskring_%s.eml.xml".formatted(location, constituency);
        }
        return Path.of(PathUtils.getResourcePath(basePath));
    }

    /**
     * Generic helper method that parses election data and extracts a specific type of list from the
     * {@link Election} object using the provided extractor function.
     *
     * @param constituency the name of the constituency (can be null to get all constituencies)
     * @param year the election year
     * @return the election object
     * @throws RuntimeException if any IO or XML parsing error occurs
     */
    private Election getElectionData(KieskringType constituency, int year) {
        String location = "TK" + year; // electionId
        Election election = new Election(location);
        DutchElectionParser parser = createDutchElectionParser(election);

        try {
            // build the skeleton for data
            String definitionPath = "/%s_HvA_UvA/Verkiezingsdefinitie_%s.eml.xml".formatted(location, location);
            parser.parseResults(location, PathUtils.getResourcePath(definitionPath));

            Path path = buildResourcePath(constituency, location);
            parser.parseResults(location, String.valueOf(path));

            // election
            return election;
        } catch (IOException | XMLStreamException | NullPointerException | ParserConfigurationException | SAXException e) {
            logger.error("Failed to process election data for constituency={} year={}", constituency, year, e);
            throw new RuntimeException("Failed to process election data for constituency " + constituency, e);
        }

    }


    // ------ GET METHODS -------

    /**
     * Retrieves the full Election object for the given constituency and election year.
     * <p>
     * If a specific constituency is provided, only that constituency's data will be parsed.
     * If {@code constituency} is {@code null}, the entire election data (all constituencies)
     * will be returned.
     *
     * @param constituency the constituency to retrieve (nullable, for all constituencies)
     * @param year the election year (e.g., 2023)
     * @return the full {@link Election} object containing all parsed election data
     */
    public Election getElection(KieskringType constituency, int year) {
        return getElectionData(constituency, year);
    }

    /**
     * Retrieves party votes for the given constituency and election year.
     * <p>
     * If a specific constituency is provided, only that constituency's votes are returned.
     * If {@code constituency} is {@code null}, votes from all constituencies are combined.
     *
     * @param constituency the constituency to retrieve votes for (nullable, for all constituencies)
     * @param year the election year (e.g., 2023)
     * @return a list of {@link RegionPartyVoteDTO} objects representing party votes
     */
    public List<RegionPartyVoteDTO> getPartyVotes(KieskringType constituency, int year) {
        Election election = getElectionData(constituency, year);
        System.out.printf("%s party votes: %s\n", constituency, election);

        election.getState().getConstituencyList().forEach(c ->
                System.out.println("Constituency in XML: " + c.getName())
        );
        System.out.println("Requested constituency: " + constituency);

        // if the name of the constituency is specified, return only that constituency
        if (constituency != null) {

            return election.getState().getConstituencyList().stream()
                    .filter(c -> c.getName().equalsIgnoreCase(constituency.name().replace("_", " "))) // filter out unneeded constituencies
                    .flatMap(c -> c.getPartyVoteList().stream() // merges inner Streams into 1 big one (like Stream<Stream<PartyVote>>.)
                            .map(v -> new RegionPartyVoteDTO(c.getName(), v)))
                    .collect(Collectors.toList());
        } else {
            // Combine all
            return election.getState().getConstituencyList().stream()
                    .flatMap(c -> c.getPartyVoteList().stream()
                            .map(v -> new RegionPartyVoteDTO(c.getName(), v)))
                    .collect(Collectors.toList());
        }
    }

    /**
     * Retrieves candidate votes for the given constituency and election year.
     * <p>
     * If a specific constituency is provided, only that constituency's votes are returned.
     * If {@code constituency} is {@code null}, votes from all constituencies are combined.
     *
     * @param constituency the constituency to retrieve votes for (nullable, for all constituencies)
     * @param year the election year (e.g., 2023)
     * @return a list of {@link RegionCandidateVote} objects representing candidate votes
     */
    public List<RegionCandidateVoteDTO> getCandidateVotes(KieskringType constituency, int year) {
        Election election = getElectionData(constituency, year);

        // if the name of the constituency is specified, return only that constituency
        if (constituency != null) {
            return election.getState().getConstituencyList().stream()
                    .filter(c -> c.getName().equalsIgnoreCase(constituency.name().replace("_", " ")))
                    .flatMap(c -> c.getCandidateVotes().stream()
                            .map(v -> new RegionCandidateVoteDTO(c.getName(), v)))
                    .collect(Collectors.toList());
        } else {
            return election.getState().getConstituencyList().stream()
                    .flatMap(c -> c.getCandidateVotes().stream()
                            .map(v -> new RegionCandidateVoteDTO(c.getName(), v)))
                    .collect(Collectors.toList());
        }
    }

    /**
     * Retrieves election metadata for the given constituency and election year.
     * <p>
     * If a specific constituency is provided, only that constituency's metadata is returned.
     * If {@code constituency} is {@code null}, metadata from all constituencies is combined.
     *
     * @param constituency the constituency to retrieve metadata for (nullable, for all constituencies)
     * @param year the election year (e.g., 2023)
     * @return a list of {@link Metadata} objects (never null, but may be empty)
     */
    public List<Metadata> getElectionMetadata(KieskringType constituency, int year) {
        Election election = getElectionData(constituency, year);

        // if the name of the constituency is specified, return only that constituency
        if (constituency != null) {
            return election.getState().getConstituencyList().stream() // Converts the list into a Stream, which allows functional operations like filtering, mapping, and flattening.
                    .filter(c -> c.getName().equalsIgnoreCase(constituency.name().replace("_", " "))) // filter out unneeded constituencies
                    .map(Constituency::getMetadata) // keeps only the metadata
                    .filter(Objects::nonNull) // This line removes any null metadata from the results.
                    .collect(Collectors.toList()); // converts the Stream back into a Java List<Metadata>
        } else {
            return election.getState().getConstituencyList().stream()
                    .map(Constituency::getMetadata)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        }
    }


}
