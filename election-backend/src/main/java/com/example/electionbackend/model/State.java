package com.example.electionbackend.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a state, which is a specific type of region
 */
@Entity
@Table(name = "state")
public class State extends Region {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    // --- Relationships ---

    /**
     * The state to which this state belongs.
     * Mapped as a many-to-one relationship with State.
     */
    @OneToOne
    @JoinColumn(name = "election_id", referencedColumnName = "id")
    private Election election;

    /**
     * List of constituencies in this state.
     * Mapped as a one-to-many relationship with Constituency
     */
    @OneToMany(mappedBy = "state", cascade = CascadeType.ALL)
    private final List<Constituency> constituencyList = new ArrayList<>();

    /**
     * List of party votes in this state.
     * Mapped as a one-to-many relationship with PartyVote
     */
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id", referencedColumnName = "id")
    public List<PartyVote> partyVoteList = new ArrayList<>();

    /**
     * Metadata associated with this state.
     * Mapped as a one-to-one relationship with Metadata
     */
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "metadata_id", referencedColumnName = "id")
    private Metadata metadata;

    /**
     * Default constructor for creating an empty State class.
     */
    public State() {
        super();
    }

    @Override
    public String getName() {
        return super.getName();
    }

    public void setName(String name) {
        super.setName(name);
    }

    // --- Election relationship ---

    /**
     * Retrieves the election associated with this state.
     *
     * @return the Election object associated with this state, or null if no election is set
     */
    public Election getElection() {
        return election;
    }

    /**
     * Sets the election associated with this state.
     *
     * @param election the Election object to associate with this state
     */
    public void setElection(Election election) {
        this.election = election;
    }

    // --- Constituency relationship ---

    /**
     * Retrieves a list of constituencies
     * @return a list of constituencies
     */
    public List<Constituency> getConstituencyList() {
        return constituencyList;
    }

    /**
     * Adds a constituency class to the list
     * @param constituency a Constituency class
     */
    public void addConstituency(Constituency constituency) {
        // Add validation so no duplicate constituency can be in the list
        if (!this.constituencyList.contains(constituency)) {
            this.constituencyList.add(constituency);
            constituency.setState(this);
        }
    }

    /**
     * Retrieves a Constituency from the list of constituencies using the specified ID.
     * If no matching constituency is found, returns null.
     *
     * @param id the unique identifier of the constituency to retrieve
     * @return the Constituency object with the specified ID, or null if not found
     */
    public Constituency getConstituency(int id) {
        for (Constituency constituency : this.constituencyList) {
            if (constituency.getId() == id)
                return constituency;
        }
        return null;
    }

    /**
     * Retrieves a list of party votes from national level
     * @return a list of party votes from national level
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
     * Returns the metadata linked to this state.
     *
     * @return the state's metadata
     */
    public Metadata getMetadata() {
        return metadata;
    }

    /**
     * Sets the metadata linked to this state.
     *
     * @param metadata the metadata to set
     */
    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    /**
     * Returns the unique ID of the state.
     *
     * @return the state ID
     */
    public int getId() {
        return id;
    }

    /**
     * Updates the metadata region ID after the state is saved.
     * Ensures the region ID matches the state's database ID.
     */
    @PostPersist
    private void assignRegionIdToMetadata() {
        if (metadata != null && (metadata.getRegionId() == null || metadata.getRegionId().equals("0"))) {
            metadata.setRegionId(String.valueOf(this.id));
        }
    }
}
