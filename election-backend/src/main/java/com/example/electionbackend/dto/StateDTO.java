package com.example.electionbackend.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a state, which is a specific type of region
 */
public class StateDTO extends RegionDTO {
    private final List<ConstituencyDTO> constituencyList = new ArrayList<>();
    private final List<PartyVoteDTO> partyVoteList = new ArrayList<>();
    private MetadataDTO metadataDTO;

    /**
     * Default constructor for creating an empty StateDTO class.
     */
    public StateDTO() {
        super();
    }

    /**
     * Constructs a StateDTO with the specified name.
     * @param name the name of the state (mapped to regionName)
     */
    public StateDTO(String name) {
        super(name);
    }

    /**
     * Gets the name of the state.
     * @return the state name
     */
    public String getName() {
        return super.getRegionName();
    }

    /**
     * Sets the name of the state.
     * @param name the new state name
     */
    public void setName(String name) {
        super.setRegionName(name);
    }

    // --- Constituency ---

    /**
     * Retrieves a list of constituencies
     * @return a list of constituencies
     */
    public List<ConstituencyDTO> getConstituencyList() {
        return constituencyList;
    }

    /**
     * Adds a constituency class to the list
     * @param constituency a ConstituencyDTO class
     */
    public void addConstituency(ConstituencyDTO constituency) {
        if (!this.constituencyList.contains(constituency)) {
            this.constituencyList.add(constituency);
        }
    }

    /**
     * Retrieves a constituency by its unique identifier.
     *
     * @param id the unique identifier of the constituency to retrieve
     * @return the ConstituencyDTO object matching the provided ID, or null if no match is found
     */
    public ConstituencyDTO getConstituency(int id) {
        for (ConstituencyDTO constituency : this.constituencyList) {
            if (constituency.getId() == id)
                return constituency;
        }
        return null;
    }

    // --- Party Votes ---

    /**
     * Returns the list of party votes.
     *
     * @return the list of party votes
     */
    public List<PartyVoteDTO> getPartyVoteList() {
        return partyVoteList;
    }

    /**
     * Adds a party vote to the list if it is not already present.
     *
     * @param partyVote the party vote to add
     */
    public void addPartyVote(PartyVoteDTO partyVote) {
        if (!partyVoteList.contains(partyVote)) {
            partyVoteList.add(partyVote);
        }
    }

    // --- Metadata ---

    /**
     * Returns the metadata associated with this object.
     *
     * @return the metadata
     */
    public MetadataDTO getMetadata() {
        return metadataDTO;
    }

    /**
     * Sets the metadata for this object.
     *
     * @param metadataDTO the metadata to set
     */
    public void setMetadata(MetadataDTO metadataDTO) {
        this.metadataDTO = metadataDTO;
    }

}
