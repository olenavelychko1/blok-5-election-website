package nl.hva.ict.sm3.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.*;

/**
 * Represents a specific constituency which inherits properties from the Region class.
 */
public class Constituency extends Region {
    private final int id = super.getRegionNumber();
    private final String name = super.getRegionName();

    /**
     * Metadata associated with this constituency.
     */

    private Metadata metadata;

    /**
     * List of party votes in this constituency.
     */
    private List<PartyVote> partyVoteList = new ArrayList<>();

    @JsonIgnore // temporary for testing
    private final List<RegionCandidateVote> candidateVotes = new ArrayList<>();
    private final List<Municipality>  municipalityList = new ArrayList<>();

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
     * @param id   the constituency's unique identifier (mapped to regionNumber)
     * @param name the constituency's name (mapped to regionName)
     */
    public Constituency(int id, String name) {
        super(id, name);
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
     * Gets the constituency ID.
     * @return the constituency ID
     */
    public int getId() {
        return super.getRegionNumber();
    }

    /**
     * Sets the constituency ID.
     * @param id the new constituency ID
     */
    public void setId(int id) {
        super.setRegionNumber(id);
    }

    /**
     * Gets the constituency name.
     * @return the constituency name
     */
    public String getName() {
        return super.getRegionName();
    }

    /**
     * Sets the constituency name.
     * @param name the new constituency name
     */
    public void setName(String name) {
        super.setRegionName(name);
    }

    // --- Municipality ---

    /**
     * Retrieves a municipality by its ID by O(1) lookup
     * @param id the ID of the municipality
     * @return a Municipality class
     */
    public Municipality getMunicipalityById(int id) {
        for (Municipality m : municipalityList) {
            if (m.getId() == id) {
                return m;
            }
        }
        return null;
    }

    /**
     * Retrieves a list of municipalities
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
        }
    }

    // --- Metadata ---

    /**
     * Gets the election metadata for the constituency
     *
     * @return ElectionMetadata - the election metadata
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
    }


    // --- PartyVote ---

    /**
     * Gets the list of party votes in the constituency
     * @return - list of ConstituencyPartyVote objects
     */
    public List<PartyVote> getPartyVoteList() {
        return partyVoteList;
    }

    /**
     * Sets the list of party votes in the constituency
     * @param partyVotesList - list of ConstituencyPartyVote objects to set
     */
    public void setPartyVoteList(List<PartyVote> partyVotesList) {
        this.partyVoteList = partyVotesList;
    }

    /**
     * Adds a ConstituencyPartyVote to the list if it doesn't already exist
     *
     * @param partyVote - ConstituencyPartyVote object to add
     */
    public void addPartyVote(PartyVote partyVote) {
        if (!partyVoteList.contains(partyVote)) {
            partyVoteList.add(partyVote);
        }
    }

    /**
     * Retrieves all candidate vote records associated with this constituency.
     * <p>
     * Each {@link RegionCandidateVote} contains a candidate ID, their party ID,
     * and the number of votes received in this region.
     *
     * @return a list of {@link RegionCandidateVote} objects
     */
    public List<RegionCandidateVote> getCandidateVotes() {
        return candidateVotes;
    }

    /**
     * Adds a candidate vote record to the constituency if it doesn't already exist.
     *
     * @param vote the {@link RegionCandidateVote} to add
     */
    public void addCandidateVote(RegionCandidateVote vote) {
        if (!candidateVotes.contains(vote)) {
            candidateVotes.add(vote);
        }
    }

    /**
     * Returns a string representation of the Constituency object
     *
     * @return - string representation of the Constituency
     */
    @Override
    public String toString() {
        return "Constituency {\n" +
                "    id: " + getRegionNumber() + "\n" +
                "    name: '" + getRegionName() + "'\n" +
                "    municipalities: " + municipalityList + "\n" +
                "}";
    }
}
