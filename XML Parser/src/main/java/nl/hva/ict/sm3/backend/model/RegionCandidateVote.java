package nl.hva.ict.sm3.backend.model;

/**
 * Holds the number of votes a candidate got in a region (municipality or constituency),
 * along with the candidate's party.
 */
public class RegionCandidateVote {
    private int candidateId;
    private int partyId;  // <-- new
    private int votes;

    /**
     * Constructs a new RegionCandidateVote.
     *
     * @param candidateId the unique ID of the candidate
     * @param partyId     the ID of the party the candidate belongs to
     * @param votes       the number of votes the candidate received in the region
     */
    public RegionCandidateVote(int candidateId, int partyId, int votes) {
        this.candidateId = candidateId;
        this.partyId = partyId;
        this.votes = votes;
    }

    /**
     * Gets the candidate ID.
     *
     * @return the candidate ID
     */
    public int getCandidateId() {
        return candidateId;
    }

    /**
     * Sets the candidate ID.
     *
     * @param candidateId the candidate ID to set
     */
    public void setCandidateId(int candidateId) {
        this.candidateId = candidateId;
    }

    /**
     * Gets the party ID.
     *
     * @return the party ID
     */
    public int getPartyId() {
        return partyId;
    }

    /**
     * Sets the party ID.
     *
     * @param partyId the party ID to set
     */
    public void setPartyId(int partyId) {
        this.partyId = partyId;
    }

    /**
     * Gets the number of votes received by the candidate.
     *
     * @return the number of votes
     */
    public int getVotes() {
        return votes;
    }

    /**
     * Sets the number of votes.
     *
     * @param votes the vote count to set
     */
    public void setVotes(int votes) {
        this.votes = votes;
    }

    @Override
    public String toString() {
        return "RegionCandidateVote{" +
                "candidateId=" + candidateId +
                ", partyId=" + partyId +
                ", votes=" + votes +
                '}';
    }
}
