package com.example.electionbackend.controller.api.v1;

import com.example.electionbackend.dto.RegionDTO;
import com.example.electionbackend.exception.InvalidSortPropertyException;
import com.example.electionbackend.exception.pollingStation.PollingStationNotFoundException;
import com.example.electionbackend.interfaces.IPollingStationService;
import com.example.electionbackend.model.PollingStation;
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
 * Test class for PollingStationController.
 */
@WebMvcTest(PollingStationController.class)
public class PollingStationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private IPollingStationService pollingStationService;

    @Autowired
    private ObjectMapper objectMapper;

    private List<RegionDTO> pollingStations;

    /**
     * Sets up test data before each test case.
     */
    @BeforeEach
    void setup() {
        pollingStations = List.of(
                new RegionDTO(1, "Station 1"),
                new RegionDTO(2, "Station 2")
        );
    }

    /**
     * Verifies that getting a polling station by ID returns a 200 status code.
     */
    @Test
    void shouldReturn200_whenGetPollingStationById() throws Exception {
        // Arrange
        when(pollingStationService.getById(1)).thenReturn(pollingStations.get(0));

        // Act & Assert
        mockMvc.perform(get("/v1/pollingStations/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Station 1"));
    }

    /**
     * Verifies that getting a polling station by a non-existent ID returns a 404 status code.
     */
    @Test
    void shouldReturn404_whenGetPollingStationByIdNotFound() throws Exception {
        // Arrange
        int id = 999;
        when(pollingStationService.getById(id)).thenThrow(new PollingStationNotFoundException(id));

        // Act & Assert
        mockMvc.perform(get("/v1/pollingStations/999"))
                .andExpect(status().isNotFound());
    }

    /**
     * Verifies that getting all polling stations returns a 200 status code.
     */
    @Test
    void shouldReturn200_whenGetAllPollingStations() throws Exception {
        // Arrange
        Page<RegionDTO> page = new PageImpl<>(pollingStations);
        when(pollingStationService.getAll(any(Pageable.class), anyInt())).thenReturn(page);

        // Act & Assert
        mockMvc.perform(get("/v1/pollingStations"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content.length()").value(2))
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.content[0].name").value("Station 1"))
                .andExpect(jsonPath("$.content[1].id").value(2))
                .andExpect(jsonPath("$.content[1].name").value("Station 2"));
    }

    /**
     * Verifies that getting all polling stations with an invalid sort property returns a 400 status code.
     */
    @Test
    void shouldReturn400_whenGetAllPollingStations_withInvalidSort() throws Exception {
        // Arrange
        String sortProperty = "invalidField!!!!!!";
        when(pollingStationService.getAll(any(Pageable.class), anyInt()))
                .thenThrow(new InvalidSortPropertyException(sortProperty));

        // Act & Assert
        mockMvc.perform(get("/v1/pollingStations")
                        .param("sort", sortProperty + ",asc"))
                .andExpect(status().isBadRequest());
    }

    /**
     * Verifies that creating a polling station returns a 201 status code.
     */
    @Test
    void shouldReturn201_whenCreatePollingStation() throws Exception {
        // Arrange
        PollingStation pollingStation = new PollingStation("SB1", "Station");
        when(pollingStationService.create(any(PollingStation.class))).thenReturn(pollingStation);

        // Act & Assert
        mockMvc.perform(post("/v1/pollingStations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pollingStation)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.pollingStationId").value("SB1"))
                .andExpect(jsonPath("$.name").value("Station"));
    }

    /**
     * Verifies that creating a polling station with invalid data returns a 400 status code.
     */
    @Test
    void shouldReturn400_whenCreatePollingStation_withInvalidData() throws Exception {
        // Arrange
        PollingStation pollingStation = new PollingStation("", "");
        when(pollingStationService.create(any(PollingStation.class)))
                .thenThrow(new IllegalArgumentException("Invalid data"));

        // Act & Assert
        mockMvc.perform(post("/v1/pollingStations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pollingStation)))
                .andExpect(status().isBadRequest());
    }

    /**
     * Verifies that updating a polling station returns a 200 status code.
     */
    @Test
    void shouldReturn200_whenUpdatePollingStation() throws Exception {
        // Arrange
        PollingStation pollingStation = new PollingStation("SB1", "Updated Station");
        when(pollingStationService.update(any(PollingStation.class))).thenReturn(pollingStation);

        // Act & Assert
        mockMvc.perform(put("/v1/pollingStations/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pollingStation)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pollingStationId").value("SB1"))
                .andExpect(jsonPath("$.name").value("Updated Station"));

        verify(pollingStationService).update(any(PollingStation.class));
    }

    /**
     * Verifies that updating a non-existent polling station returns a 404 status code.
     */
    @Test
    void shouldReturn404_whenUpdatingNonExistentPollingStation() throws Exception {
        // Arrange
        PollingStation pollingStation = new PollingStation("SB999", "Station 99999");
        when(pollingStationService.update(any(PollingStation.class)))
                .thenThrow(new PollingStationNotFoundException(999));

        // Act & Assert
        mockMvc.perform(put("/v1/pollingStations/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pollingStation)))
                .andExpect(status().isNotFound());
    }

    /**
     * Verifies that deleting a polling station returns a 204 status code.
     */
    @Test
    void shouldReturn204_whenDeletePollingStation() throws Exception {
        // Act & Assert
        mockMvc.perform(delete("/v1/pollingStations/1"))
                .andExpect(status().isNoContent());

        verify(pollingStationService).delete(1);
    }

    /**
     * Verifies that deleting a non-existent polling station returns a 404 status code.
     */
    @Test
    void shouldReturn404_whenDeletePollingStation_NotFound() throws Exception {
        // Arrange
        int id = 999;
        doThrow(new PollingStationNotFoundException(id))
                .when(pollingStationService).delete(id);

        // Act & Assert
        mockMvc.perform(delete("/v1/pollingStations/999"))
                .andExpect(status().isNotFound());
    }

}
