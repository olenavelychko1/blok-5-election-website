package com.example.electionbackend.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

/**
 * The election class
 */
@Entity
@Table(name = "election")
public class Election {

    /**
     * The unique identifier for the election
     */
    @Id
    private String id;

    /**
     * The current state of the election
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "state_id", referencedColumnName = "id")
    private State state;

    /**
     * List of parties in the election
     */
    @OneToMany(mappedBy = "election", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Party> partyList = new ArrayList<>();

    /**
     * Constructor method of the election class
     */
    public Election() {
        // Default constructor for JPA
    }

    /**
     * Constructor method of the election class
     * @param id The id of the election entity
     */
    public Election(String id) {
        this.id = id;
    }

    /**
     * Gets the id of the election
     * @return Long id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the id of the election
     * @param id The id of the election entity
     */
    public void setId(String id) {
        this.id = id;
    }

    // --- Party ---

    /**
     * Get a list of parties
     * @return parties return a list of parties
     */
    public List<Party> getPartyList() {
        return partyList;
    }

    /**
     * Gets a single party
     * @param id The id of the party entity
     * @return Party entity
     */
    public Party getParty(int id) {
        for (Party party : partyList) {
            if (party.getId() == id) {
                return party;
            }
        }
        return null;
    }

    /**
     * Sets a list of parties
     * @param partyList a list of parties
     */
    public void setPartyList(List<Party> partyList) {
        this.partyList.clear();
        for (Party party : partyList) {
            addParty(party);
        }
    }

    /**
     * Add a single party to the party list
     * @param party a party entity
     */
    public void addParty(Party party) {
        // Add validation so no duplicate party can be in the list
        if (!partyList.contains(party)) {
            partyList.add(party);
            party.setElection(this); // maintain a bidirectional relationship
        }
    }

    // --- State ---

    /**
     * Sets the state to the election
     * @param state a state entity
     */
    public void setState(State state) {
        this.state = state;
        state.setElection(this);
    }

    /**
     * Retrieves the state of the election
     * @return the state of the election
     */
    public State getState() {
        return state;
    }
}
