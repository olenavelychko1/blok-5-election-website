package com.example.electionbackend.model;

import com.example.electionbackend.type.RegionType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.Objects;

/**
 * Entity representing the votes received by a party in a specific region.
 */
@Entity
@Table(name = "party_vote")
@JsonIgnoreProperties(ignoreUnknown = true)
public class PartyVote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    @Column(name = "region_type")
    private RegionType regionType;

    @Column(name = "region_id")
    private int regionId;

    private int votes;

    @Column(name = "party_id")
    private int partyId;

    public PartyVote() {
        // Default constructor for JPA
    }

    /**
     * Parameterized constructor for PartyVote.
     *
     * @param regionType - the type of vote
     * @param regionId   - the identifier for the type
     * @param votes      - the number of votes received
     */
    public PartyVote(RegionType regionType, int regionId, int votes) {
        this.regionType = regionType;
        this.regionId = regionId;
        this.votes = votes;
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     *
     * @param o the reference object with which to compare.
     * @return - true if this object is the same as the obj argument; false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PartyVote partyVote = (PartyVote) o;
        return id == partyVote.id && votes == partyVote.votes && regionType == partyVote.regionType
                && Objects.equals(regionId, partyVote.regionId);
    }

    /**
     * Returns a hash code value for the object.
     *
     * @return - a hash code value for this object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, regionType, regionId, votes);
    }

    // --- Getters and Setters ---

    /**
     * Gets the ID of the PartyVote.
     *
     * @return - the ID of the PartyVote.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the PartyVote.
     *
     * @param id - the ID to set.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the type of vote.
     *
     * @return - the type of vote.
     */
    public RegionType getRegionType() {
        return regionType;
    }

    /**
     * Sets the type of vote.
     *
     * @param regionType - the type of vote to set.
     */
    public void setRegionType(RegionType regionType) {
        this.regionType = regionType;
    }

    /**
     * Gets the type ID.
     *
     * @return - the type ID.
     */
    public int getRegionId() {
        return regionId;
    }

    /**
     * Sets the type ID.
     *
     * @param regionId - the type ID to set.
     */
    public void setRegionId(int regionId) {
        this.regionId = regionId;
    }

    /**
     * Gets the number of votes.
     *
     * @return - the number of votes.
     */
    public int getVotes() {
        return votes;
    }

    /**
     * Sets the number of votes.
     *
     * @param votes - the number of votes to set.
     */
    public void setVotes(int votes) {
        this.votes = votes;
    }

    /**
     * Gets the party ID.
     *
     * @return - the party ID.
     */
    public int getPartyId() {
        return partyId;
    }

    /**
     * Sets the party ID.
     *
     * @param partyId - the party ID to set.
     */
    public void setPartyId(int partyId) {
        this.partyId = partyId;
    }

    @Override
    public String toString() {
        return "PartyVote {\n" +
                "  id=" + id + ",\n" +
                "  regionType=" + regionType + ",\n" +
                "  regionId='" + regionId + "',\n" +
                "  votes=" + votes + ",\n" +
                '}';
    }
}
