package com.example.electionbackend.controller.api.v1;

import com.example.electionbackend.dto.MunicipalityDTO;
import com.example.electionbackend.dto.MunicipalityPollingStationDTO;
import com.example.electionbackend.dto.PollingStationDTO;
import com.example.electionbackend.dto.RegionDTO;
import com.example.electionbackend.exception.InvalidSortPropertyException;
import com.example.electionbackend.exception.municipality.MunicipalityNotFoundException;
import com.example.electionbackend.interfaces.IMunicipalityService;
import com.example.electionbackend.model.Municipality;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for MunicipalityController.
 */
@WebMvcTest(MunicipalityController.class)
public class MunicipalityControllerTest {
    // MockMvc to simulate HTTP requests
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private IMunicipalityService municipalityService;

    @Autowired
    private ObjectMapper objectMapper;

    private List<RegionDTO> municipalities;

    @BeforeEach
    void setUp() {
        municipalities = List.of(
                new RegionDTO(1, "Amsterdam"),
                new RegionDTO(2, "Almere"),
                new RegionDTO(3, "Utrecht")
        );
    }

    /**
     * Verifies that getting a municipality by ID returns HTTP 200 with the correct data.
     *
     * @throws Exception - if an error occurs during the test
     */
    @Test
    void shouldReturn200_whenGetMunicipalityById() throws Exception {
        // Arrange
        when(municipalityService.getById(1)).thenReturn(municipalities.get(0));

        // Act & Assert
        mockMvc.perform(get("/v1/municipalities/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Amsterdam"));
    }

    /**
     * Verifies that getting a municipality by an invalid ID returns HTTP 404.
     *
     * @throws Exception - if an error occurs during the test
     */
    @Test
    void shouldReturn404_whenGetMunicipalityByInvalidId() throws Exception {
        // Arrange
        int id = 999;
        when(municipalityService.getById(id)).thenThrow(new MunicipalityNotFoundException(id));

        // Act & Assert
        mockMvc.perform(get("/v1/municipalities/999"))
                .andExpect(status().isNotFound());
    }

