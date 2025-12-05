package com.example.electionbackend.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

/**
 * This class holds metadata information for an election.
 */
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MetadataDTO {
    private int total_cast;
    private int total_counted;
    private int invalid;
    private int blank;

    /**
     * Default constructor for the MetadataDTO class.
     */
    public MetadataDTO() {}

    /**
     * Constructor for the MetadataDTO class.
     *
     * @param total_cast - the number of votes total_cast
     * @param total_counted - the total number of counted votes
     * @param invalid - the number of invalid votes
     * @param blank - the number of blank votes
     */
    public MetadataDTO(int total_cast, int total_counted, int invalid, int blank) {
        this.total_cast = total_cast;
        this.total_counted = total_counted;
        this.invalid = invalid;
        this.blank = blank;
    }

    /**
     * Get the number of votes total_cast
     *
     * @return - the number of votes total_cast
     */
    public int getTotalCast() {
        return total_cast;
    }

    /**
     * Set the number of votes total_cast
     *
     * @param total_cast - the number of votes total_cast
     */
    public void setTotalCast(int total_cast) {
        this.total_cast = total_cast;
    }

    /**
     * Get the total number of counted votes
     *
     * @return - the total number of counted votes
     */
    public int getTotalCounted() {
        return total_counted;
    }

    /**
     * Set the total number of counted votes
     *
     * @param total_counted - the total number of counted votes
     */
    public void setTotalCounted(int total_counted) {
        this.total_counted = total_counted;
    }

    /**
     * Get the map of rejected votes with reasons and their counts
     *
     * @return - the map of rejected votes
     */
    public int getInvalid() {
        return invalid;
    }

    /**
     * Set the map of rejected votes with reasons and their counts
     *
     * @param invalid - the map of rejected votes
     */
    public void setInvalid(int invalid) {
        this.invalid = invalid;
    }

    /**
     * Get the map of uncounted votes with reasons and their counts
     *
     * @return - the map of uncounted votes
     */
    public int getBlank() {
        return blank;
    }

    /**
     * Set the map of uncounted votes with reasons and their counts
     *
     * @param blank - the map of uncounted votes
     */
    public void setBlank(int blank) {
        this.blank = blank;
    }
}
