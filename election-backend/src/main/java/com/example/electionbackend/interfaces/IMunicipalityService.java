package com.example.electionbackend.interfaces;

import com.example.electionbackend.dto.RegionDTO;
import com.example.electionbackend.dto.RegionPartyVoteDTO;
import com.example.electionbackend.dto.MunicipalityPollingStationDTO;
import com.example.electionbackend.model.Municipality;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service interface for managing Municipality entities.
 */
public interface IMunicipalityService {
    RegionDTO getById(int id);
    Page<RegionDTO> getAll(Pageable pageable, int year);
    MunicipalityPollingStationDTO getPollingStationsById(int id);
    Municipality create(Municipality municipality);
    Municipality update(Municipality municipality);
    void delete(int id);
}