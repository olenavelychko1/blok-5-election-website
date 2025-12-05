package nl.hva.ict.sm3.backend.service;

import nl.hva.ict.sm3.backend.model.Election;
import nl.hva.ict.sm3.backend.model.Party;
import nl.hva.ict.sm3.backend.utils.PathUtils;
import nl.hva.ict.sm3.backend.utils.types.KieskringType;
import nl.hva.ict.sm3.backend.utils.xml.DutchElectionParser;
import nl.hva.ict.sm3.backend.utils.xml.transformers.*;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.util.List;

/**
 * the candidate service
 */
@Service
public class CandidateService {
    /**
     * Retrieves the candidates in a list of parties
     * @param kieskring District of the Dutch Election
     * @param year The year of the election
     * @return <List>Party list of parties with candidates in them
     */
    public List<Party> getCandidates(KieskringType kieskring, int year) {
        String location = "TK" + year;
        Election election = new Election(location);

        DutchElectionParser electionParser = new DutchElectionParser(
                new DutchDefinitionTransformer(election),
                new DutchCandidateTransformer(election),
                new DutchResultTransformer(election),
                new DutchNationalVotesTransformer(election),
                new DutchConstituencyVotesTransformer(election),
                new DutchMunicipalityVotesTransformer(election)
        );

        try {
            // If kieskring in undefined get all the candidates else get the candidates per kieskring
            if (kieskring != null) {
                electionParser.parseResults(location + "_" + kieskring, PathUtils.getResourcePath("/%s".formatted(location + "_HvA_UvA")));
            } else {
                electionParser.parseResults(location, PathUtils.getResourcePath("/%s".formatted(location + "_HvA_UvA")));
            }

            return election.getPartyList();
        } catch (IOException | XMLStreamException | NullPointerException | ParserConfigurationException | SAXException e) {
            throw new RuntimeException(e);
        }
    }
}