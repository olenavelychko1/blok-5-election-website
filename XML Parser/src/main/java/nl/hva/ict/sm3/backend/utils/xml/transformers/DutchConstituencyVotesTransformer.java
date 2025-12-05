package nl.hva.ict.sm3.backend.utils.xml.transformers;

import nl.hva.ict.sm3.backend.model.*;
import nl.hva.ict.sm3.backend.utils.xml.TagAndAttributeNames;
import nl.hva.ict.sm3.backend.utils.xml.VotesTransformer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Transformer responsible for processing constituency-level election data.
 * <p>
 * This class implements {@link VotesTransformer} and handles:
 * <ul>
 *     <li>Party votes registration</li>
 *     <li>Candidate votes registration</li>
 *     <li>Election metadata registration</li>
 * </ul>
 * It uses prebuilt lookup maps for fast access to {@link Constituency} and {@link Municipality}
 * objects during XML parsing.
 * </p>
 */
public class DutchConstituencyVotesTransformer implements VotesTransformer, TagAndAttributeNames {
    private final Election election;

    // Prebuild lookup maps once for fast O(1) access during XML processing.
    // This avoids repeatedly looping through constituencies/municipalities for each tag.
    private Map<Integer, Constituency> constituencyMap;
    private  Map<Integer, Municipality> municipalityMap;

    private Party currentParty; // the current party being processed

    /**
     * Creates a new transformer for handling the votes at the constituency level. It expects an instance of
     * Election that can be used for storing the results.
     *
     * @param election the election in which the votes will be stored.
     */
    public DutchConstituencyVotesTransformer(Election election) {
        this.election = election;

    }

    /**
     * Registers the party votes found in the electionData map.
     *
     * @param aggregated   <code>true</code> when the votes at the aggregation (constituency) level are processed,
     *                     <code>false</code> otherwise (polling station).
     * @param electionData the tags and values.
     */
    @Override
    public void registerPartyVotes(boolean aggregated, Map<String, String> electionData) {
        ensureMapsInitialized(); // make sure 0(1) lookup maps exist before using them

        // if aggregated, the locationKey is Constituency level (AUTHORITY_IDENTIFIER),
        // otherwise it's municipality level (REPORTING_UNIT_IDENTIFIER)
        boolean isConstituency = aggregated;

        int partyId = Integer.parseInt(electionData.get("AffiliationIdentifier-Id"));
        int validVotes = Integer.parseInt(electionData.get(VALID_VOTES));
        PartyVote partyVote = new PartyVote(partyId, validVotes);

        this.currentParty = new Party(partyId); // Set the current party

        if (isConstituency) {
            saveConstituencyPartyVote(electionData, partyVote);
        } else {
            saveMunicipalityPartyVote(electionData, partyVote);
        }
    }

    /**
     * Registers the candidate votes found in the electionData map.
     *
     * @param aggregated   <code>true</code> when the votes at the aggregation (constituency) level are processed,
     *                     <code>false</code> otherwise (polling station).
     * @param electionData the tags and values.
     */
    @Override
    public void registerCandidateVotes(boolean aggregated, Map<String, String> electionData) {
        ensureMapsInitialized(); // make sure 0(1) lookup maps exist before using them

        // if aggregated, the locationKey is Constituency level (AUTHORITY_IDENTIFIER),
        // otherwise it's municipality level (REPORTING_UNIT_IDENTIFIER)
        boolean isConstituency = aggregated;

        RegionCandidateVote candidateVote = parseCandidateVote(electionData);

        if (isConstituency) {
            // Constituency level
            int constituencyId = Integer.parseInt(electionData.get(CONTEST_IDENTIFIER + "-Id"));
            Constituency c = constituencyMap.get(constituencyId);
            if (c != null) {
                c.addCandidateVote(candidateVote);
            }
        } else {
            // Municipality level
            int municipalityId = parseMunicipalityId(electionData);
            Municipality m = municipalityMap.get(municipalityId);
            if (m != null) {
                m.addCandidateVote(candidateVote);
            }
        }
    }

    /**
     * Registers the election metadata found in the electionData map. Metadata found at the end of each TotalVotes like UncountedVotes, RejectedVotes, etc.
     *
     * @param aggregated   <code>true</code> when the votes at the aggregation (constituency) level are processed,
     *                     <code>false</code> otherwise (polling station).
     * @param electionData the tags and values.
     *
     *
     */
    @Override
    public void registerMetadata(boolean aggregated, Map<String, String> electionData) {
        ensureMapsInitialized(); // make sure 0(1) lookup maps exist before using them

        // if aggregated, the locationKey is Constituency level (AUTHORITY_IDENTIFIER),
        // otherwise it's municipality level (REPORTING_UNIT_IDENTIFIER)
        boolean isConstituency = aggregated;

        Metadata metadata = createMetadata(electionData);

        if (isConstituency) {
            int id = Integer.parseInt(electionData.get(CONTEST_IDENTIFIER + "-Id"));
            Constituency c = constituencyMap.get(id);
            if (c != null) c.setMetadata(metadata);
        } else {
            int id = parseMunicipalityId(electionData);
            Municipality m = municipalityMap.get(id);
            if (m != null) m.setMetadata(metadata);
        }
    }

