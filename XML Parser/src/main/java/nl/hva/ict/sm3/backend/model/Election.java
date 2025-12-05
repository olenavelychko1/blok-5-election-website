package nl.hva.ict.sm3.backend.model;

import java.util.ArrayList;
import java.util.List;

/**
 * The election class
 */
public class Election {
    private final String id;
    private State state;
    private final List<Party> partyList = new ArrayList<>();

    /**
     * Constructor method of the election class
     *
     * @param id The id of the election entity
     */
    public Election(String id) {
        this.id = id;
    }

    /**
     * Gets the id of the election
     *
     * @return string id
     */
    public String getId() {
        return id;
    }

    // --- Party ---

    /**
     * Get a list of parties
     *
     * @return parties return a list of parties
     */
    public List<Party> getPartyList() {
        return partyList;
    }

    /**
     * Gets a single party
     *
     * @param id The id of the party entity
     * @return int id
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
     *
     * @param partyList a list of parties
     */
    public void setPartyList(List<Party> partyList) {
        // Loops through addParty so no duplicate party can be in the list
        this.partyList.addAll(partyList);
    }

    /**
     * Add a single party to the party list
     *
     * @param party a party entity
     */
    public void addParty(Party party) {
        // Add validation so no duplicate party can be in the list
        if (!partyList.contains(party)) {
            partyList.add(party);
        }
    }

    // --- State ---

    /**
     * Sets the state to the election
     * @param state a state entity
     */
    public void setState(State state) {
        this.state = state;
    }

    /**
     * Retrieves the state of the election
     * @return the state of the election
     */
    public State getState() {
        return state;
    }

    // --- Municipality ---
    public Municipality getMunicipality(int municipalityId) {
        List<Constituency> constituencyList = this.getState().getConstituencyList();

        for  (Constituency constituency : constituencyList) {
            List<Municipality> municipalityList = constituency.getMunicipalityList();
            for (Municipality municipality : municipalityList) {
                if (municipality.getId() == municipalityId) {
                    return municipality;
                }
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "Election{" +
                "id='" + id + '\'' +
                ", state=" + state +
                ", partyList=" + partyList +
                '}';
    }

}
