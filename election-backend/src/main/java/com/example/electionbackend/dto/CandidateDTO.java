package com.example.electionbackend.dto;

import java.util.Objects;

/**
 * The candidate DTO class
 */
public class CandidateDTO {
    private int id;
    private String initials;
    private String firstName;
    private String lastName;
    private String gender;
    private String locality;
    private boolean elected;

    /**
     * Default no-argument constructor for the CandidateDTO class.
     */
    public CandidateDTO() {}

    /**
     * The constructor method which sets all the properties
     * @param id the id of the candidate
     * @param initials the initials of the candidate
     * @param firstName the firstname of the candidate
     * @param lastName the lastname of the candidate
     * @param gender the gender of the candidate
     * @param locality the locality of the candidate
     * @param elected the elected status of the candidate
     */
    public CandidateDTO(
            int id,
            String initials,
            String firstName,
            String lastName,
            String gender,
            String locality,
            boolean elected
    ) {
        this.id = id;
        this.initials = initials;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.locality = locality;
        this.elected = elected;
    }

    /**
     * Method to compare objects
     * @param o Object of the candidate DTO class
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CandidateDTO candidate = (CandidateDTO) o;
        return Objects.equals(id, candidate.id) &&
                Objects.equals(firstName, candidate.firstName);
    }

    /**
     * Returns the integer hash code value of the object
     * @return the hashed code off the class
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName);
    }

    // --- Getters and Setters ----

    /**
     * Retrieves the identifier of the candidate.
     * @return the id*/
    public int getId() {
        return id;
    }

    /**
     * Sets the identifier for the candidate.
     *
     * @param id the unique identifier of the candidate
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retrieves the initials of the candidate.
     *
     * @return the initials as a String
     */
    public String getInitials() {
        return initials;
    }

    /**
     * Sets the initials for the candidate.
     *
     * @param initials the initials of the candidate
     */
    public void setInitials(String initials) {
        this.initials = initials;
    }

    /**
     * Retrieves the first name of the candidate.
     *
     * @return the first name as a String
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name of the candidate.
     *
     * @param firstName the first name of the candidate
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Retrieves the last name of the candidate.
     *
     * @return the*/
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name of the candidate.
     *
     * @param lastName the last name of the candidate
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Retrieves the gender of the candidate.
     *
     * @return the gender as a String
     */
    public String getGender() {
        return gender;
    }

    /**
     * Sets the gender of the candidate.
     *
     * @param gender the gender of the candidate; if null, defaults to "unknown"
     */
    public void setGender(String gender) {
        this.gender = (gender == null) ? "unknown" : gender;
    }

    /**
     * Retrieves the locality of the candidate.
     *
     * @return the locality as a String
     */
    public String getLocality() {
        return locality;
    }

    /**
     * Sets the locality of the candidate.
     *
     * @param locality the locality of the candidate
     */
    public void setLocality(String locality) {
        this.locality = locality;
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
}


