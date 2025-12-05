package com.example.electionbackend.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

/**
 * Represents a geographical region with a unique number and name.
 * This class serves as a base model for more specific region-based entities such as {@link Constituency} and {@link State}
 */
@MappedSuperclass
public abstract class Region {

    @Column(name = "name")
    private String name;

    /**
     * The empty constructor method of the region class
     */
    public Region() {}

    /**
     * The constructor method of the region class, which sets the region's name
     * @param name the name of the region class
     */
    public Region(String name) {
        this.name = name;
    }

    /**
     * Retrieves the region's name
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Updates the region's name
     *
     * @param name the new name to assign to the region
     */
    public void setName(String name) {
        this.name = name;
    }
}
