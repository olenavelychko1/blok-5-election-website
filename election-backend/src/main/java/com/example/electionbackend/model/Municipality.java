package com.example.electionbackend.model;

import com.example.electionbackend.type.RegionType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a specific municipality which inherits properties from the Region class.
 */
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "municipality")
public class Municipality extends Region {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // --- Relationships ---

    /**
     * The constituency to which this municipality belongs.
     * Mapped as a many-to-one relationship with Constituency.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "constituency_id")
    @JsonBackReference
    private Constituency constituency;

    /**
     * List of polling stations in this municipality.
     * Mapped as a one-to-many relationship with PollingStation
     */
    @OneToMany(mappedBy = "municipality", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private final List<PollingStation> pollingStationList = new ArrayList<>();

    /**
     * List of party votes in this municipality.
     * Mapped as a one-to-many relationship with PartyVote
     */
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id", referencedColumnName = "id")
    public List<PartyVote> partyVoteList = new ArrayList<>();

    /**
     * Metadata associated with this municipality.
     * Mapped as a one-to-one relationship with Metadata
     */
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "metadata_id", referencedColumnName = "id")
    private Metadata metadata;

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
     * @param name the Municipality's name (mapped to name)
     */
    public Municipality(String name) {
        super(name);
    }

    /**
     * Method to compare objects
     *
     * @param o Object of the municipality class
     * @return boolean if the object equals the municipality entity
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Municipality municipality = (Municipality) o;
        return Objects.equals(this.getId(), municipality.getId()) &&
                Objects.equals(this.getName(), municipality.getName());
    }

    // --- ID and Name mapping to Region ---
    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retrieves
     */
    public String getName() {
        return super.getName();
    }

    /**
     * Updates the name of the municipality.
     *
     * @param name the new name to assign to the municipality
     */
    public void setName(String name) {
        super.setName(name);
    }

    /**
     * Gets constituency.
     *
     * @return the constituency
     */
    public Constituency getConstituency() {
        return constituency;
    }

    /**
     * Sets constituency.
     *
     * @param constituency the constituency
     */
    public void setConstituency(Constituency constituency) {
        this.constituency = constituency;
    }

    // --- PollingStation relationship ---

    /**
     * Gets a polling station by its id
     *
     * @param id - the id of the polling station
     * @return PollingStation - the polling station with the specified id, or null if not found
     */
    public PollingStation getPollingStation(int id) {
        for (PollingStation ps : pollingStationList) {
            if (ps.getId() == id) {
                return ps;
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
     * Sets the municipality of the polling station to this municipality
     *
     * @param pollingStation - a polling station entity to add
     */
    public void addPollingStation(PollingStation pollingStation) {
        if (!pollingStationList.contains(pollingStation)) {
            pollingStationList.add(pollingStation);
            pollingStation.setMunicipality(this);
        }
    }

    /**
     * Gets the list of party votes in the municipality
     *
     * @return List<PartyVote> - a list of party votes
     */
    public List<PartyVote> getPartyVoteList() {
        return partyVoteList;
    }

    /**
     * Sets the list of party votes in the municipality
     *
     * @param partyVoteList - a list of party votes to set
     */
    public void setPartyVoteList(List<PartyVote> partyVoteList) {
        this.partyVoteList = partyVoteList;
    }

    /**
     * Adds a party vote to the municipality's party vote list
     *
     * @param partyVote - a party vote entity to add
     */
    public void addPartyVote(PartyVote partyVote) {
        if (!partyVoteList.contains(partyVote)) {
            partyVoteList.add(partyVote);
            partyVote.setRegionId(this.getId());
        }
    }

    /**
     * Gets the election metadata for the municipality
     *
     * @return - the election metadata
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
        if (metadata != null) {
            metadata.setRegionId(String.valueOf(this.getId()));
            metadata.setRegionType(RegionType.MUNICIPALITY);
        }
    }

    @PostPersist
    private void assignMetadataRegionIdAfterPersist() {
        if (this.metadata != null) {
            metadata.setRegionId(String.valueOf(this.id));
        }
    }
}
