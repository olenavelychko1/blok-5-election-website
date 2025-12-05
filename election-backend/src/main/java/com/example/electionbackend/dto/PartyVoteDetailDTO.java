package com.example.electionbackend.dto;

import com.example.electionbackend.type.RegionType;

/**
 * Data Transfer Object representing detailed information about the number of votes a party received
 * in a specific region, including party and region names.
 * Extends PartyVoteDTO to include additional details.
 */
public class PartyVoteDetailDTO extends PartyVoteDTO {
    private String partyName;
    private String regionName;
    private int seats;

    /**
     * Default constructor.
     */
    public PartyVoteDetailDTO() {
        super();
    }

    /**
     * Parameterized constructor for PartyVoteDetailDTO.
     *
     * @param partyId    - the ID of the party
     * @param votes      - the number of votes received
     * @param regionType - the type of the region
     * @param regionId   - the ID of the region
     * @param partyName  - the name of the party
     * @param regionName - the name of the region
     */
    public PartyVoteDetailDTO(int partyId, int votes, RegionType regionType, int regionId, String partyName, String regionName, int seats) {
        super(partyId, votes, regionType, regionId);
        this.partyName = partyName;
        this.regionName = regionName;
        this.seats = seats;
    }

    /**
     * Gets the party name.
     *
     * @return the party name
     */
    public String getPartyName() {
        return partyName;
    }

    /**
     * Sets the party name.
     *
     * @param partyName the new party name
     */
    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    /**
     * Gets the region name.
     *
     * @return the region name
     */
    public String getRegionName() {
        return regionName;
    }

    /**
     * Sets the region name.
     *
     * @param regionName the new region name
     */
    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    /**
     * Gets the number of seats in the party
     *
     * @return int - The number of seats
     */
    public int getSeats() {
        return seats;
    }

    /**
     * Sets the number of seats in the party
     *
     * @param seats the number of seats in the party
     */
    public void setSeats(int seats) {
        this.seats = seats;
    }
}
