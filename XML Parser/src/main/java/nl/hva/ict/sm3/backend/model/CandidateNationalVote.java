package nl.hva.ict.sm3.backend.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The candidate class
 */
public class CandidateNationalVote {
    private String shortCode;
    private int votes;
    private final List<NationalCandidateConstituencyVote> votesPerConstituency = new ArrayList<>();

    /**
     * The constructor
     */
    public CandidateNationalVote(String shortCode) {
        this.shortCode = shortCode;
    }

    /**
     * Gets the short code of the candidate.
     *
     * @return the candidate's short code
     */
    public String getShortCode() {
        return shortCode;
    }

    /**
     * Sets the short code of the candidate.
     *
     * @param shortCode the candidate's short code
     */
    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }

    /**
     * Gets the total number of votes received by the candidate.
     *
     * @return the total votes
     */
    public int getVotes() {
        return votes;
    }

    /**
     * Sets the total number of votes received by the candidate.
     *
     * @param votes the total votes
     */
    public void setVotes(int votes) {
        this.votes = votes;
    }

    /**
     * Gets the map of votes received per constituency.
     * The map key represents the constituency name or ID, and the value represents the vote count.
     *
     * @return a list of votes per constituency
     */
    public List<NationalCandidateConstituencyVote> getVotesPerConstituency() {
        return votesPerConstituency;
    }

    /**
     * Adds a constituency vote to the candidate's list.
     *
     * @param vote the constituency vote to add
     */
    public void addConstituencyVote(NationalCandidateConstituencyVote vote) {
        votesPerConstituency.add(vote);
    }

    /**
     * Returns a string representation of the candidate,
     *
     * @return a string describing the candidate
     */
    @Override
    public String toString() {
        return "Candidate{" +
                "shortCode='" + shortCode + '\'' +
                ", votes=" + votes +
                ", votesPerConstituency=" + votesPerConstituency +
                '}';
    }
}
