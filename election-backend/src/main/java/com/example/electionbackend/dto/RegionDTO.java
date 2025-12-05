package com.example.electionbackend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.MappedSuperclass;

/**
 * This class represents a region DTO with its associated data.
 */
@JsonIgnoreProperties(value = {"regionNumber", "regionName"}, allowGetters = false, allowSetters = true)
@MappedSuperclass
public class RegionDTO {
    private int regionNumber;
    private String regionName;

    /**
     * The empty constructor method of the region DTO class
     */
    public RegionDTO() {}

    /**
     * The constructor method of the region DTO class, which sets the region's name
     * @param regionName the region name of the DTO
     */
    public RegionDTO(String regionName) {
        this.regionName = regionName;
    }

    /**
     * The constructor method of the region DTO class, which sets all properties
     * @param regionNumber the region number of the DTO
     * @param regionName the region name of the DTO
     */
    public RegionDTO(int regionNumber, String regionName) {
        this.regionNumber = regionNumber;
        this.regionName = regionName;
    }

    /**
     * Retrieves the region's unique number.
     * @return the region number
     */
    public int getRegionNumber() {
        return regionNumber;
    }

    /**
     * Updates the region's unique number.
     * @param regionNumber the new region number to assign
     */
    public void setRegionNumber(int regionNumber) {
        this.regionNumber = regionNumber;
    }

    /**
     * Retrieves the region's name
     * @return the region name
     */
    public String getRegionName() {
        return regionName;
    }

    /**
     * Updates the region's name
     * @param regionName the new name to assign to the region
     */
    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    @JsonProperty("id")
    public int getId() {
        return regionNumber;
    }

    @JsonProperty("id")
    public void setId(int id) {
        this.regionNumber = id;
    }

    @JsonProperty("name")
    public String getName() {
        return regionName;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.regionName = name;
    }
}
