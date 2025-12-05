package com.example.electionbackend.interfaces;

import com.example.electionbackend.dto.PartyVoteDTO;
import com.example.electionbackend.dto.PartyVoteDetailDTO;
import com.example.electionbackend.model.PartyVote;
import com.example.electionbackend.type.RegionType;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Interface for PartyVote service operations.
 */
public interface IPartyVoteService {
    List<PartyVoteDetailDTO> getByPartyId(int partyId, Pageable pageable);
    List<PartyVoteDetailDTO> getByRegionType(RegionType regionType, Pageable pageable);
    List<PartyVoteDetailDTO> getByRegionTypeAndId(RegionType regionType, String regionId, Pageable pageable);
    List<PartyVoteDetailDTO> getByRegionTypeAndIdAndPartyId(RegionType regionType, String regionId, int partyId, Pageable pageable);
    PartyVote create(PartyVote partyVote);
    PartyVote update(PartyVote partyVote);
    void delete(int id);
}
