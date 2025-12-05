package nl.hva.ict.sm3.backend.utils.xml.transformers;

import nl.hva.ict.sm3.backend.model.*;
import nl.hva.ict.sm3.backend.utils.xml.VotesTransformer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Just prints to content of electionData to the standard output.>br/>
 * <b>This class needs heavy modification!</b>
 */
public class DutchNationalVotesTransformer implements VotesTransformer {
    private final Election election;

    /**
     * Creates a new transformer for handling the votes at the national level. It expects an instance of
     * Election that can be used for storing the results.
     * @param election the election in which the votes wil be stored.
     */

    public DutchNationalVotesTransformer(Election election) {
        this.election = election;
    }

    /**
     * Transformer for processing national-level Dutch election votes.
     *
     * @param aggregated whether the data is aggregated nationally
     * @param electionData map containing party votes data
     */
    @Override
    public void registerPartyVotes(boolean aggregated, Map<String, String> electionData) {
        int partyId = Integer.parseInt(electionData.get("AffiliationIdentifier-Id"));
        int validVotes = Integer.parseInt(electionData.get("ValidVotes"));

        PartyVote partyVote = new PartyVote(partyId, validVotes);
        State state = election.getState();

        if (aggregated) {
            handleNationalVotes(state, partyVote);
        } else {
            handleConstituencyVotes(state, electionData, partyVote);
        }
    }

    /**
     * Handles national-level (aggregated) vote totals for a specific party.
     *
     * @param state      the State object representing the national election area
     * @param partyVote     the PartyVote object containing the party ID and number of valid votes

     */
    private void handleNationalVotes(State state, PartyVote partyVote) {
        state.addPartyVote(partyVote);
    }

    /**
     * Handles constituency-level votes for a given party and assigns them
     *
     * @param state         the State object containing all constituencies
     * @param electionData  a map with raw XML data related to the constituency
     * @param partyVote     the PartyVote object containing the party ID and number of valid votes
     */
    private void handleConstituencyVotes(State state, Map<String, String> electionData, PartyVote partyVote) {
        String reportingUnitName = electionData.get("ReportingUnitIdentifier");
        String constituencyName = reportingUnitName.replace("Kieskring ", "").trim();
        Constituency constituency = state.getConstituencyByName(constituencyName);
        constituency.addPartyVote(partyVote);
    }

    /**
     * Registers votes for a candidate at the national level.
     *
     * @param aggregated whether the data is aggregated nationally
     * @param electionData map containing candidate data
     */
    @Override
    public void registerCandidateVotes(boolean aggregated, Map<String, String> electionData) {
        String candidateShortCode = electionData.get("CandidateIdentifier-ShortCode");
        int validVotes = Integer.parseInt(electionData.get("ValidVotes"));

        State state = election.getState();

        if (aggregated) {
            handleNationalCandidateVotes(state, candidateShortCode, validVotes);
        } else {
            handleConstituencyCandidateVotes(state, electionData, candidateShortCode, validVotes);
        }
    }

    /**
     * Handles national-level (aggregated) votes for a candidate.
     *
     * @param state      the state containing all national vote data
     * @param shortCode  the candidate’s unique short code
     * @param validVotes the total number of valid votes received nationally
     */
    private void handleNationalCandidateVotes(State state, String shortCode, int validVotes) {
        CandidateNationalVote candidate = new CandidateNationalVote(shortCode);
        candidate.setVotes(validVotes);
        state.addNationalCandidateVote(candidate);
    }

    /**
     * Handles constituency-level votes for a candidate.
     *
     * @param state        the state containing all national vote data
     * @param electionData map with constituency-specific vote data
     * @param shortCode    the candidate’s unique short code
     * @param validVotes   the number of valid votes in this constituency
     */
    private void handleConstituencyCandidateVotes(State state, Map<String, String> electionData,
                                                  String shortCode, int validVotes) {
        String constituencyName = electionData.get("ReportingUnitIdentifier");

        CandidateNationalVote candidate = state.getNationalCandidateShortCode(shortCode);
        if (candidate == null) {
            candidate = new CandidateNationalVote(shortCode);
            state.addNationalCandidateVote(candidate);
        }

        NationalCandidateConstituencyVote vote = new NationalCandidateConstituencyVote(constituencyName, validVotes);
        candidate.addConstituencyVote(vote);
    }


    /**
     * Registers election metadata at the national level, including
     * total cast votes, counted votes, rejected votes, and uncounted votes.
     *
     * @param aggregated whether the data is aggregated nationally
     * @param electionData map containing metadata values
     */
    @Override
    public void registerMetadata(boolean aggregated, Map<String, String> electionData) {
        if (aggregated) {
            parseMetadataForNational(electionData);
        } else {
            parseMetadataForConstituency(electionData);
        }
    }

    /**
     * Parses and assigns metadata for the national level.
     *
     * @param electionData the election data map
     */
    private void parseMetadataForNational(Map<String, String> electionData) {
        Metadata metadata = createMetadata(electionData);
        State state = election.getState();
        state.setMetadata(metadata);
    }

    /**
     * Parses and assigns metadata for a specific constituency.
     *
     * @param electionData the election data map
     */
    private void parseMetadataForConstituency(Map<String, String> electionData) {
        Metadata metadata = createMetadata(electionData);
        State state = election.getState();

        String reportingUnitName = electionData.get("ReportingUnitIdentifier");

        String constituencyName = reportingUnitName.replace("Kieskring ", "").trim();
        Constituency constituency = state.getConstituencyByName(constituencyName);
        if (constituency != null) {
            constituency.setMetadata(metadata);
        }
    }

    /**
     * Creates a Metadata object from the election data.
     *
     * @param electionData the election data map
     * @return a Metadata object with parsed values
     */
    private Metadata createMetadata(Map<String, String> electionData) {
        String castValue = electionData.get("Cast");
        int cast = castValue != null ? Integer.parseInt(castValue) : 0;
        int totalCounted = Integer.parseInt(electionData.get("TotalCounted"));
        int invalid = Integer.parseInt(electionData.getOrDefault("RejectedVotes-ongeldig", "0"));
        int blank = Integer.parseInt(electionData.getOrDefault("RejectedVotes-blanco", "0"));

        return new Metadata(
                cast,
                totalCounted,
                invalid,
                blank
        );
    }
}
