package com.example.electionbackend.dto;

import java.util.ArrayList;
import java.util.List;


/**
 * The election DTO class
 */
public class ElectionDTO {
    private String id;
    private StateDTO state;
    private List<PartyDTO> partyList = new ArrayList<>();

    public ElectionDTO() {}

    /**
     * Constructor method of the election DTO class
     * @param id The id of the election entity
     */
    public ElectionDTO(String id, StateDTO state, List<PartyDTO> partyList) {
        this.id = id;
        this.state = state;
        this.partyList = partyList;
    }

    /**
     * Gets the id of the election
     * @return string id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the identifier for the election.
     * @param id The unique identifier of the election entity.
     */
    public void setId(String id) {
        this.id = id;
    }

    // --- Party ---

    /**
     * Get a list of parties
     * @return parties return a list of parties
     */
    public List<PartyDTO> getPartyList() {
        return partyList;
    }

    /**
     * Sets a list of parties
     * @param partyList a list of parties
     */
    public void setPartyList(List<PartyDTO> partyList) {
        this.partyList.addAll(partyList);
    }

    /**
     * Add a single party to the party list
     * @param party a party entity
     */
    public void addParty(PartyDTO party) {
        if (!partyList.contains(party)) {
            partyList.add(party);
        }
    }

    // --- State ---

    /**
     * Sets the state to the election
     * @param state a state entity
     */
    public void setState(StateDTO state) {
        this.state = state;
    }

    /**
     * Retrieves the state of the election
     * @return the state of the election
     */
    public StateDTO getState() {
        return state;
    } 
}
