package com.example.electionbackend.dto;

import com.example.electionbackend.model.Metadata;
import com.example.electionbackend.model.PartyVote;

import java.util.List;
// TODO: remove
/**
 * Data Transfer Object representing a region along with its party votes and metadata.
 * Inherits from RegionDTO.
 */
public class RegionPartyVoteDTO extends RegionDTO {
    private List<PartyVote> partyVotes;
    private Metadata metadata;

    /**
     * Constructor to initialize a RegionPartyVoteDTO with ID, name, party votes, and metadata.
     *
     * @param id unique identifier of the region
     * @param name name of the region
     * @param partyVotes list of party votes for this region
     * @param metadata metadata information for the region
     */
    public RegionPartyVoteDTO(int id, String name, List<PartyVote> partyVotes, Metadata metadata) {
        super(id, name);
        this.partyVotes = partyVotes;
        this.metadata = metadata;
    }

    public List<PartyVote> getPartyVotes() {
        return partyVotes;
    }

    public void setPartyVotes(List<PartyVote> partyVotes) {
        this.partyVotes = partyVotes;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }
}
