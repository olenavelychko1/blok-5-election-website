package com.example.electionbackend.repository.interfaces;

import com.example.electionbackend.model.Constituency;
import com.example.electionbackend.model.Municipality;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for managing State entities.
 */
@Repository
public interface ConstituencyRepository  {
    Constituency findById(int id);
    List<Constituency> findAll(int page, int size, int year);
    List<Municipality> findAllMunicipalitiesById(int id);
    Constituency save(Constituency constituency);
    Constituency delete(Constituency constituency);
    Constituency deleteById(int id);
}