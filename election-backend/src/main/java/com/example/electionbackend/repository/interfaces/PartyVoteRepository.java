package com.example.electionbackend.repository.interfaces;

import com.example.electionbackend.model.PartyVote;
import com.example.electionbackend.type.RegionType;

import java.util.List;

/**
 * Repository interface for managing Party Vote entities.
 */
public interface PartyVoteRepository {
    List<PartyVote> findByPartyId(int id);
    List<PartyVote> findByType(RegionType type);
    List<PartyVote> findByTypeAndTypeId(RegionType type, String id);
    List<PartyVote> findByTypeAndTypeIdAndPartyId(RegionType type, String id, int partyId);
    PartyVote save(PartyVote partyVote);
    PartyVote delete(PartyVote partyVote);
    PartyVote deleteById(int id);
}
