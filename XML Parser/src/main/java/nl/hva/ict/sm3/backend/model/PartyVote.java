package nl.hva.ict.sm3.backend.model;

import java.util.Objects;

public class PartyVote {
    private int partyId;
    private int votes;

    /**
     * Parameterized constructor for PartyVote.
     *
     * @param votes - the number of votes received
     */
    public PartyVote(int partyId, int votes) {
        this.partyId = partyId;
        this.votes = votes;
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     *
     * @param o the reference object with which to compare.
     * @return - true if this object is the same as the obj argument; false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PartyVote partyVote = (PartyVote) o;
        return partyId == partyVote.partyId && votes == partyVote.votes;
    }

    /**
     * Returns a hash code value for the object.
     *
     * @return - a hash code value for this object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(partyId, votes);
    }

    // --- Getters and Setters ---

    /**
     * Gets the number of votes.
     *
     * @return - the number of votes.
     */
    public int getVotes() {
        return votes;
    }

    /**
     * Sets the number of votes.
     *
     * @param votes - the number of votes to set.
     */
    public void setVotes(int votes) {
        this.votes = votes;
    }

    /**
     * Gets the party ID.
     *
     * @return - the party ID.
     */
    public int getPartyId() {
        return partyId;
    }

    /**
     * Sets the party ID.
     *
     * @param partyId - the party ID to set.
     */
    public void setPartyId(int partyId) {
        this.partyId = partyId;
    }

    @Override
    public String toString() {
        return "Vote{\n" +
                "    partyId: '" + partyId + "'\n" +
                "    vote: '" + votes + "'\n" +
                "}";
    }
}