package nl.hva.ict.sm3.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a specific constituency which inherits properties from the Region class.
 */
public class Municipality extends Region {
    private Metadata metadata;

//    @JsonIgnore // temporary for testing
    private List<PartyVote> partyVoteList = new ArrayList<>();

    @JsonIgnore // temporary for testing
    private final List<RegionCandidateVote> candidateVotes = new ArrayList<>();

    private final List<PollingStation> pollingStationList = new ArrayList<>();
    /**
     * Default constructor for Municipality.
     * Calls the no-argument constructor of the Region class.
     */
    public Municipality() {
        super();
    }

    /**
     * Parameterized constructor that initializes the Municipality with an ID and name.
     *
     * @param id   the Municipality's unique identifier (mapped to regionNumber)
     * @param name the Municipality's name (mapped to regionName)
     */
    public Municipality(int id, String name) {
        super(id, name);
    }

    /**
     * Method to compare objects of the Municipality class
     *
     * @param o the reference object with which to compare.
     * @return - boolean if the object equals the municipality entity
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Municipality municipality = (Municipality) o;
        return Objects.equals(this.getId(), municipality.getId()) &&
                Objects.equals(this.getName(), municipality.getName());
    }

    /**
     * Gets the municipality ID.
     * @return the municipality ID
     */
    public int getId() {
        return super.getRegionNumber();
    }

    /**
     * Sets the municipality ID.
     * @param id the new municipality ID
     */
    public void setId(int id) {
        super.setRegionNumber(id);
    }

    /**
     * Gets the municipality name.
     * @return the municipality name
     */
    public String getName() {
        return super.getRegionName();
    }

    /**
     * Sets the municipality name.
     * @param name the new municipality name
     */
    public void setName(String name) {
        super.setRegionName(name);
    }

    /**
     * Gets a polling station by its id by O(1) lookup
     *
     * @param id - the id of the polling station
     * @return PollingStation - the polling station with the specified id, or null if not found
     */
    public PollingStation getPollingStation(String id) {
        for (PollingStation pollingStation : pollingStationList) {
            if (pollingStation.getPollingStationId().equals(id)) {
                return pollingStation;
            }
        }

        return null;
    }

    /**
     * Gets a list of polling stations in the municipality
     *
     * @return List<PollingStation> - a list of polling stations
     */
    public List<PollingStation> getPollingStationList() {
        return pollingStationList;
    }

    /**
     * Adds a polling station to the municipality's polling station list
     *
     * @param pollingStation - a polling station entity to add
     */
    public void addPollingStation(PollingStation pollingStation) {
        if (!pollingStationList.contains(pollingStation)) {
            this.pollingStationList.add(pollingStation);
        }
    }

    /**
     * Gets the election metadata for the municipality
     *
     * @return ElectionMetadata - the election metadata
     */
    public Metadata getMetadata() {
        return metadata;
    }

    /**
     * Sets the election metadata for the municipality
     *
     * @param metadata - the election metadata to set
     */
    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    /**
     * Gets the list of party votes in the municipality
     * @return - list of MunicipalityPartyVote objects
     */
    public List<PartyVote> getPartyVoteList() {
        return partyVoteList;
    }

    /**
     * Sets the list of party votes in the municipality
     * @param partyVotesList - list of MunicipalityPartyVote objects to set
     */
    public void setPartyVoteList(List<PartyVote> partyVotesList) {
        this.partyVoteList = partyVotesList;
    }

    /**
     * Adds a MunicipalityPartyVote to the list if it doesn't already exist
     *
     * @param partyVote - MunicipalityPartyVote object to add
     */
    public void addPartyVote(PartyVote partyVote) {
        if (!partyVoteList.contains(partyVote)) {
            partyVoteList.add(partyVote);
        }
    }

    /**
     * Gets the candidate votes
     *
     * @return List<RegionCandidateVote>
     */
    public List<RegionCandidateVote> getCandidateVotes() {
        return candidateVotes;
    }

    /**
     * Adds a RegionCandidateVote.
     *
     * @param vote RegionCandidateVote
     */
    public void addCandidateVote(RegionCandidateVote vote) {
        if (!candidateVotes.contains(vote)) {
            candidateVotes.add(vote);
        }
    }
}
