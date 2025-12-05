package nl.hva.ict.sm3.backend.utils.xml.transformers;

import nl.hva.ict.sm3.backend.model.*;
import nl.hva.ict.sm3.backend.utils.xml.TagAndAttributeNames;
import nl.hva.ict.sm3.backend.utils.xml.VotesTransformer;

import java.util.HashMap;
import java.util.Map;

/**
 * Transformer for handling the votes at the municipality level.
 */
public class DutchMunicipalityVotesTransformer implements VotesTransformer, TagAndAttributeNames {
    private final Election election;

    /**
     * The current party being processed.
     */
    private Party currentParty;

    /**
     * Creates a new transformer for handling the votes at the municipality level. It expects an instance of
     * Election that can be used for storing the results.
     *
     * @param election the election in which the votes wil be stored.
     */
    public DutchMunicipalityVotesTransformer(Election election) {
        this.election = election;
    }

    /**
     * Registers the party votes found in the electionData map.
     *
     * @param aggregated   <code>true</code> when the votes at the aggregation level are processed, <code>false</code>
     *                     otherwise.
     * @param electionData the tags and values.
     */
    @Override
    public void registerPartyVotes(boolean aggregated, Map<String, String> electionData) {
        if (!aggregated) {
            parsePollingStationVotes(electionData);
        }
        // Save municipality votes per party
        else {
            parseMunicipalityVotes(electionData);
        }

    }

    /**
     * Registers the candidate votes found in the electionData map.
     * <p>
     * We probably won't use this
     *
     * @param aggregated   <code>true</code> when the votes at the aggregation level are processed, <code>false</code>
     *                     otherwise.
     * @param electionData the tags and values.
     */
    @Override
    public void registerCandidateVotes(boolean aggregated, Map<String, String> electionData) {
        if (!aggregated) {
            Municipality municipality = getMunicipalityFromElectionData(electionData);
            if (municipality == null) return;

            PollingStation pollingStation = getOrCreatePollingStation(municipality, electionData);

            RegionCandidateVote candidateVote = createAndAssignPartyToCandidateVote(electionData, currentParty);
            pollingStation.addRegionCandidateVote(candidateVote);
        }
    }

    /**
     * Registers the election metadata found in the electionData map.
     *
     * @param aggregated   <code>true</code> when the votes at the aggregation level are processed, <code>false</code>
     *                     otherwise.
     * @param electionData the tags and values.
     */
    @Override
    public void registerMetadata(boolean aggregated, Map<String, String> electionData) {
        // Municipality level
        if (aggregated) {
            parseMetadata(electionData, AUTHORITY_IDENTIFIER);
        }
        // Polling station level
        else {
            parseMetadata(electionData, REPORTING_UNIT_IDENTIFIER);
        }
    }

    /**
     * Retrieves the Municipality from the election data.
     *
     * @param electionData - the election data map
     * @return - the Municipality object
     */
    private Municipality getMunicipalityFromElectionData(Map<String, String> electionData) {
        int municipalityId = Integer.parseInt(electionData.get(AUTHORITY_IDENTIFIER + "-Id"));
        return findMunicipality(municipalityId);
    }

    /**
     * Finds or creates a PollingStation in the given Municipality based on the election data.
     *
     * @param municipality - the Municipality to search in
     * @param electionData - the election data map
     * @return - the found or newly created PollingStation
     */
    private PollingStation getOrCreatePollingStation(Municipality municipality, Map<String, String> electionData) {
        String pollingStationId = electionData.get(REPORTING_UNIT_IDENTIFIER + "-Id");
        PollingStation pollingStation = municipality.getPollingStation(pollingStationId);
        if (pollingStation == null) {
            pollingStation = createPollingStation(electionData);
            municipality.addPollingStation(pollingStation);
        }
        return pollingStation;
    }

    /**
     * Creates a PollingStation from the election data.
     *
     * @param electionData - the election data map
     * @return - a PollingStation object
     */
    private PollingStation createPollingStation(Map<String, String> electionData) {
        String pollingStationId = electionData.get(REPORTING_UNIT_IDENTIFIER + "-Id");
        String pollingStationName = electionData.get(REPORTING_UNIT_IDENTIFIER);
        return new PollingStation(pollingStationId, pollingStationName);
    }

    /**
     * Finds a municipality by its ID in the election's constituencies.
     *
     * @param municipalityId - the ID of the municipality to find
     * @return - the Municipality object if found, null otherwise
     */
    private Municipality findMunicipality(int municipalityId) {
        for (Constituency c : election.getState().getConstituencyList()) {
            Municipality m = c.getMunicipalityById(municipalityId); // O(1) lookup
            if (m != null) {
                return m;
            }
        }
        return null;
    }

    /**
     * Creates a PollingStationCandidateVote and assigns the party ID to it.
     *
     * @param electionData - the election data map
     * @param party        - the Party to assign
     * @return - a PollingStationCandidateVote object with the party ID assigned
     */
    private RegionCandidateVote createAndAssignPartyToCandidateVote(Map<String, String> electionData, Party party) {
        RegionCandidateVote candidateVote = createPollingStationCandidateVote(electionData);
        candidateVote.setPartyId(party.getId());
        return candidateVote;
    }

