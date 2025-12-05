package nl.hva.ict.sm3.backend.model;

import java.util.Objects;

/**
 * The candidate class
 */
public class Candidate {
    private int id;
    private String initials;
    private String firstName;
    private String lastName;
    private String gender;
    private String locality;
    private boolean elected = false;

    /**
     * The constructor method which sets all the properties
     * @param id the id of the candidate
     * @param initials the initials of the candidate
     * @param firstName the firstname of the candidate
     * @param lastName the lastname of the candidate
     * @param gender the gender of the candidate
     * @param locality the locality of the candidate
     */
    public Candidate(
            int id,
            String initials,
            String firstName,
            String lastName,
            String gender,
            String locality
    ) {
        this.id = id;
        this.initials = initials;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.locality = locality;
    }

    /**
     * Method to compare objects
     * @param o Object of the candidate class
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Candidate candidate = (Candidate) o;
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
     * Gets the id of candidate
     * @return the id of the candidate entity
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the id of the candidate
     * @param id int
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the initials of the candidate
     * @return string initials
     */
    public String getInitials() {
        return initials;
    }

    /**
     * Sets the initials of the candidate
     * @param initials the initials of the candidate
     */
    public void setInitials(String initials) {
        this.initials = initials;
    }

    /**
     * Gets the first name of the candidate
     * @return string firstname
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name of the candidate
     * @param firstName the firstname of the candidate entity
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Get the last name of the candidate
     * @return string lastname
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name of the candidate
     * @param lastName the lastname of the candidate entity
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets the gender of the candidate
     * The gender can be unknown
     * @return string gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * Sets the gender of the candidate
     * @param gender the gender of the candidate entity
     */
    public void setGender(String gender) {
        this.gender = (gender == null) ? "unknown" : gender;
    }

    /**
     * Gets the locality of the candidate
     * @return string locality
     */
    public String getLocality() {
        return locality;
    }

    /**
     * Sets the locality of the candidate
     * @param locality the locality of the candidate entity
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