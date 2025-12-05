package nl.hva.ict.sm3.backend.model;

/**
 * Data Transfer Object (DTO) that associates a political party’s vote results with a specific region.
 * <p>
 * Used to provide structured election data that links the region name
 * to the corresponding {@link PartyVote} information.
 */
public class RegionPartyVoteDTO {
    private final String regionName;
    private final PartyVote partyVote;

    /**
     * Constructs a new RegionPartyVoteDTO.
     *
     * @param regionName the name of the region (e.g., municipality or constituency)
     * @param partyVote  the party vote data for this region
     */
    public RegionPartyVoteDTO(String regionName, PartyVote partyVote) {
        this.regionName = regionName;
        this.partyVote = partyVote;
    }

    /**
     * Gets the name of the region.
     *
     * @return the region name
     */
    public String getRegionName() {
        return regionName;
    }

    /**
     * Gets the party vote data for this region.
     *
     * @return the party vote details
     */
    public PartyVote getPartyVote() {
        return partyVote;
    }
}
