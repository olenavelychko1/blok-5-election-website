package com.example.electionbackend.dto;

/**
 * The party summary DTO class.
 */
public class PartySummaryDTO {

    private final int id;
    private final int pid;
    private final String name;

    /**
     * Constructor for the PartySummaryDTO class.
     *
     * @param id - the id of the party
     * @param pid - the id of the party
     * @param name - the name of the party
     */
    public PartySummaryDTO(int id, int pid, String name) {
        this.id = id;
        this.pid = pid;
        this.name = name;
    }

    /**
     * Gets the id of the party.
     *
     * @return the id of the party
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the id of the party.
     *
     * @return the id of the party
     */
    public int getPid() {
        return pid;
    }

    /**
     * Gets the name of the party.
     *
     * @return the name of the party
     */
    public String getName() {
        return name;
    }
}
