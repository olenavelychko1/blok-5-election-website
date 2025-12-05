package nl.hva.ict.sm3.backend.model;

import java.util.Objects;

/**
 * This class holds the votes a candidate received at a specific polling station.
 */
public class PollingStationCandidateVote {
    private int candidateId;
    private int partyId;
    private int votes;

    /**
     * Constructor for PollingStationCandidateVote
     *
     * @param candidateId      - the id of the candidate
     * @param votes            - the number of votes received
     */
    public PollingStationCandidateVote(int candidateId, int votes) {
        this.candidateId = candidateId;
        this.votes = votes;
    }

    /**
     * Method to compare objects of the PollingStationCandidateVote class
     * @param o   the reference object with which to compare.
     * @return - boolean indicating whether the objects are equal.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PollingStationCandidateVote pscv = (PollingStationCandidateVote) o;

        if (!Objects.equals(candidateId, pscv.candidateId)) return false;
        return partyId == pscv.partyId;
    }

    /**
     * Generates a hash code for this PollingStationCandidateVote.
     * @return int representing the hash code.
     */
    @Override
    public int hashCode() {
        return Objects.hash(candidateId, partyId);
    }

    /* --- Getters and Setters --- */

    /**
     * Get the candidate id
     *
     * @return - the id of the candidate
     */
    public int getCandidateId() {
        return candidateId;
    }

    /**
     * Set the candidate id
     *
     * @param candidateId - the id of the candidate
     */
    public void setCandidateId(int candidateId) {
        this.candidateId = candidateId;
    }

    /**
     * Get the party id
     *
     * @return - the id of the party
     */
    public int getPartyId() {
        return partyId;
    }

    /**
     * Sets the party id
     *
     * @param partyId - the id of the party
     */
    public void setPartyId(int partyId) {
        this.partyId = partyId;
    }

    /**
     * Get the number of votes
     *
     * @return - the number of votes
     */
    public int getVotes() {
        return votes;
    }

    /**
     * Set the number of votes
     *
     * @param votes - the number of votes
     */
    public void setVotes(int votes) {
        this.votes = votes;
    }

    /**
     * String representation of PollingStationCandidateVotes
     *
     * @return - string representation of PollingStationCandidateVotes
     */
    @Override
    public String toString() {
        return "PollingStationCandidateVotes {\n" +
                "    candidateId: " + candidateId + "\n" +
                "    partyId: " + partyId + "\n" +
                "    votes: " + votes + "\n" +
                "}";
    }
}
