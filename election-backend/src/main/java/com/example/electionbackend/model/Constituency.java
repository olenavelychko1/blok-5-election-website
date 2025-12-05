package com.example.electionbackend.model;

import com.example.electionbackend.type.RegionType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a specific constituency which inherits properties from the Region class.
 */
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "constituency")
public class Constituency extends Region {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // --- Relationships ---

    /**
     * The state to which this constituency belongs.
     * Mapped as a many-to-one relationship with State.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "state_id")
    private State state;

    /**
     * List of municipalities in this constituency.
     * Mapped as a one-to-many relationship with Municipality
     */
    @OneToMany(mappedBy = "constituency", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private final List<Municipality> municipalityList = new ArrayList<>();

    /**
     * List of party votes in this municipality.
     * Mapped as a one-to-many relationship with PartyVote
     */
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id", referencedColumnName = "id")
    public List<PartyVote> partyVoteList = new ArrayList<>();

    /**
     * Metadata associated with this constituency.
     * Mapped as a one-to-one relationship with Metadata
     */
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "metadata_id", referencedColumnName = "id")
    private Metadata metadata;


    /**
     * Default constructor for Constituency.
     * Calls the no-argument constructor of the Region class.
     */
    public Constituency() {
        super();
    }

    /**
     * Parameterized constructor that initializes the Constituency with an ID and name.
     *
     * @param name the constituency's name (mapped to name)
     */
    public Constituency(String name) {
        super(name);
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
        Constituency constituency = (Constituency) o;
        return Objects.equals(this.getId(), constituency.getId()) &&
                Objects.equals(this.getName(), constituency.getName());
    }

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

    public String getName() {
        return super.getName();
    }

    /**
     * Sets the name of the constituency.
     *
     * @param name the name to assign to the constituency
     */
    public void setName(String name) {
        super.setName(name);
    }

    /**
     * Gets state.
     *
     * @return the state
     */
    public State getState() {
        return state;
    }

    /**
     * Sets state.
     *
     * @param state the state
     */
    public void setState(State state) {
        this.state = state;
    }

    /**
     * Retrieves a list of municipalities
     *
     * @return a list of municipalities
     */
    public List<Municipality> getMunicipalityList() {
        return municipalityList;
    }

    /**
     * Adds a municipality class the list
     * @param m a Municipality class
     */
    public void addMunicipality(Municipality m) {
        if (!municipalityList.contains(m)) {
            municipalityList.add(m);
            m.setConstituency(this);
        }
    }

    // --- Party vote relationship ---

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
     * Gets the election metadata for the constituency
     *
     * @return - the election metadata
     */
    public Metadata getMetadata() {
        return metadata;
    }

    /**
     * Sets the election metadata for the constituency
     *
     * @param metadata - the election metadata to set
     */
    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
        if (metadata != null) {
            metadata.setRegionId(String.valueOf(this.getId()));
            metadata.setRegionType(RegionType.CONSTITUENCY);
        }
    }

    @PostPersist
    private void assignMetadataRegionIdAfterPersist() {
        if (this.metadata != null) {
            metadata.setRegionId(String.valueOf(this.id));
        }
    }
}