package nl.hva.ict.sm3.backend.utils.xml.transformers;

import nl.hva.ict.sm3.backend.model.Candidate;
import nl.hva.ict.sm3.backend.model.Election;
import nl.hva.ict.sm3.backend.model.Party;
import nl.hva.ict.sm3.backend.utils.xml.TagAndAttributeNames;
import nl.hva.ict.sm3.backend.utils.xml.VotesTransformer;

import java.util.Map;

/**
 * Just prints to content of electionData to the standard output.>br/>
 * <b>This class needs heavy modification!</b>
 */
public class DutchResultTransformer implements VotesTransformer, TagAndAttributeNames {
    private final Election election;

    private Party currentParty; // the current party being processed

    /**
     * Creates a new transformer for handling the votes at the results. It expects an instance of
     * Election that can be used for storing the results. The results contain either which party has candidates who
     * have been elected or the candidates who are elected.
     *
     * @param election the election in which the votes wil be stored.
     */
    public DutchResultTransformer(Election election) {
        this.election = election;
    }

    @Override
    public void registerPartyVotes(boolean aggregated, Map<String, String> electionData) {
        // Example: <AffiliationIdentifier Id="19"><RegisteredName>NSC</RegisteredName></AffiliationIdentifier>

        int partyId = Integer.parseInt(electionData.get(AFFILIATION_IDENTIFIER_ID));

        Party party = election.getParty(partyId);
        if (party == null) {
            System.out.printf("[WARNING] Result lists party %d but it was not found in candidates list!\n", partyId);
            return;
        }

        this.currentParty = party; // Set the current party

        System.out.printf("[RESULT] Party elected seats for '%s' (ID %d)\n", party.getName(), partyId);

    }

    @Override
    public void registerCandidateVotes(boolean aggregated, Map<String, String> electionData) {
        // This is called for candidate <Selection> like:
        // <Candidate><CandidateIdentifier Id="1"/></Candidate>
        // <Elected>yes</Elected>

        if (currentParty == null) {
            System.out.println("[ERROR] Candidate encountered but no current party is set!");
            return;
        }

        int candidateId = Integer.parseInt(electionData.get(CANDIDATE_IDENTIFIER_ID));

        // figure out if the candidate was elected
        String electedValue = electionData.getOrDefault(ELECTED, "no");
        boolean isElected = electedValue.equalsIgnoreCase("yes");

        if (!isElected) return;

        // Find candidate inside currentParty
        assert currentParty != null;
        Candidate candidate = currentParty.getCandidates()
                .stream()
                .filter(c -> c.getId() == candidateId)
                .findFirst()
                .orElse(null);

        if (candidate == null) {
            System.out.printf(
                    "[WARNING] Candidate %d not found in party '%s'\n",
                    candidateId, currentParty.getName()
            );
            return;
        }

        // Mark elected
        candidate.setElected(isElected);
        currentParty.addSeat();

        System.out.printf(
                "[RESULT] Candidate %s %s (ID %d) from party %s → ELECTED\n",
                candidate.getFirstName(),
                candidate.getLastName(),
                candidateId,
                currentParty.getName()
        );
    }

    @Override
    public void registerMetadata(boolean aggregated, Map<String, String> electionData) {
//        throw new IllegalStateException("There is no implementation on purpose.");
    }
}
