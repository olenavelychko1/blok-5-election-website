package com.example.electionbackend.interfaces;

import com.example.electionbackend.dto.RegionDTO;
import com.example.electionbackend.model.Constituency;

import java.util.List;

/**
 * Service interface for managing Constituency entities.
 */
public interface IConstituencyService {
    RegionDTO getById(int id);
    List<RegionDTO> getAllConstituencies(int page, int size, int year);
    List<RegionDTO> getAllMunicipalitiesById(int id);
    Constituency createConstituency(Constituency constituency);
    Constituency updateConstituency(Constituency constituency);
    void deleteConstituency(int id);
}