    /**
     * Verifies that getting all municipalities returns HTTP 200 with the correct data.
     *
     * @throws Exception - if an error occurs during the test
     */
    @Test
    void shouldReturn200_whenGetAllMunicipalities() throws Exception {
        // Arrange
        Page<RegionDTO> page = new PageImpl<>(municipalities);
        when(municipalityService.getAll(any(Pageable.class), anyInt())).thenReturn(page);


        // Act & Assert
        mockMvc.perform(get("/v1/municipalities"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.content[0].name").value("Amsterdam"))
                .andExpect(jsonPath("$.content[1].id").value(2))
                .andExpect(jsonPath("$.content[1].name").value("Almere"))
                .andExpect(jsonPath("$.content[2].id").value(3))
                .andExpect(jsonPath("$.content[2].name").value("Utrecht"));
    }

    /**
     * Verifies that getting all municipalities with an invalid sort property returns HTTP 400.
     *
     * @throws Exception - if an error occurs during the test
     */
    @Test
    void shouldReturn400_whenGetAllMunicipalities_WithInvalidSort() throws Exception {
        // Arrange
        String sortProperty = "invalidField!!!";
        when(municipalityService.getAll(any(Pageable.class), anyInt()))
                .thenThrow(new InvalidSortPropertyException(sortProperty));

        // Act & Assert
        mockMvc.perform(get("/v1/municipalities")
                        .param("sort", "invalidField!!!,asc"))
                .andExpect(status().isBadRequest());
    }

    /**
     * Verifies that getting all polling stations by municipality ID returns HTTP 200 with the correct data.
     *
     * @throws Exception - if an error occurs during the test
     */
    @Test
    void shouldReturn200_whenGetAllPollingStationsByMunicipalityId() throws Exception {
        // Arrange
        int municipalityId = 1;
        MunicipalityPollingStationDTO dto = new MunicipalityPollingStationDTO(
                municipalityId,
                "Almere",
                List.of(
                        new PollingStationDTO("SB1", "Station 1"),
                        new PollingStationDTO("SB2", "Station 2")
                )
        );
        when(municipalityService.getPollingStationsById(municipalityId)).thenReturn(dto);

        // Act & Assert
        mockMvc.perform(get("/v1/municipalities/{id}/pollingStations", municipalityId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(municipalityId))
                .andExpect(jsonPath("$.name").value("Almere"))
                .andExpect(jsonPath("$.pollingStationList[0].pollingStationId").value("SB1"))
                .andExpect(jsonPath("$.pollingStationList[1].pollingStationId").value("SB2"));
    }

    /**
     * Verifies that creating a municipality returns HTTP 200 with the created data.
     *
     * @throws Exception - if an error occurs during the test
     */
    @Test
    void shouldReturn201_whenCreateMunicipality() throws Exception {
        // Arrange
        Municipality municipality = new Municipality("Amsterdam");
        municipality.setId(1);

        when(municipalityService.create(municipality)).thenReturn(municipality);

        // Act & Assert
        mockMvc.perform(post("/v1/municipalities")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(municipality)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Amsterdam"));
    }

    /**
     * Verifies that creating a municipality with invalid data returns HTTP 400.
     *
     * @throws Exception - if an error occurs during the test
     */
    @Test
    void shouldReturn400_whenCreateMunicipality_WithInvalidData() throws Exception {
        // Arrange
        MunicipalityDTO municipality = new MunicipalityDTO(0, null);
        when(municipalityService.create(any(Municipality.class)))
                .thenThrow(new IllegalArgumentException("Invalid municipality data"));

        // Act & Assert
        mockMvc.perform(post("/v1/municipalities")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(municipality)))
                .andExpect(status().isBadRequest());
    }

    /**
     * Verifies that updating a municipality returns HTTP 200 with the updated data.
     *
     * @throws Exception - if an error occurs during the test
     */
    @Test
    void shouldReturn200_whenUpdateMunicipality() throws Exception {
        // Arrange
        Municipality municipality = new Municipality("Amsterdam Updated");
        municipality.setId(1);

        when(municipalityService.update(municipality)).thenReturn(municipality);

        // Act & Assert
        mockMvc.perform(put("/v1/municipalities/{id}", municipality.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(municipality)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Amsterdam Updated"));

    }

    /**
     * Verifies that updating a municipality with invalid data returns HTTP 400.
     *
     * @throws Exception - if an error occurs during the test
     */
    @Test
    void shouldReturn404_whenUpdatingNonExistingMunicipality() throws Exception {
        // Arrange
        MunicipalityDTO municipality = new MunicipalityDTO(0, null);
        when(municipalityService.update(any(Municipality.class)))
                .thenThrow(new MunicipalityNotFoundException(0));

        // Act & Assert
        mockMvc.perform(put("/v1/municipalities/{id}", municipality.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(municipality)))
                .andExpect(status().isNotFound());
    }

    /**
     * Verifies that deleting a municipality returns HTTP 204.
     *
     * @throws Exception - if an error occurs during the test
     */
    @Test
    void shouldReturn204_whenDeleteMunicipality() throws Exception {
        // Arrange
        int municipalityId = 1;

        // Act & Assert
        mockMvc.perform(delete("/v1/municipalities/{id}", municipalityId))
                .andExpect(status().isNoContent());
    }

    /**
     * Verifies that deleting a non-existing municipality returns HTTP 404.
     *
     * @throws Exception - if an error occurs during the test
     */
    @Test
    void shouldReturn404_whenDeleteMunicipality_NotFound() throws Exception {
        // Arrange
        int municipalityId = 999;
        doThrow(new MunicipalityNotFoundException(municipalityId))
                .when(municipalityService).delete(municipalityId);

        // Act & Assert
        mockMvc.perform(delete("/v1/municipalities/{id}", municipalityId))
                .andExpect(status().isNotFound());
    }

}
