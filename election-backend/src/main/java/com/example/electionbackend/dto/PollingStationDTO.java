package com.example.electionbackend.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Data Transfer Object for Polling Station information.
 */
public class PollingStationDTO extends RegionDTO {
    private String pollingStationId;
    private String name = super.getRegionName();
    private MetadataDTO metadata;
    private final List<PartyVoteDTO> partyVoteList = new ArrayList<>();

    /**
     * Default constructor for PollingStationDTO.
     */
    public PollingStationDTO() {
        super();
    }

    /**
     * Constructor for PollingStationDTO with polling station ID and name.
     *
     * @param pollingStationId - the unique identifier for the polling station
     * @param name             - the name of the polling station
     */
    public PollingStationDTO(String pollingStationId, String name) {
        super(name);
        this.pollingStationId = pollingStationId;
    }

    /**
     * Constructor for PollingStationDTO with ID, polling station ID, and name.
     *
     * @param id               - the unique identifier for the region
     * @param pollingStationId - the unique identifier for the polling station
     * @param name             - the name of the polling station
     */
    public PollingStationDTO(int id, String pollingStationId, String name) {
        super(id, name);
        this.pollingStationId = pollingStationId;
    }

    /**
     * Gets the polling station ID.
     * @return the polling station ID
     */
    public String getPollingStationId() {
        return pollingStationId;
    }

    /**
     * Sets the polling station ID.
     * @param pollingStationId the new polling station ID
     */
    public void setPollingStationId(String pollingStationId) {
        this.pollingStationId = pollingStationId;
    }

    /**
     * Gets the name of the polling station.
     * @return the name of the polling station
     */
    public String getName() {
        return super.getRegionName();
    }

    /**
     * Sets the name of the polling station.
     * @param name the new name of the polling station
     */
    public void setName(String name) {
        super.setRegionName(name);
    }

    /** --- Metadata --- */
    public MetadataDTO getMetadata() {
        return metadata;
    }

    public void setMetadata(MetadataDTO metadata) {
        this.metadata = metadata;
    }

    // --- Party Votes ---

    public List<PartyVoteDTO> getPartyVoteList() {
        return partyVoteList;
    }

    public void addPartyVote(PartyVoteDTO partyVote) {
        if (!partyVoteList.contains(partyVote)) {
            partyVoteList.add(partyVote);
        }
    }

    public void setPartyVoteList(List<PartyVoteDTO> partyVoteList) {
        this.partyVoteList.clear();
        if (partyVoteList != null) {
            this.partyVoteList.addAll(partyVoteList);
        }
    }
}
