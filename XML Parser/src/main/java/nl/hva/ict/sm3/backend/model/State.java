package nl.hva.ict.sm3.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a state, which is a specific type of region
 */
public class State extends Region {
    private int id = super.getRegionNumber();
    private String name = super.getRegionName();
    private final List<PartyVote> partyVoteList = new ArrayList<>();
    private Metadata metadata;

    private final List<Constituency> constituencyList = new ArrayList<>();
    @JsonIgnore // Temporary, otherwise i cant test if the partyvote list is in the database
    private final List<CandidateNationalVote> nationalCandidateList = new ArrayList<>();


    /**
     * Default constructor for creating an empty State class.
     */
    public State() {
        super();
    }

    /**
     * Constructs a State with the specified name.
     * @param name the name of the state (mapped to regionName)
     */
    public State(String name) {
        super(name);
    }

    /**
     * Gets the name of the state.
     * @return the state name
     */
    public String getName() {
        return super.getRegionName();
    }

    /**
     * Sets the name of the state.
     * @param name the new state name
     */
    public void setName(String name) {
        super.setRegionName(name);
    }

    // --- Constituency ---

    /**
     * Retrieves a list of constituencies
     * @return a list of constituencies
     */
    public List<Constituency> getConstituencyList() {
        return constituencyList;
    }

    /**
     * Adds a constituency class the list
     * @param constituency a Constituency class
     */
    public void addConstituency(Constituency constituency) {
        // Add validation so no duplicate constituency can be in the list
        if (!this.constituencyList.contains(constituency)) {
            this.constituencyList.add(constituency);
        }
    }

    /**
     * Retrieves a constituency by its ID.
     *
     * @param id the ID of the constituency
     * @return the Constituency with the specified ID or null if not found
     */
    public Constituency getConstituency(int id) {
        for (Constituency constituency : this.constituencyList) {
            if (constituency.getId() == id)
                return constituency;
        }
        return null;
    }

    /**
     * Retrieves a constituency by its name.
     *
     * @param name the name of the constituency to search for
     * @return the Constituency object matching the given name, or null if no match is found
     */
    public Constituency getConstituencyByName(String name) {
        if (name == null) return null;

        for (Constituency constituency : this.constituencyList) {
            if (constituency.getName().equalsIgnoreCase(name.trim())) {
                return constituency;
            }
        }
        return null;
    }

    // --- National Party Votes ---

    /**
     * Retrieves the list of national-level party votes.
     *
     * @return a list of PartyVote objects representing national votes
     */
    public List<PartyVote> getPartyVoteList() {
        return partyVoteList;
    }

    /**
     * Adds a PartyVote to the national list if it does not already exist.
     *
     * @param partyVote the PartyVote instance to add
     */
    public void addPartyVote(PartyVote partyVote) {
        if (!this.partyVoteList.contains(partyVote)) {
            this.partyVoteList.add(partyVote);
        }
    }

    /**
     * Retrieves a PartyVote by its party ID.
     *
     * @param partyId the ID of the party
     * @return the PartyVote with the given ID, or null if not found
     */
    public PartyVote getPartyVoteByPartyId(int partyId) {
        for (PartyVote vote : this.partyVoteList) {
            if (vote.getPartyId() == partyId) {
                return vote;
            }
        }
        return null;
    }


    // --- National Candidate Votes ---

    /**
     * Retrieves the list of national candidate votes.
     *
     * @return a list of CandidateNationalVote objects
     */
    public List<CandidateNationalVote> getNationalCandidateList() {
        return nationalCandidateList;
    }

    /**
     * Adds a national candidate vote to the list if it does not already exist.
     *
     * @param candidateVote the CandidateNationalVote to add
     */
    public void addNationalCandidateVote(CandidateNationalVote candidateVote) {
        if (!this.nationalCandidateList.contains(candidateVote)) {
            this.nationalCandidateList.add(candidateVote);
        }
    }

    /**
     * Retrieves a candidate by their short code.
     *
     * @param shortCode the unique short code identifying the candidate
     * @return the matching CandidateNationalVote, or null if not found
     */
    public CandidateNationalVote getNationalCandidateShortCode(String shortCode) {
        for (CandidateNationalVote candidateVote : this.nationalCandidateList) {
            if (candidateVote.getShortCode().equals(shortCode))
                return candidateVote;
        }
        return null;
    }

    /**
     * Returns the metadata associated with this object.
     *
     * @return the metadata
     */
    public Metadata getMetadata() {
        return metadata;
    }

    /**
     * Sets the metadata for this object.
     *
     * @param metadata the Metadata to associate with this object
     */
    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    /**
     * Returns a string representation of the State object, including its ID,
     * name, constituency list, and national party vote list.
     *
     * @return a string representation of this State
     */
    @Override
    public String toString() {
        return "State{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", constituencyList=" + constituencyList +
                '}';
    }
}