    // ------------- Parsing Helpers --------

    /**
     * Saves a party vote to the appropriate constituency.
     *
     * @param electionData the election data map
     * @param partyVote    the {@link PartyVote} to save
     */
    private void saveConstituencyPartyVote(Map<String, String> electionData, PartyVote partyVote) {
        int constituencyId = Integer.parseInt(electionData.get(CONTEST_IDENTIFIER + "-Id"));

        Constituency constituency = constituencyMap.get(constituencyId);
        if (constituency == null) {
            System.out.printf("Constituency %s not found in election!\n", constituencyId);
            return;
        }

        constituency.addPartyVote(partyVote);
    }

    /**
     * Saves a party vote to the appropriate municipality.
     *
     * @param electionData the election data map
     * @param partyVote    the {@link PartyVote} to save
     */
    private void saveMunicipalityPartyVote(Map<String, String> electionData, PartyVote partyVote) {
        int municipalityId = parseMunicipalityId(electionData);

        Municipality municipality = municipalityMap.get(municipalityId);
        if (municipality == null) {
            System.out.printf("Municipality %s not found in election!\n", municipalityId);
            return;
        }

        municipality.addPartyVote(partyVote);
    }

    /**
     * Parse candidate vote information from election data.
     *
     * @param electionData the election data map
     * @return a {@link RegionCandidateVote} object representing the candidate's votes
     */
    private RegionCandidateVote parseCandidateVote(Map<String, String> electionData) {
        int candidateId = Integer.parseInt(electionData.get("CandidateIdentifier-Id"));
        int validVotes = Integer.parseInt(electionData.get(VALID_VOTES));

        // Use the currentParty that you set during parsePartyVotes
        int partyId = currentParty != null ? currentParty.getId() : 0;

        return new RegionCandidateVote(candidateId, partyId, validVotes);
    }

    /**
     * Creates a {@link Metadata} object from the election data.
     *
     * @param electionData  the election data map
     * @return a populated {@link Metadata} object
     */
    private Metadata createMetadata(Map<String, String> electionData) {
        int cast = Integer.parseInt(electionData.get(CAST));
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

    // ------------- Helper Methods --------

    /**
     * Builds a lookup map of all constituencies in the current election, once the DutchConstituencyVotesTransformer is created.
     *
     * <p>This method iterates once through the list of constituencies in the election state,
     * and creates a {@code Map<Integer, Constituency>} where the key is the constituency's ID
     * and the value is the corresponding {@link Constituency} object.</p>
     *
     * <p>By precomputing this lookup map once in the constructor, later access to specific
     * constituencies by ID becomes an O(1) hash map lookup instead of repeatedly scanning
     * the list, improving performance when handling many XML elements.</p>
     *
     * @param election the election object containing all constituencies
     * @return a map that links each constituency ID to its object
     */
    private Map<Integer, Constituency> buildConstituencyMap(Election election) {
        if (election.getState() == null) return Map.of();
        List<Constituency> list = election.getState().getConstituencyList();
        return list.stream().collect(Collectors.toMap(Constituency::getId, c -> c));
    }

    /**
     * Builds a lookup map of all municipalities across all constituencies in the election.
     *
     * <p>This goes through all constituencies and their municipalities,
     * and puts them into a map where the key is the municipality ID
     * and the value is the Municipality object.</p>
     *
     * <p>By building this map up front, we avoid repeated nested loops whenever we need to
     * retrieve a municipality by ID during vote registration.</p>
     *
     * @param election the election that contains all constituencies and municipalities
     * @return a map that links each municipality ID to its object
     */
    private Map<Integer, Municipality> buildMunicipalityMap(Election election) {
        if (election.getState() == null) return Map.of();
        return election.getState().getConstituencyList().stream()
                .flatMap(c -> c.getMunicipalityList().stream())
                .collect(Collectors.toMap(Municipality::getId, m -> m));
    }

    /**
     * Extracts the municipality ID from the election data map.
     *
     * @param electionData the election data map
     * @return the municipality ID as Integer
     */
    private Integer parseMunicipalityId(Map<String, String> electionData) {
        String municipalityId = electionData.get(REPORTING_UNIT_IDENTIFIER + "-Id");
        if (municipalityId != null) {
            // has to be done, because the id for each municipality inside kieskring files is HSB{constituencyId}::0{municipalityId}
            String[] parts = municipalityId.split("::");

            return Integer.parseInt(parts[1]);
        }
        throw new IllegalArgumentException("Municipality ID is null");
    }

    /**
     * Initializes lookup maps for fast access during XML parsing.
     * <p>
     * Must be called after the election object is created and before parsing votes.
     * </p>
     */
    private void ensureMapsInitialized() {
        if (constituencyMap == null || municipalityMap == null) {
            this.constituencyMap = buildConstituencyMap(this.election);
            this.municipalityMap = buildMunicipalityMap(this.election);
            System.out.println("Initialized lookup maps inside constituency transformer");
        }
    }
}
