package com.example.electionbackend.interfaces;

import com.example.electionbackend.dto.RegionDTO;
import com.example.electionbackend.dto.RegionPartyVoteDTO;
import com.example.electionbackend.model.PollingStation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service interface for managing Polling Station entities.
 */
public interface IPollingStationService {
    RegionDTO getById(int id);
    Page<RegionDTO> getAll(Pageable pageable, int year);
    PollingStation create(PollingStation pollingStation);
    PollingStation update(PollingStation pollingStation);
    void delete(int id);
}