package com.example.electionbackend.repository.interfaces;

import com.example.electionbackend.model.Municipality;

import java.util.List;

/**
 * Repository interface for managing Election entities.
 */
public interface MunicipalityRepository {
    Municipality findById(int id);
    List<Municipality> findAll(int year);
    Municipality save(Municipality municipality);
    Municipality delete(Municipality municipality);
    Municipality deleteById(int id);

}
