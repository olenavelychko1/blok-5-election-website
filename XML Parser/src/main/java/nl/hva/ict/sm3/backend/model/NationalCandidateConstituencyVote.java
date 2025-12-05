package nl.hva.ict.sm3.backend.model;

/**
 * Represents the number of votes a candidate received in a specific constituency.
 */
public class NationalCandidateConstituencyVote {
    private String constituencyName;
    private int votes;

    /**
     * Constructs a new constituency vote for a candidate.
     *
     * @param constituencyName the name of the constituency
     * @param votes the number of valid votes received
     */
    public NationalCandidateConstituencyVote(String constituencyName, int votes) {
        this.constituencyName = constituencyName;
        this.votes = votes;
    }

    /**
     * Gets the name of the constituency.
     *
     * @return the constituency name
     */
    public String getConstituencyName() {
        return constituencyName;
    }

    /**
     * Sets the name of the constituency.
     *
     * @param constituencyName the constituency name to set
     */
    public void setConstituencyName(String constituencyName) {
        this.constituencyName = constituencyName;
    }

    /**
     * Gets the number of valid votes received.
     *
     * @return the number of votes
     */
    public int getVotes() {
        return votes;
    }

    /**
     * Sets the number of valid votes received.
     *
     * @param votes the number of votes to set
     */
    public void setVotes(int votes) {
        this.votes = votes;
    }

    @Override
    public String toString() {
        return "NationalCandidateConstituencyVote{" +
                "constituencyName='" + constituencyName + '\'' +
                ", votes=" + votes +
                '}';
    }
}
