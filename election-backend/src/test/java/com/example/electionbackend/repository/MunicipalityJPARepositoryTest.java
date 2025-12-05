package com.example.electionbackend.repository;

import com.example.electionbackend.model.*;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;

/**
 * Unit tests for MunicipalityJPARepository
 */
@DataJpaTest
@Import(MunicipalityJPARepository.class)
public class MunicipalityJPARepositoryTest {
    @Autowired
    private MunicipalityJPARepository municipalityJPARepository;

    @Autowired
    EntityManager entityManager;

    private List<Municipality> municipalityList;

    /**
     * Arranges test data before each test case.
     * Returns a list of created Municipalities with related associations.
     */
    @BeforeEach
    void setUp() {
        // Metadata
        Metadata metadata = new Metadata();
        entityManager.persist(metadata);

        // State
        State state = new State();
        state.setName("Test State");
        state.setMetadata(metadata);
        entityManager.persist(state);

        // Election, needed for the year
        Election election = new Election("TK" + 2025);
        election.setState(state);
        entityManager.persist(election);

        // Constituency
        Constituency constituency = new Constituency();
        constituency.setName("Test Constituency");
        constituency.setState(state);
        constituency.setMetadata(metadata);
        entityManager.persist(constituency);

        // Create Municipalities
        Municipality m1 = new Municipality();
        m1.setName("Almere");
        m1.setConstituency(constituency);
        m1.setMetadata(metadata);
        entityManager.persist(m1);

        Municipality m2 = new Municipality();
        m2.setName("Amsterdam");
        m2.setConstituency(constituency);
        m2.setMetadata(metadata);
        entityManager.persist(m2);

        entityManager.flush();
        municipalityList = List.of(m1, m2);
    }

    /**
     * Verifies that findById returns the correct Municipality when it exists.
     */
    @Test
    void findById_shouldReturnMunicipality_whenExists() {
        // Arrange
        Municipality municipality = municipalityList.get(0);

        // Act
        Municipality result = municipalityJPARepository.findById(municipality.getId());

        // Assert
        assertNotNull(result);
        assertEquals("Almere", result.getName());
        assertEquals(municipality.getId(), result.getId());
    }

    /**
     * Verifies that findById returns null when the Municipality does not exist.
     */
    @Test
    void findById_shouldReturnNull_whenNotExists() {
        // Act
        Municipality result = municipalityJPARepository.findById(999);

        // Assert
        assertNull(result);
    }

    /**
     * Verifies that findAll returns all Municipalities.
     */
    @Test
    void findAll_shouldReturnAllMunicipalities() {
        // Act
        List<Municipality> result = municipalityJPARepository.findAll(2025);

        // Assert
        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(m -> m.getName().equals("Almere")));
        assertTrue(result.stream().anyMatch(m -> m.getName().equals("Amsterdam")));
    }

    /**
     * Verifies that findAll returns an empty list when no Municipalities exist.
     */
    @Test
    void findAll_shouldReturnEmptyList_whenNoMunicipalitiesExist() {
        // Arrange
        entityManager.createQuery("DELETE FROM Municipality").executeUpdate();

        // Act
        List<Municipality> result = municipalityJPARepository.findAll(anyInt());

        // Assert
        assertTrue(result.isEmpty());
    }

    /**
     * Verifies that save creates a new Municipality.
     */
    @Test
    void save_shouldCreateNewMunicipality() {
        // Arrange
        Municipality existingMunicipality = municipalityList.get(0);

        Municipality newMunicipality = new Municipality();
        newMunicipality.setName("Almere");
        newMunicipality.setConstituency(existingMunicipality.getConstituency());
        newMunicipality.setMetadata(existingMunicipality.getMetadata());

        // Act
        Municipality savedMunicipality = municipalityJPARepository.save(newMunicipality);

        // Assert
        assertNotNull(savedMunicipality);
        assertNotNull(savedMunicipality.getId());
        assertEquals("Almere", savedMunicipality.getName());
    }

    /**
     * Verifies that save updates an existing Municipality.
     */
    @Test
    void save_shouldUpdateExistingMunicipality() {
        // Arrange
        Municipality municipality = municipalityList.get(0);
        municipality.setName("Updated Name");

        // Act
        Municipality updatedMunicipality = municipalityJPARepository.save(municipality);

        // Assert
        assertNotNull(updatedMunicipality);
        assertEquals("Updated Name", updatedMunicipality.getName());
    }

    /**
     * Verifies that delete removes the Municipality.
     */
    @Test
    void delete_shouldRemoveMunicipality() {
        // Arrange
        Municipality municipality = municipalityList.get(0);

        // Act
        municipalityJPARepository.delete(municipality);
        Municipality result = municipalityJPARepository.findById(municipality.getId());

        // Assert
        assertNull(result);
    }

    /**
     * Verifies that deleteById removes the Municipality by its ID.
     */
    @Test
    void deleteById_shouldRemoveMunicipality() {
        // Arrange
        Municipality municipality = municipalityList.get(0);

        // Act
        municipalityJPARepository.deleteById(municipality.getId());
        Municipality result = municipalityJPARepository.findById(municipality.getId());

        // Assert
        assertNull(result);
    }
}
