package nl.hva.ict.sm3.backend.utils.xml.transformers;

import nl.hva.ict.sm3.backend.model.Election;
import nl.hva.ict.sm3.backend.model.Party;
import nl.hva.ict.sm3.backend.model.Candidate;
import nl.hva.ict.sm3.backend.utils.xml.CandidateTransformer;

import java.util.Map;

/**
 * The DutchCandidateTransformer class
 */
public class DutchCandidateTransformer implements CandidateTransformer {
    private final Election election;

    /**
     * Creates a new transformer for handling the candidate lists. It expects an instance of Election that can
     * be used for storing the candidate lists.
     * @param election the election in which the candidate lists will be stored.
     */
    public DutchCandidateTransformer(Election election) {
        this.election = election;
    }

    /**
     * Registers the candidates to a party which is stored in the election.partyList
     * @param electionData the data of the election result
     */
    @Override
    public void registerCandidate(Map<String, String> electionData) {
        int partyId = Integer.parseInt(electionData.get("AffiliationIdentifier-Id"));
        Party newParty = new Party(partyId);
        newParty.setName(electionData.get("RegisteredName"));

        // Adds the party to the party list, the function already checks for duplicate entries
        election.addParty(newParty);

        // Create new candidate
        Candidate candidate = new Candidate(
                Integer.parseInt(electionData.get("CandidateIdentifier-Id")),
                electionData.get("NameLine"),
                electionData.get("FirstName"),
                electionData.get("LastName"),
                electionData.get("Gender"),
                electionData.get("LocalityName")
        );

        // Add a candidate to a party
        election.getParty(partyId).addCandidate(candidate);
    }
}