    /**
     * Saves polling station party votes to the polling station in the municipality.
     *
     * @param electionData - the election data map
     */
    private void parsePollingStationVotes(Map<String, String> electionData) {
        // Find municipality
        Municipality municipality = getMunicipalityFromElectionData(electionData);

        // Get the party and votes
        int partyId = Integer.parseInt(electionData.get(AFFILIATION_IDENTIFIER + "-Id"));
        int votes = Integer.parseInt(electionData.get(VALID_VOTES));
        this.currentParty = new Party(partyId); // Set the current party

        if (municipality != null) {
            // Find or create the polling station, then add party votes
            PollingStation pollingStation = getOrCreatePollingStation(municipality, electionData);
            PartyVote partyVote = new PartyVote(partyId, votes);
            pollingStation.addPartyVote(partyVote);
        }
    }

    /**
     * Saves municipality party votes to the municipality in the election.
     *
     * @param electionData - the election data map
     */
    private void parseMunicipalityVotes(Map<String, String> electionData) {
        // Find municipality
        Municipality municipality = getMunicipalityFromElectionData(electionData);

        // Get the party and votes
        int partyId = Integer.parseInt(electionData.get(AFFILIATION_IDENTIFIER + "-Id"));
        int votes = Integer.parseInt(electionData.get(VALID_VOTES));

        // Add party votes to the municipality
        if (municipality != null) {
            PartyVote partyVote = new PartyVote(partyId, votes);
            municipality.addPartyVote(partyVote);
        }
    }

    /**
     * Parses the election metadata from the election data.
     * Creates maps for rejected and uncounted votes with their reasons and counts
     *
     * @param electionData - the election data map
     * @param location     - the location key to extract the location name (municipality or polling station name)
     */
    private void parseMetadata(Map<String, String> electionData, String location) {
        Metadata metadata = createMetadata(electionData);
        Municipality municipality = getMunicipalityFromElectionData(electionData);
        if (municipality == null) return;

        if (location.equals(AUTHORITY_IDENTIFIER)) {
            municipality.setMetadata(metadata);
            return;
        }

        PollingStation pollingStation = getOrCreatePollingStation(municipality, electionData);
        pollingStation.setMetadata(metadata);
    }

    /**
     * Creates ElectionMetadata from the election data.
     *
     * @param electionData - the election data map
     * @return - an ElectionMetadata object containing the parsed data
     */
    private Metadata createMetadata(Map<String, String> electionData) {
        String castValue = electionData.get(CAST);
        int cast = castValue != null ? Integer.parseInt(castValue) : 0;
        int totalCounted = Integer.parseInt(electionData.get(TOTAL_COUNTED));
        int invalid = Integer.parseInt(electionData.getOrDefault(REJECTED_VOTES + "-ongeldig", "0"));
        int blank = Integer.parseInt(electionData.getOrDefault(REJECTED_VOTES + "-blanco", "0"));

        // Not in the requirements
        // Map<String, Integer> uncountedVotes = this.parseUncountedVotes(electionData);

        return new Metadata(
                cast,
                totalCounted,
                invalid,
                blank
        );
    }

    /**
     * Parses rejected votes from the election data.
     *
     * @param electionData - the election data map
     * @return - a map of rejected vote reasons and their counts
     */
    private Map<String, Integer> parseRejectedVotes(Map<String, String> electionData) {
        Map<String, Integer> rejectedVotes = new HashMap<>();
        rejectedVotes.put("ongeldig", Integer.parseInt(electionData.getOrDefault(REJECTED_VOTES + "-ongeldig", "0")));
        rejectedVotes.put("blanco", Integer.parseInt(electionData.getOrDefault(REJECTED_VOTES + "-Blanco", "0")));
        return rejectedVotes;
    }

    /**
     * Parses uncounted votes from the election data.
     *
     * @param electionData - the election data map
     * @return - a map of uncounted vote reasons and their counts
     */
    private Map<String, Integer> parseUncountedVotes(Map<String, String> electionData) {
        Map<String, Integer> uncountedVotes = new HashMap<>();
        String[] uncountedVotesKeys = {
                "geldige stempassen",
                "geldige volmachtbewijzen",
                "geldige kiezerspassen",
                "toegelaten kiezers",
                "meer getelde stembiljetten",
                "minder getelde stembiljetten",
                "meegenomen stembiljetten",
                "te weinig uitgereikte tembiljetten",
                "te veel uitgereikte stembiljetten",
                "geen verklaring",
                "andere verklaring"
        };

        for (String key : uncountedVotesKeys) {
            uncountedVotes.put(key, Integer.parseInt(electionData.getOrDefault(UNCOUNTED_VOTES + "-" + key, "0")));
        }

        return uncountedVotes;
    }

    /**
     * Creates a PollingStationCandidateVote from the election data.
     *
     * @param electionData - the election data map
     * @return - a PollingStationCandidateVote object
     */
    private RegionCandidateVote createPollingStationCandidateVote(Map<String, String> electionData) {
        return new RegionCandidateVote(
                Integer.parseInt(electionData.get(CANDIDATE_IDENTIFIER + "-Id")),
                this.currentParty.getId(),
                Integer.parseInt(electionData.get(VALID_VOTES))
        );
    }
}
