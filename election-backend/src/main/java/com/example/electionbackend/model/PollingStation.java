package com.example.electionbackend.model;

import com.example.electionbackend.type.RegionType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This class represents a PollingStation entity with its associated data.
 */
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "polling_station")
public class PollingStation extends Region {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "polling_station_id")
    private String pollingStationId;

    // --- Relationships ---

    /**
     * List of party votes in this polling station.
     * Mapped as a one-to-many relationship with PartyVote
     */
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id", referencedColumnName = "id")
    public List<PartyVote> partyVoteList = new ArrayList<>();

    /**
     * The municipality to which this polling station belongs.
     * Mapped as a many-to-one relationship with Municipality.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "municipality_id")
    @JsonBackReference
    private Municipality municipality;

    /**
     * Metadata associated with this polling station.
     * Mapped as a one-to-one relationship with Metadata
     */
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "metadata_id", referencedColumnName = "id")
    private Metadata metadata;


    /**
     * Constructor for PollingStation
     *
     * @param pollingStationId - the id of the polling station
     * @param name             - the name of the polling station
     */
    public PollingStation(String pollingStationId, String name) {
        super(name);
        this.pollingStationId = pollingStationId;
    }

    /**
     * Default constructor for PollingStation.
     * Calls the no-argument constructor of the Region class.
     */
    public PollingStation() {
        super();
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
        return Objects.equals(id, pollingStation.id) && Objects.equals(this.getName(), pollingStation.getName());
    }

    /**
     * Returns the integer hash code value of the object
     *
     * @return - int The hashed code of the object
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, this.getName());
    }

    /* --- Getters and setters --- */

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the name of the polling station
     *
     * @return - the name of the polling station
     */
    public String getName() {
        return super.getName();
    }

    /**
     * Sets the name of the polling station
     *
     * @param name - the name to set
     */
    public void setName(String name) {
        super.setName(name);
    }

    /**
     * Gets the municipality of the polling station
     *
     * @return - the municipality of the polling station
     */
    public Municipality getMunicipality() {
        return municipality;
    }

    /**
     * Sets the municipality of the polling station
     *
     * @param municipality - the municipality to set
     */
    public void setMunicipality(Municipality municipality) {
        this.municipality = municipality;
    }

    /**
     * Gets the list of party votes in the polling station
     *
     * @return - the list of party votes
     */
    public List<PartyVote> getPartyVoteList() {
        return partyVoteList;
    }

    /**
     * Sets the list of party votes in the polling station
     *
     * @param partyVoteList - the list of party votes to set
     */
    public void setPartyVoteList(List<PartyVote> partyVoteList) {
        this.partyVoteList = partyVoteList;
    }

    /**
     * Adds a party vote to the polling station's party vote list
     *
     * @param partyVote - the party vote to add
     */
    public void addPartyVote(PartyVote partyVote) {
        if (!partyVoteList.contains(partyVote)) {
            partyVoteList.add(partyVote);
            partyVote.setRegionId(this.getId());
        }
    }

    /**
     * Gets the polling station metadata
     *
     * @return - the polling station metadata
     */
    public Metadata getMetadata() {
        return metadata;
    }

    /**
     * Sets the polling station metadata
     *
     * @param metadata - the polling station metadata to set
     */
    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
        if (metadata != null) {
            metadata.setRegionType(RegionType.POLLING_STATION);
        }
    }

    /**
     * Assigns the metadata region ID after the PollingStation entity is persisted.
     * This is needed to ensure the id is not 0 when setting the metadata region ID.
     */
    @PostPersist
    private void assignMetadataRegionIdAfterPersist() {
        if (this.metadata != null) {
            metadata.setRegionId(String.valueOf(this.id));
        }
    }

    /**
     * String representation of the PollingStation object
     *
     * @return - string representation of the PollingStation
     */
    @Override
    public String toString() {
        return "PollingStation {\n" +
                "    id: '" + id + "'\n" +
                "    name: '" + this.getName() + "'\n" +
                "}";
    }
}
