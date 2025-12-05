package com.example.electionbackend.model;

import com.example.electionbackend.type.RegionType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.persistence.*;

import java.util.Objects;

/**
 * This class holds metadata information for a region.
 */
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "metadata")
public class Metadata {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "region_type")
    @Enumerated(EnumType.STRING)
    private RegionType regionType;

    @Column(name = "region_id")
    private String regionId;

    private int total_cast;
    private int total_counted;
    private int invalid;
    private int blank;

    /**
     * Default constructor for JPA
     */
    public Metadata() {
        // Default constructor for JPA
    }

    /**
     * Constructs a Metadata object with the specified values.
     *
     * @param total_cast the total number of votes cast
     * @param total_counted the total number of votes counted
     * @param invalid the number of invalid votes
     * @param blank the number of blank votes
     */
    public Metadata(int total_cast, int total_counted, int invalid, int blank) {
        this.total_cast = total_cast;
        this.total_counted = total_counted;
        this.invalid = invalid;
        this.blank = blank;
    }

    /**
     * Compares this object with the specified object for equality.
     * Two Metadata objects are considered equal if their id, regionType, and regionId fields are equal.
     *
     * @param o the object to be compared for equality with this Metadata object
     * @return true if the specified object is equal to this Metadata object
     * */
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Metadata metadata = (Metadata) o;
        return Objects.equals(this.getId(), metadata.getId()) &&
                Objects.equals(this.getRegionType(), metadata.getRegionType()) &&
                Objects.equals(this.getRegionId(), metadata.getRegionId());
    }

    /**
     * Generates a hash code value for the Metadata object. The hash code
     * is computed using the fields id, regionType, regionId, total_cast,
     * total_counted, invalid, and blank.
     *
     * @return an integer hash code value for this Metadata object
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, regionType, regionId, total_cast, total_counted, invalid, blank);
    }

    /* --- Getters and Setters --- */

    /**
     * Get the number of votes total_cast
     *
     * @return - the number of votes total_cast
     */
    public int getCast() {
        return total_cast;
    }

    /**
     * Set the number of votes total_cast
     *
     * @param total_cast - the number of votes total_cast
     */
    public void setCast(int total_cast) {
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
     * Get the number of invalid votes
     *
     * @return - the number of invalid votes
     */
    public int getInvalid() {
        return invalid;
    }

    /**
     * Set the number of invalid votes
     *
     * @param invalid - the number of invalid votes
     */
    public void setInvalid(int invalid) {
        this.invalid = invalid;
    }

    /**
     * Get the number of blank votes
     *
     * @return - the number of blank votes
     */
    public int getBlank() {
        return blank;
    }

    /**
     * Set the number of blank votes
     *
     * @param blank - the number of blank votes
     */
    public void setBlank(int blank) {
        this.blank = blank;
    }

    /**
     * Get the id of the metadata object
     *
     * @return - the id of the metadata object
     */
    public int getId() {
        return id;
    }

    /**
     * Set the id of the metadata object
     *
     * @param id - the id of the metadata object
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Get the region type
     * @return - the region type
     */
    public RegionType getRegionType() {
        return regionType;
    }

    /**
     * Set the region type
     *
     * @param regionType - the region type
     */
    public void setRegionType(RegionType regionType) {
        this.regionType = regionType;
    }

    /**
     * Get the region id
     *
     * @return - the region id
     */
    public String getRegionId() {
        return regionId;
    }

    /**
     * Set the region id
     *
     * @param regionId - the region id
     */
    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }
}
