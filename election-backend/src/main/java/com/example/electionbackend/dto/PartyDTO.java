package com.example.electionbackend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The party DTO class
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PartyDTO {
    private int id;
    private String name;
    private int seats;
    private List<CandidateDTO> candidateList = new ArrayList<>();

    public PartyDTO() {}

    public PartyDTO(int id) {}

    /**
     * The constructor method of the party DTO class
     * @param id The id of the party entity
     */
    public PartyDTO(int id, String name, List<CandidateDTO> candidateList, int seats) {
        this.id = id;
        this.name = name;
        this.candidateList = candidateList;
        this.seats = seats;
    }

    /**
     * Method to compare objects
     *
     * @param o Object of the party DTO class
     * @return boolean if the object equals the party entity
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PartyDTO party = (PartyDTO) o;
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
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the id of the party
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

    /**
     * Gets the number of seats in the party
     * @return int - The number of seats
     */
    public int getSeats() {
        return seats;
    }

    /**
     * Sets the number of seats in the party
     * @param seats the number of seats in the party
     */
    public void setSeats(int seats) {
        this.seats = seats;
    }

    // --- Candidate ---

    /**
     * Gets a list of the candidates
     *
     * @return List<CandidateDTO> A list of candidates related to the party entity
     */
    public List<CandidateDTO> getCandidates() {
        return candidateList;
    }

    /**
     * Sets a list of candidates
     *
     * @param candidateList A list of candidates
     */
    public void setCandidates(List<CandidateDTO> candidateList) {
        this.candidateList.addAll(candidateList);
    }

    /**
     * Adds a candidate to the related party
     *
     * @param candidate An candidate entity
     */
    public void addCandidate(CandidateDTO candidate) {
        if (!candidateList.contains(candidate)) {
            this.candidateList.add(candidate);
        }
    }
}

