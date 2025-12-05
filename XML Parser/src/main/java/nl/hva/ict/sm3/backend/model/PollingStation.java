package nl.hva.ict.sm3.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This class represents a PollingStation entity with its associated data.
 */
public class PollingStation {
    private String pollingStationId;
    private String name;

//    @JsonIgnore // temporary for testing
    private List<PartyVote> partyVoteList = new ArrayList<>();

    @JsonIgnore // temporary for testing
    private final List<RegionCandidateVote> regionCandidateVotes = new ArrayList<>();

    private Metadata metadata;

    /**
     * Constructor for PollingStation
     *
     * @param pollingStationId - the id of the polling station
     * @param name             - the name of the polling station
     */
    public PollingStation(String pollingStationId, String name) {
        this.name = name;
        this.pollingStationId = pollingStationId;
    }

    /**
     * Method to compare objects of the PollingStation class
     *
     * @param o the reference object with which to compare.
     * @return - boolean if the object equals the polling station entity
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PollingStation pollingStation = (PollingStation) o;
        return Objects.equals(this.getPollingStationId(), pollingStation.getPollingStationId()) &&
                Objects.equals(this.getName(), pollingStation.getName());
    }

    /**
     * Gets the name of the polling station
     *
     * @return - The name of the polling station
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the polling station
     *
     * @param name - The name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the id of the polling station
     *
     * @return - the id of the polling station
     */
    public String getPollingStationId() {
        return pollingStationId;
    }

    /**
     * Sets the id of the polling station
     *
     * @param pollingStationId - the id to set
     */
    public void setPollingStationId(String pollingStationId) {
        this.pollingStationId = pollingStationId;
    }

    /**
     * Gets the election metadata associated with the polling station
     *
     * @return ElectionMetadata - the election metadata
     */
    public Metadata getMetadata() {
        return metadata;
    }

    /**
     * Sets the election metadata for the polling station
     *
     * @param metadata - the election metadata to set
     */
    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    /**
     * Gets a list of PollingStationCandidateVote objects
     *
     * @return - list of PollingStationCandidateVote objects
     */
    public List<RegionCandidateVote> getRegionCandidateVotes() {
        return regionCandidateVotes;
    }

    /**
     * Adds a PollingStationCandidateVote to the list if it doesn't already exist
     *
     * @param pscv - PollingStationCandidateVote object to add
     */
    public void addRegionCandidateVote(RegionCandidateVote pscv) {
        if (!regionCandidateVotes.contains(pscv)) {
            this.regionCandidateVotes.add(pscv);
        }
    }

    public List<PartyVote> getPartyVoteList() {
        return partyVoteList;
    }

    public void addPartyVote(PartyVote partyVote) {
        if (!partyVoteList.contains(partyVote)) {
            this.partyVoteList.add(partyVote);
        }
    }

    public void setPartyVoteList(List<PartyVote> partyVoteList) {
        this.partyVoteList = partyVoteList;
    }
}
