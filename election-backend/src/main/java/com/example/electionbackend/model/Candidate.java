package com.example.electionbackend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.Objects;

/**
 * Represents a candidate entity in an election system.
 * Contains information such as the candidate's ID, initials, first name,
 * last name, gender, locality, and the associated political party.
 * This class is annotated to be managed as a JPA entity for persistence.
 */
@Entity
public class Candidate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // Candidate id
    private int cid;
    private String initials;
    private String firstName;
    private String lastName;
    private String gender;
    private String locality;
    private boolean elected;

    @ManyToOne
    @JoinColumn(name = "party_id", referencedColumnName = "id")
    @JsonBackReference
    private Party party;

    /**
     * Default constructor for Candidate.
     */
    public Candidate() {
        // The default constructor method of the Candidate class
    }

    /**
     * Constructs a new Candidate object with the specified values.
     * @param id the id of the candidate
     * @param initials the initials of the candidate
     * @param firstName the firstname of the candidate
     * @param lastName the lastname of the candidate
     * @param gender the gender of the candidate
     * @param locality the locality of the candidate
     * @param elected the elected status of the candidate
     */
    public Candidate(int id, String initials, String firstName, String lastName, String gender, String locality, boolean elected) {
        this.cid = id;
        this.initials = initials;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.locality = locality;
        this.elected = elected;
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
        Candidate candidate = (Candidate) o;
        return Objects.equals(this.getId(), candidate.getId()) &&
                Objects.equals(this.getParty().getId(), candidate.getParty().getId());
    }

    /**
     * Retrieves the candidate's unique identifier.
     *
     * @return the candidate ID as an integer
     */
    public int getId() {
        return cid;
    }

    /**
     * Sets the candidate's unique identifier.
     *
     * @param id the candidate ID to be set as an integer
     */
    public void setId(int id) {
        this.cid = id;
    }

    /**
     * Retrieves the initials of the candidate.
     *
     * @return the initials of the candidate as a string
     */
    public String getInitials() {
        return this.initials;
    }

    /**
     * Sets the initials of the candidate.
     * If the given initials are not null, leading and trailing whitespace
     * is removed from them. Otherwise, the initials will be set to null.
     *
     * @param initials the initials of the candidate as a string
     */
    public void setInitials(String initials) {
        this.initials = initials != null ? initials.trim() : null;
    }

    /**
     * Retrieves the first name of the candidate.
     *
     * @return the first name of the candidate as a string
     */
    public String getFirstName() {
        return this.firstName;
    }

    /**
     * Sets the first name of the candidate.
     * If the provided value is not null, leading and trailing whitespace
     * will be removed. Otherwise, it will set the value to null.
     *
     * @param firstName the first name of the candidate as a string
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName != null ? firstName.trim() : null;
    }

    /**
     * Retrieves the last name of the candidate.
     *
     * @return the last name of the candidate as a string
     */
    public String getLastName() {
        return this.lastName;
    }

    /**
     * Sets the last name of the candidate.
     * If the provided value is not null, leading and trailing whitespace
     * will be removed. Otherwise, it will set the value to null.
     *
     * @param lastName the last name of the candidate as a string
     */
    public void setLastName(String lastName) {
        this.lastName = lastName != null ? lastName.trim() : null;
    }

    /**
     * Retrieves the gender of the candidate.
     *
     * @return the gender of the candidate
     */
    public String getGender() {
        return this.gender;
    }

    /**
     * Sets the gender of the candidate.
     * The gender value will be trimmed of leading and trailing whitespace
     * and converted to uppercase. If the provided value is null, the gender
     * will be set to null.
     *
     * @param gender the gender of the candidate as a string
     */
    public void setGender(String gender) {
        this.gender = gender != null ? gender.trim().toUpperCase() : null;
    }

    /**
     * Retrieves the locality of the candidate.
     *
     * @return the locality of the candidate as a string
     */
    public String getLocality() {
        return this.locality;
    }

    /**
     * Sets the locality of the candidate.
     * If it wasn't parsed as null, leading and trailing whitespace will be removed.
     *
     * @param locality the locality of the candidate as a string
     */
    public void setLocality(String locality) {
        this.locality = locality != null ? locality.trim() : null;
    }

    /**
     * Gets the elected status of the candidate
     * @return boolean elected
     */
    public boolean isElected() {
        return elected;
    }

    /**
     * Sets the elected status of the candidate
     * @param elected boolean
     */
    public void setElected(boolean elected) {
        this.elected = elected;
    }

    /**
     * Retrieves the political party associated with the candidate.
     *
     * @return the associated
     */
    public Party getParty() {
        return party;
    }

    /**
     * Sets the political party associated with the candidate.
     *
     * @param party the political party to be assigned to the candidate
     */
    public void setParty(Party party) {
        this.party = party;
    }
}
