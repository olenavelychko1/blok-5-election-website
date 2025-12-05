package com.example.electionbackend.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a specific constituency which inherits properties from the RegionDTO class.
 */
public class MunicipalityDTO extends RegionDTO {
    private final List<PollingStationDTO> pollingStationList = new ArrayList<>();
    private final List<PartyVoteDTO> partyVoteList = new ArrayList<>();
    private MetadataDTO metadata;

    /**
     * Default constructor for MunicipalityDTO.
     * Calls the no-argument constructor of the RegionDTO class.
     */
    public MunicipalityDTO() {
        super();
    }

    /**
     * Parameterized constructor that initializes the MunicipalityDTO with an ID and name.
     *
     * @param id   the Municipality's unique identifier (mapped to regionNumber)
     * @param name the Municipality's name (mapped to regionName)
     */
    public MunicipalityDTO(int id, String name) {
        super(id, name);
    }

    /**
     * Method to compare objects
     * @param o Object of the party class
     * @return boolean if the object equals the party entity
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MunicipalityDTO municipality = (MunicipalityDTO) o;
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
     * Gets the metadata of the municipality.
     *
     * @return the metadata
     */
    public MetadataDTO getMetadata() {
        return metadata;
    }

    /**
     * Sets the metadata of the municipality.
     *
     * @param metadata the metadata to set
     */
    public void setMetadata(MetadataDTO metadata) {
        this.metadata = metadata;
    }

    // --- Polling Station ---

    public List<PollingStationDTO> getPollingStationList() {
        return pollingStationList;
    }

    public void addPollingStation(PollingStationDTO pollingStation) {
        if (!pollingStationList.contains(pollingStation)) {
            pollingStationList.add(pollingStation);
        }
    }

    public void setPollingStationList(List<PollingStationDTO> pollingStationList) {
        this.pollingStationList.clear();
        this.pollingStationList.addAll(pollingStationList);
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
        this.partyVoteList.addAll(partyVoteList);
    }
}