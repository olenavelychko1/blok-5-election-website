package com.example.electionbackend.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a specific constituency which inherits properties from the RegionDTO class.
 */
public class ConstituencyDTO extends RegionDTO {
    private final int id = super.getRegionNumber();
    private final String name = super.getRegionName();

    private final List<MunicipalityDTO> municipalityList = new ArrayList<>();
    private MetadataDTO metadata;

    /**
     * List of party votes in this constituency.
     */
    private List<PartyVoteDTO> partyVoteList = new ArrayList<>();
    private final List<RegionCandidateVoteDTO> candidateVotes = new ArrayList<>();

    /**
     * Default constructor for ConstituencyDTO.
     * Calls the no-argument constructor of the RegionDTO class.
     */
    public ConstituencyDTO() {
        super();
    }

    /**
     * Parameterized constructor that initializes the ConstituencyDTO with an ID and name.
     *
     * @param id   the constituency's unique identifier (mapped to regionNumber)
     * @param name the constituency's name (mapped to regionName)
     */
    public ConstituencyDTO(int id, String name) {
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
        ConstituencyDTO constituency = (ConstituencyDTO) o;
        return Objects.equals(this.getId(), constituency.getId()) &&
                Objects.equals(this.getName(), constituency.getName());
    }

    /**
     * Gets the constituency ID.
     * @return the constituency ID
     */
    public int getId() {
        return super.getRegionNumber();
    }

    /**
     * Sets the constituency ID.
     * @param id the new constituency ID
     */
    public void setId(int id) {
        super.setRegionNumber(id);
    }

    /**
     * Gets the constituency name.
     * @return the constituency name
     */
    public String getName() {
        return super.getRegionName();
    }

    /**
     * Sets the constituency name.
     * @param name the new constituency name
     */
    public void setName(String name) {
        super.setRegionName(name);
    }

    // --- Municipality ---

    /**
     * Retrieves a list of municipalities
     * @return a list of municipalities
     */
    public List<MunicipalityDTO> getMunicipalityList() {
        return municipalityList;
    }

    /**
     * Adds a municipality class the list
     * @param m a MunicipalityDTO class
     */
    public void addMunicipality(MunicipalityDTO m) {
        if (!municipalityList.contains(m)) {
            municipalityList.add(m);
        }
    }

    /**
     * Gets the list of party votes in the constituency.
     *
     * @return the list of party votes
     */
    public List<PartyVoteDTO> getPartyVotes() {
        return partyVoteList;
    }

    /**
     * Sets the list of party votes in the constituency.
     *
     * @param partyVotes the list of party votes to set
     */
    public void setPartyVotes(List<PartyVoteDTO> partyVotes) {
        this.partyVoteList = partyVotes;
    }

    /**
     * Gets the metadata for the constituency.
     *
     * @return the metadata
     */
    public MetadataDTO getMetadata() {
        return metadata;
    }

    /**
     * Sets the metadata for the constituency.
     *
     * @param metadata the metadata to set
     */
    public void setMetadata(MetadataDTO metadata) {
        this.metadata = metadata;
    }

    // --- Party Votes ---
    /**
     * Returns the list of party votes associated with this constituency.
     *
     * @return a list of {@link PartyVoteDTO} objects representing votes per party
     */
    public List<PartyVoteDTO> getPartyVoteList() {
        return partyVoteList;
    }

    /**
     * Adds a new party vote to the list if it does not already exist.
     *
     * @param partyVote the {@link PartyVoteDTO} to add
     */
    public void addPartyVote(PartyVoteDTO partyVote) {
        if (!partyVoteList.contains(partyVote)) {
            partyVoteList.add(partyVote);
        }
    }

    /**
     * Replaces the current list of party votes with the given list.
     * The existing list is cleared before adding the new elements.
     *
     * @param partyVoteList the new list of {@link PartyVoteDTO} objects
     */
    public void setPartyVoteList(List<PartyVoteDTO> partyVoteList) {
        this.partyVoteList.clear();
        this.partyVoteList.addAll(partyVoteList);
    }
}

