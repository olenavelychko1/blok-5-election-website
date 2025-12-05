package com.example.electionbackend.service;

import com.example.electionbackend.dto.RegionDTO;
import com.example.electionbackend.exception.InvalidSortPropertyException;
import com.example.electionbackend.mapper.PollingStationMapper;
import com.example.electionbackend.model.PollingStation;
import com.example.electionbackend.repository.interfaces.PollingStationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Test class for PollingStationService.
 */
@ExtendWith(MockitoExtension.class)
public class PollingStationServiceTest {
    @InjectMocks
    private PollingStationService pollingStationService;

    @Mock
    private PollingStationRepository pollingStationRepository;

    @Mock
    private PollingStationMapper pollingStationMapper;

    private PollingStation pollingStation;
    private final List<PollingStation> pollingStationList = new ArrayList<>();

    private RegionDTO regionDTO;
    private final List<RegionDTO> regionDTOList = new ArrayList<>();

    /**
     * Sets up test data before each test case.
     */
    @BeforeEach
    void setup() {
        pollingStation = new PollingStation("SB1", "Almere");
        pollingStation.setId(1);
        regionDTO = new RegionDTO(1, "Almere");

        pollingStationList.add(pollingStation);
        pollingStationList.add(new PollingStation("SB2", "Amsterdam"));

        regionDTOList.add(regionDTO);
        regionDTOList.add(new RegionDTO(2, "Amsterdam"));
    }

    /**
     * Verifies that getById returns the correct RegionDTO.
     */
    @Test
    void getById_shouldReturnRegionDTO() {
        // Arrange
        when(pollingStationRepository.findById(1)).thenReturn(pollingStation);
        when(pollingStationMapper.toRegionDTO(pollingStation)).thenReturn(regionDTO);

        // Act
        RegionDTO result = pollingStationService.getById(1);

        // Assert
        assertNotNull(result);
        assertEquals(regionDTO.getId(), result.getId());
        assertEquals(regionDTO.getName(), result.getName());
        verify(pollingStationRepository).findById(1);
    }

    /**
     * Verifies that getById throws an exception when the polling station is not found.
     */
    @Test
    void getById_shouldThrowException_whenPollingStationNotFound() {
        // Arrange
        when(pollingStationRepository.findById(999)).thenReturn(null);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> pollingStationService.getById(999));
        verify(pollingStationRepository).findById(999);
    }

    /**
     * Verifies that getAll returns a paged list of RegionDTOs.
     */
    @Test
    void getAll_shouldReturnPagedPollingStations() {
        // Arrange
        when(pollingStationRepository.findAll(anyInt())).thenReturn(pollingStationList);
        when(pollingStationMapper.toRegionDTO(pollingStationList.get(0))).thenReturn(regionDTOList.get(0));
        when(pollingStationMapper.toRegionDTO(pollingStationList.get(1))).thenReturn(regionDTOList.get(1));

        Pageable pageable = PageRequest.of(0, 10);

        // Act
        Page<RegionDTO> result = pollingStationService.getAll(pageable, anyInt());

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        assertEquals(2, result.getContent().size());
        verify(pollingStationRepository).findAll(anyInt());
    }

    /**
     * Verifies that getAll returns sorted polling stations.
     */
    @Test
    void getAll_shouldReturnSortedPollingStations() {
        // Arrange
        when(pollingStationRepository.findAll(anyInt())).thenReturn(pollingStationList);
        when(pollingStationMapper.toRegionDTO(pollingStationList.get(0))).thenReturn(regionDTOList.get(0));
        when(pollingStationMapper.toRegionDTO(pollingStationList.get(1))).thenReturn(regionDTOList.get(1));

        Pageable pageable = PageRequest.of(0, 10, Sort.by("name").descending());

        // Act
        Page<RegionDTO> result = pollingStationService.getAll(pageable, anyInt());

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        assertEquals(2, result.getContent().size());
        assertEquals("Amsterdam", result.getContent().get(0).getName());
        assertEquals("Almere", result.getContent().get(1).getName());
        verify(pollingStationRepository).findAll(anyInt());
    }

    /**
     * Verifies that getAll throws an exception when an invalid sort property is provided.
     */
    @Test
    void getAll_shouldThrowException_whenInvalidSortProperty() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10, Sort.by("invalidProperty"));

        when(pollingStationRepository.findAll(anyInt())).thenReturn(pollingStationList);
        when(pollingStationMapper.toRegionDTO(any(PollingStation.class))).thenReturn(regionDTO);

        // Act & Assert
        assertThrows(InvalidSortPropertyException.class, () -> pollingStationService.getAll(pageable, anyInt()));
    }
}
