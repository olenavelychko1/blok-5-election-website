package nl.hva.ict.sm3.backend.model;

import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

/**
 * The party class
 */
public class Party {
    private int id;
    private String name;
    private final List<Candidate> candidateList = new ArrayList<>();
    private int seats = 0; // number of seats in the party

    /**
     * The constructor method of the party class
     *
     * @param id The id of the party entity
     */
    public Party(int id) {
        this.id = id;
    }

    /**
     * Method to compare objects
     *
     * @param o Object of the party class
     * @return boolean if the object equals the party entity
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Party party = (Party) o;
        return Objects.equals(id, party.id) &&
                Objects.equals(name, party.name);
    }

    /**
     * Returns the integer hash code value of the object
     *
     * @return int The hashed code of the object
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    // --- Getters and setters ---

    /**
     * Returns the id of the party
     *
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the id of the party
     *
     * @param id The id of the party
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Return the name of the party
     *
     * @return string The name of the party entity
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the party
     *
     * @param name Sets the name of the party entity
     */
    public void setName(String name) {
        this.name = name;
    }

    // --- Candidate ---

    /**
     * Gets a list of the candidates
     *
     * @return List<Candidate> A list of candidates related to the party entity
     */
    public List<Candidate> getCandidates() {
        return candidateList;
    }

    /**
     * Sets a list of candidates
     *
     * @param candidateList A list of candidates
     */
    public void setCandidates(List<Candidate> candidateList) {
        // Loops through addCandidate so no duplicate candidate can be in the list
        this.candidateList.addAll(candidateList);
    }

    /**
     * Adds a candidate to the related party
     *
     * @param candidate An candidate entity
     */
    public void addCandidate(Candidate candidate) {
        // Add validation so no duplicate candidate can be in the list
        if (!candidateList.contains(candidate)) {
            this.candidateList.add(candidate);
        }
    }

    /**
     * Gets the number of seats in the party
     * @return int - The number of seats
     */
    public int getSeats() {
        return seats;
    }

    /**
     * Increases the number of seats by one
     * Called when a seat is parsed in DutchResultTransformer for each candidate
     */
    public void addSeat() {
        this.seats++;
    }
}