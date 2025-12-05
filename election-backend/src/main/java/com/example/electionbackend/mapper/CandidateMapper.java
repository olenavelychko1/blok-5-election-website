package com.example.electionbackend.mapper;

import com.example.electionbackend.dto.CandidateDTO;
import com.example.electionbackend.model.Candidate;
import org.springframework.stereotype.Component;

/**
 * The candidate mapper class
 */
@Component
public class CandidateMapper {

    /**
     * Converts a CandidateDTO object to a Candidate entity.
     *
     * @param dto the CandidateDTO instance containing data to be converted to a Candidate entity
     * @return a new Candidate entity populated with data from the given CandidateDTO
     */
    public Candidate toEntity(CandidateDTO dto) {
        return new Candidate(
                dto.getId(),
                dto.getInitials(),
                dto.getFirstName(),
                dto.getLastName(),
                dto.getGender(),
                dto.getLocality(),
                dto.isElected()
        );
    }

    /**
     * Converts a Candidate entity to a CandidateDTO.
     *
     * @param entity the Candidate entity to be converted
     * @return a new CandidateDTO populated with data from the given entity
     */
    public CandidateDTO toDTO(Candidate entity) {
        return new CandidateDTO(
                entity.getId(),
                entity.getInitials(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getGender(),
                entity.getLocality(),
                entity.isElected()
        );
    }
}

