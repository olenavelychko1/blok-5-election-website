package nl.hva.ict.sm3.backend.model;

/**
 * Data Transfer Object (DTO) that associates a candidate’s vote results with a specific region.
 * <p>
 * Used to return structured data that includes both the region name and
 * the detailed vote information for an individual candidate.
 */
public class RegionCandidateVoteDTO {
    private final String regionName;
    private final RegionCandidateVote candidateVote;

    /**
     * Constructs a new RegionCandidateVoteDTO.
     *
     * @param regionName    the name of the region (e.g., constituency or municipality)
     * @param candidateVote the candidate vote data for this region
     */
    public RegionCandidateVoteDTO(String regionName, RegionCandidateVote candidateVote) {
        this.regionName = regionName;
        this.candidateVote = candidateVote;
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
     * Gets the candidate vote data for this region.
     *
     * @return the candidate vote details
     */
    public RegionCandidateVote getCandidateVote() {
        return candidateVote;
    }

    @Override
    public String toString() {
        return "RegionCandidateVoteDTO{" +
                "regionName='" + regionName + '\'' +
                ", candidateVote=" + candidateVote +
                '}';
    }
}
