package nl.hva.ict.sm3.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Represents a geographical region with a unique number and name.
 * This class serves as a base model for more specific region-based entities such as {@link Constituency} and {@link }
 */
@JsonIgnoreProperties(value = {"regionNumber", "regionName"}, allowGetters = false, allowSetters = true)
public class Region {
    private int regionNumber;
    private String regionName;

    /**
     * The empty constructor method of the region class
     */
    public Region() {}

    /**
     * The constructor method of the region class, which sets the region's name
     * @param regionName the region name of the region class
     */
    public Region(String regionName) {
        this.regionName = regionName;
    }

    /**
     * The constructor method of the region class, which sets all properties
     * @param regionNumber the region number of the region
     * @param regionName the region name of the region class
     */
    public Region(int regionNumber, String regionName) {
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
}
