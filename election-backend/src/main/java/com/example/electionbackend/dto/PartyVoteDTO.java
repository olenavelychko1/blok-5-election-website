package com.example.electionbackend.dto;

import com.example.electionbackend.type.RegionType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Data Transfer Object representing the number of votes a party received
 * in a specific region (e.g., municipality, constituency, or other region types).
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PartyVoteDTO {
    private int partyId;
    private int votes;
    private RegionType regionType;
    private int regionId;

    /**
     * Default constructor.
     */
    public PartyVoteDTO() {
    }

    public PartyVoteDTO(int partyId, int votes, RegionType regionType, int regionId) {
        this.partyId = partyId;
        this.votes = votes;
        this.regionType = regionType;
        this.regionId = regionId;
    }

    /**
     * Gets the party ID.
     *
     * @return the party ID
     */
    public int getPartyId() {
        return partyId;
    }

    /**
     * Sets the party ID.
     *
     * @param partyId the new party ID
     */
    public void setPartyId(int partyId) {
        this.partyId = partyId;
    }

    /**
     * Gets the number of votes.
     *
     * @return the number of votes
     */
    public int getVotes() {
        return votes;
    }

    /**
     * Sets the number of votes.
     *
     * @param votes the new number of votes
     */
    public void setVotes(int votes) {
        this.votes = votes;
    }

    /**
     * Gets the region type.
     *
     * @return the region type
     */
    public RegionType getRegionType() {
        return regionType;
    }

    /**
     * Sets the region type.
     *
     * @param regionType the new region type
     */
    public void setRegionType(RegionType regionType) {
        this.regionType = regionType;
    }

    /**
     * Gets the region ID.
     *
     * @return the region ID
     */
    public int getRegionId() {
        return regionId;
    }

    /**
     * Sets the region ID.
     *
     * @param regionId the new region ID
     */
    public void setRegionId(int regionId) {
        this.regionId = regionId;
    }
}
