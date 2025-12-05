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

/**
 * Test class for PollingStationJPARepository.
 */
@DataJpaTest
@Import(PollingStationJPARepository.class)
public class PollingStationJPARepositoryTest {
    @Autowired
    private PollingStationJPARepository pollingStationJPARepository;

    @Autowired
    EntityManager entityManager;

    private List<PollingStation> pollingStationList;

    /**
     * Sets up test data before each test case
     * Returns a list of polling stations with related associations.
     */
    @BeforeEach
    void setup() {
        // Metadata
        Metadata metadata = new Metadata();
        entityManager.persist(metadata);

        // State
        State state = new State();
        state.setName("State 1");
        state.setMetadata(metadata);
        entityManager.persist(state);

        // Election, needed for the year
        Election election = new Election("TK" + 2025);
        election.setState(state);
        entityManager.persist(election);

        // Constituency
        Constituency constituency = new Constituency();
        constituency.setName("Constituency 1");
        constituency.setState(state);
        constituency.setMetadata(metadata);
        entityManager.persist(constituency);

        // Municipality
        Municipality municipality = new Municipality();
        municipality.setName("Municipality 1");
        municipality.setConstituency(constituency);
        municipality.setMetadata(metadata);
        entityManager.persist(municipality);

        // Create polling stations
        PollingStation p1 = new PollingStation();
        p1.setName("Station 1");
        p1.setMunicipality(municipality);
        p1.setMetadata(metadata);
        entityManager.persist(p1);

        PollingStation p2 = new PollingStation();
        p2.setName("Station 2");
        p2.setMunicipality(municipality);
        p2.setMetadata(metadata);
        entityManager.persist(p2);

        entityManager.flush();
        pollingStationList = List.of(p1, p2);

    }

    /**
     * Verifies that findById returns the correct polling station when the ID exists.
     */
    @Test
    void findById_shouldReturnPollingStation_whenIdExists() {
        // Arrange
        PollingStation pollingStation = pollingStationList.get(0);

        // Act
        PollingStation result = pollingStationJPARepository.findById(pollingStation.getId());

        // Assert
        assertNotNull(result);
        assertEquals("Station 1", result.getName());
        assertEquals(pollingStation.getId(), result.getId());
    }

    /**
     * Verifies that findById returns null when the ID does not exist.
     */
    @Test
    void findById_shouldReturnNull_whenNotExists() {
        // Act
        PollingStation result = pollingStationJPARepository.findById(9999);

        // Assert
        assertNull(result);
    }

    /**
     * Verifies that findAll returns all polling stations.
     */
    @Test
    void findAll_shouldReturnAllPollingStations() {
        // Act
        List<PollingStation> result = pollingStationJPARepository.findAll(2025);

        // Assert
        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(p -> p.getName().equals("Station 1")));
        assertTrue(result.stream().anyMatch(p -> p.getName().equals("Station 2")));
    }

    /**
     * Verifies that findAll returns an empty list when no polling stations exist.
     */
    @Test
    void findAll_shouldReturnEmptyList_whenNoPollingStationsExist() {
        // Arrange
       entityManager.createQuery("DELETE FROM PollingStation").executeUpdate();

        // Act
        List<PollingStation> result = pollingStationJPARepository.findAll(2025);

        // Assert
        assertTrue(result.isEmpty());
    }

    /**
     * Verifies that save creates a new polling station.
     */
    @Test
    void save_shouldCreateNewPollingStation() {
        // Arrange
        PollingStation existingStation = pollingStationList.get(0);

        PollingStation newPollingStation = new PollingStation();
        newPollingStation.setName("Station 3");
        newPollingStation.setMunicipality(existingStation.getMunicipality());
        newPollingStation.setMetadata(existingStation.getMetadata());

        // Act
        PollingStation savedPollingStation = pollingStationJPARepository.save(newPollingStation);

        // Assert
        assertNotNull(savedPollingStation);
        assertEquals("Station 3", savedPollingStation.getName());
    }

    /**
     * Verifies that save updates an existing polling station.
     */
    @Test
    void save_shouldUpdateExistingPollingStation() {
        // Arrange
        PollingStation pollingStation = pollingStationList.get(0);
        pollingStation.setName("Updated Station 1");

        // Act
        PollingStation updatedPollingStation = pollingStationJPARepository.save(pollingStation);

        // Assert
        assertNotNull(updatedPollingStation);
        assertEquals("Updated Station 1", updatedPollingStation.getName());
    }

    /**
     * Verifies that delete removes a polling station.
     */
    @Test
    void delete_shouldRemovePollingStation() {
        // Arrange
        PollingStation pollingStation = pollingStationList.get(0);

        // Act
        pollingStationJPARepository.delete(pollingStation);
        PollingStation result = pollingStationJPARepository.findById(pollingStation.getId());

        // Assert
        assertNull(result);
    }

    /**
     * Verifies that deleteById removes a polling station by its ID.
     */
    @Test
    void deleteById_shouldRemovePollingStation() {
        // Arrange
        PollingStation pollingStation = pollingStationList.get(0);

        // Act
        pollingStationJPARepository.deleteById(pollingStation.getId());
        PollingStation result = pollingStationJPARepository.findById(pollingStation.getId());

        // Assert
        assertNull(result);
    }
}
