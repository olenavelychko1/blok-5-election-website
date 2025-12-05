package com.example.electionbackend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "party")
public class Party {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int pid;
    private String name;

    private int seats;

    @OneToMany(mappedBy = "party", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Candidate> candidates = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "election_id")
    @JsonBackReference
    private Election election;

    /**
     * Constructor method of the Party class
     */
    public Party() {
        // Default constructor for JPA
    }

    /**
     * Constructor method which sets the Party class
     *
     * @param id The id of the party
     * @param name The name of the party
     * @param seats number of seats in the party
     */
    public Party(int id, String name, int seats) {
        pid = id;
        this.name = name;
        this.seats = seats;
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
        return Objects.equals(this.getId(), party.getId()) &&
                Objects.equals(this.getName(), party.getName());
    }

    /**
     * Gets the id of the Party
     *
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the id of the Party
     *
     * @param id That will be assigned to the Party
     */
    public void setPid(int id) {
        this.pid = id;
    }

    /**
     * Gets the id of the Party
     *
     * @return id
     */
    public int getPid() {
        return pid;
    }

    /**
     * Sets the id of the Party
     *
     * @param id That will be assigned to the Party
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the name of the Party
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the Party
     *
     * @param name That will be assigned to the Party
     */
    public void setName(String name) {
        this.name = name;
    }

    // --- Relations ---

    /**
     * Gets the list of candidates
     *
     * @return the candidate list
     */
    public List<Candidate> getCandidates() {
        return candidates;
    }

    /**
     * Adds a candidate to the candidate list
     *
     * @param candidate Candidate
     */
    public void addCandidate(Candidate candidate) {
        for (Candidate c : this.candidates) {
            if (!c.equals(candidate)) {
                this.candidates.add(candidate);
                candidate.setParty(this);
            }
        }
    }

    public void setCandidates(List<Candidate> candidates) {
        candidates.forEach(c -> c.setParty(this));
        this.candidates = candidates;
    }

    /**
     * Gets the number of seats in the party
     *
     * @return int - The number of seats
     */
    public int getSeats() {
        return seats;
    }

    /**
     * Sets the number of seats in the party
     *
     * @param seats the number of seats in the party
     */
    public void setSeats(int seats) {
        this.seats = seats;
    }

    /**
     * Gets the related election
     *
     * @return Election
     */
    public Election getElection() {
        return election;
    }

    /**
     * Sets the election
     *
     * @param election Election
     */
    public void setElection(Election election) {
        this.election = election;
    }
}
