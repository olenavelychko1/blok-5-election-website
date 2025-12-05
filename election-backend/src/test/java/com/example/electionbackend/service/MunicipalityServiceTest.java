package com.example.electionbackend.service;

import com.example.electionbackend.dto.MunicipalityPollingStationDTO;
import com.example.electionbackend.dto.PollingStationDTO;
import com.example.electionbackend.dto.RegionDTO;
import com.example.electionbackend.exception.InvalidSortPropertyException;
import com.example.electionbackend.exception.municipality.MunicipalityNotFoundException;
import com.example.electionbackend.mapper.MunicipalityMapper;
import com.example.electionbackend.model.Municipality;
import com.example.electionbackend.model.PollingStation;
import com.example.electionbackend.repository.interfaces.MunicipalityRepository;
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
 * Unit tests for MunicipalityService
 */
@ExtendWith(MockitoExtension.class)
public class MunicipalityServiceTest {
    @InjectMocks
    private MunicipalityService municipalityService;

    @Mock
    private MunicipalityRepository municipalityRepository;

    @Mock
    private MunicipalityMapper municipalityMapper;

    private Municipality municipality;
    private final List<Municipality> municipalityList = new ArrayList<>();

    private RegionDTO regionDTO;
    private final List<RegionDTO> regionDTOList = new ArrayList<>();

    @BeforeEach
    void setup() {
        municipality = new Municipality("Almere");
        municipality.setId(1);
        Municipality municipality2 = new Municipality("Amsterdam");
        municipality2.setId(2);

        regionDTO = new RegionDTO(1, "Almere");

        municipalityList.add(municipality);
        municipalityList.add(municipality2);

        regionDTOList.add(new RegionDTO(1, "Almere"));
        regionDTOList.add(new RegionDTO(2, "Amsterdam"));
    }

    /**
     * Verifies that getById returns the correct RegionDTO when a valid ID is provided.
     */
    @Test
    void getById_shouldReturnRegionDTO_withValidId() {
        // Arrange
        int id = 1;
        when(municipalityRepository.findById(id)).thenReturn(municipality);
        when(municipalityMapper.toRegionDTO(municipality)).thenReturn(regionDTO);

        // Act
        RegionDTO result = municipalityService.getById(id);

        // Assert
        assertEquals(regionDTO, result);
        assertEquals(id, result.getId());
        assertEquals("Almere", result.getName());
        verify(municipalityRepository).findById(id);
    }

    /**
     * Verifies that getById throws MunicipalityNotFoundException when an invalid ID is provided.
     */
    @Test
    void getById_shouldThrowMunicipalityNotFoundException_withInvalidId() {
        // Arrange
        int id = 999;
        when(municipalityRepository.findById(id)).thenReturn(null);

        // Act & Assert
        assertThrows(MunicipalityNotFoundException.class, () -> municipalityService.getById(id));
        verify(municipalityRepository).findById(id);
    }

    /**
     * Verifies that getAll returns a paged list of RegionDTOs.
     */
    @Test
    void getAll_shouldReturnPagedMunicipalities() {
        // Arrange
        when(municipalityRepository.findAll(anyInt())).thenReturn(municipalityList);
        when(municipalityMapper.toRegionDTO(municipalityList.get(0))).thenReturn(regionDTOList.get(0));
        when(municipalityMapper.toRegionDTO(municipalityList.get(1))).thenReturn(regionDTOList.get(1));

        Pageable pageable = PageRequest.of(0, 10);

        // Act
        Page<RegionDTO> result = municipalityService.getAll(pageable, anyInt());

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        assertEquals(2, result.getContent().size());
        verify(municipalityRepository).findAll(anyInt());
    }

    /**
     * Verifies that getAll returns a sorted list of RegionDTOs when sorting is applied.
     */
    @Test
    void getAll_shouldReturnSortedMunicipalities() {
        // Arrange
        when(municipalityRepository.findAll(anyInt())).thenReturn(municipalityList);
        when(municipalityMapper.toRegionDTO(municipalityList.get(0))).thenReturn(regionDTOList.get(0));
        when(municipalityMapper.toRegionDTO(municipalityList.get(1))).thenReturn(regionDTOList.get(1));

        Pageable pageable = PageRequest.of(0, 10, Sort.by("name").descending());

        // Act
        Page<RegionDTO> result = municipalityService.getAll(pageable, anyInt());

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        assertEquals(2, result.getContent().size());
        assertEquals("Amsterdam", result.getContent().get(0).getName());
        assertEquals("Almere", result.getContent().get(1).getName());
        verify(municipalityRepository).findAll(anyInt());
    }

    /**
     * Verifies that getAll throws InvalidSortPropertyException when an invalid sort property is provided.
     */
    @Test
    void getAll_shouldThrowException_whenInvalidSortProperty() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10, Sort.by("invalidSort!1"));

        when(municipalityRepository.findAll(anyInt())).thenReturn(municipalityList);
        when(municipalityMapper.toRegionDTO(any(Municipality.class))).thenReturn(regionDTO);

        // Act & Assert
        assertThrows(InvalidSortPropertyException.class, () -> municipalityService.getAll(pageable, anyInt()));
    }

    @Test
    void getPollingStationById_shouldReturnMunicipalityPollingStationDTO() {
        // Arrange
        MunicipalityPollingStationDTO dto = new MunicipalityPollingStationDTO(
                1,
                "Almere",
                List.of(new PollingStationDTO("SB1", "Station 1"),
                        new PollingStationDTO("SB2", "Station 2"))
        );

        when(municipalityRepository.findById(municipality.getId())).thenReturn(municipality);
        when(municipalityMapper.toMunicipalityPollingStationDTO(municipality)).thenReturn(dto);

        // Act
        MunicipalityPollingStationDTO result = municipalityService.getPollingStationsById(municipality.getId());

        // Assert
        assertEquals(dto, result);
        verify(municipalityRepository).findById(municipality.getId());
    }

}

