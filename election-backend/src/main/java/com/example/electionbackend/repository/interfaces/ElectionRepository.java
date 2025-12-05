package com.example.electionbackend.repository.interfaces;

import com.example.electionbackend.model.Election;

import java.util.List;

/**
 * Repository interface for managing Election entities.
 */
public interface ElectionRepository {
    Election get(String id);
    List<Election> getAll();
    Election save(Election election);
}
