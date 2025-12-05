package com.example.electionbackend.service.interfaces;

import com.example.electionbackend.dto.ElectionDTO;
import com.example.electionbackend.model.Election;

public interface ISeederService {

    /**
     * Persists an election record.
     * @param electionDTO payload containing election data
     * @return entity that was stored
     */
    Election persistElection(ElectionDTO electionDTO);
}