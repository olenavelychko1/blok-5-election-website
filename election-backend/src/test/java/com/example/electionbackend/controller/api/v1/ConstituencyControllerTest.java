package com.example.electionbackend.controller.api.v1;

import com.example.electionbackend.dto.RegionDTO;
import com.example.electionbackend.exception.constituency.ConstituencyNotFoundException;
import com.example.electionbackend.interfaces.IConstituencyService;
import com.example.electionbackend.model.Constituency;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Unit tests for {@link ConstituencyController}, executed with MockMvc using @WebMvcTest.
 */
@WebMvcTest(ConstituencyController.class)
public class ConstituencyControllerTest {
    /**
     * MockMvc simulates HTTP requests without starting the server.
     */
    @Autowired
    private MockMvc mockMvc;

    /**
     * Mocked service layer to control its outputs inside tests.
     */
    @MockitoBean
    private IConstituencyService constituencyService;

    /**
     * ObjectMapper for converting Java objects into JSON payloads.
     */
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Reusable list of constituencies for testing.
     */
    private List<RegionDTO> constituencies;

    @BeforeEach
    void setUp() {
        constituencies = List.of(
                new RegionDTO(1, "Flevoland"),
                new RegionDTO(2, "Utrecht"),
                new RegionDTO(3, "Drenthe")
        );
    }

    /**
     * Ensures that a valid constituency ID returns HTTP 200 and the correct JSON body.
     * <p>
     * GET /v1/constituencies/{id}
     */
    @Test
    void shouldReturn200_whenGetConstituencyById() throws Exception {
        // Arrange
        when(constituencyService.getById(1)).thenReturn(constituencies.getFirst());

        // Act & Assert
        mockMvc.perform(get("/v1/constituencies/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Flevoland"));
    }

    /**
     * Ensures that requesting a non-existing constituency returns HTTP 404.
     * <p>
     * GET /v1/constituencies/{id}
     */
    @Test
    void shouldReturn404_whenGetConstituencyByInvalidId() throws Exception {
        // Arrange
        int constituencyId = 999;
        when(constituencyService.getById(constituencyId)).thenThrow(new ConstituencyNotFoundException(constituencyId));

        // Act & Assert
        mockMvc.perform(get("/v1/constituencies/999"))
                .andExpect(status().isNotFound());
    }


    /**
     * Ensures that all constituencies are returned with HTTP 200 and correct JSON structure.
     * <p>
     * GET /v1/constituencies
     */
    @Test
    void shouldReturn200_whenGetAllConstituencies() throws Exception {
        // Arrange
        when(constituencyService.getAllConstituencies(anyInt(), anyInt(), anyInt())).thenReturn(constituencies);

        // Act & Assert
        mockMvc.perform(get("/v1/constituencies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Flevoland"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Utrecht"))
                .andExpect(jsonPath("$[2].id").value(3))
                .andExpect(jsonPath("$[2].name").value("Drenthe"));
    }

    /**
     * Ensures that an empty or null result returns HTTP 404.
     * <p>
     * GET /v1/constituencies
     */
    @Test
    void shouldReturn404_whenNoConstituenciesFound() throws Exception {
        // Arrange
        when(constituencyService.getAllConstituencies(anyInt(), anyInt(), anyInt())).thenReturn(null);

        // Act & Assert
        mockMvc.perform(get("/v1/constituencies"))
                .andExpect(status().isNotFound());
    }

    /**
     * Ensures that municipalities belonging to a constituency return HTTP 200.
     * <p>
     * GET /v1/constituencies/{id}/municipalities
     */
    @Test
    void shouldReturn200_whenGetAllMunicipalitiesByConstituencyId() throws Exception {
        // Arrange
        when(constituencyService.getAllMunicipalitiesById(1)).thenReturn(constituencies);

        // Act & Assert
        mockMvc.perform(get("/v1/constituencies/1/municipalities"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Flevoland"));
    }

    /**
     * Ensures that if no municipalities are found, HTTP 404 is returned.
     * <p>
     * GET /v1/constituencies/{id}/municipalities
     */
    @Test
    void shouldReturn404_whenMunicipalitiesNotFound() throws Exception {
        // Arrange
        when(constituencyService.getAllMunicipalitiesById(1)).thenReturn(List.of());

        // Act & Assert
        mockMvc.perform(get("/v1/constituencies/1/municipalities"))
                .andExpect(status().isNotFound());
    }

    /**
     * Ensures that a valid constituency creation request returns HTTP 200 and JSON body.
     * <p>
     * POST /v1/constituencies
     */
    @Test
    void shouldReturn201_whenCreateConstituency() throws Exception {
        // Arrange
        Constituency newConstituency = new Constituency("Flevoland");
        newConstituency.setId(1);
        when(constituencyService.createConstituency(newConstituency))
                .thenReturn(newConstituency);

        // Act & Assert
        mockMvc.perform(post("/v1/constituencies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newConstituency)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Flevoland"));
    }

    /**
     * Ensures that invalid creation requests return HTTP 400.
     * <p>
     * POST /v1/constituencies
     */
    @Test
    void shouldReturn400_whenCreateConstituencyInvalid() throws Exception {
        // Arrange
        Constituency constituency = new Constituency(null);
        constituency.setId(0);
        when(constituencyService.createConstituency(any(Constituency.class)))
                .thenThrow(new IllegalArgumentException("Invalid constituency data"));

        // Act & Assert
        mockMvc.perform(post("/v1/constituencies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(constituency)))
                .andExpect(status().isBadRequest());
    }

    /**
     * Ensures that updating an existing constituency returns HTTP 200 and the updated fields.
     * <p>
     * PUT /v1/constituencies/{id}
     */
    @Test
    void shouldReturn200_whenUpdateConstituency() throws Exception {
        // Arrange
        Constituency updated = new Constituency("Constituency Updated");
        updated.setId(1);

        when(constituencyService.updateConstituency(updated))
                .thenReturn(updated);

        // Act & Assert
        mockMvc.perform(put("/v1/constituencies/{id}", updated.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Constituency Updated"));
    }

    /**
     * Ensures that invalid update requests return HTTP 400.
     * <p>
     * PUT /v1/constituencies/{id}
     */
    @Test
    void shouldReturn400_whenUpdateFails() throws Exception {
        // Arrange
        Constituency invalid = new Constituency(null);
        invalid.setId(999);
        when(constituencyService.updateConstituency(any(Constituency.class)))
                .thenThrow(new ConstituencyNotFoundException(invalid.getId()));


        // Act & Assert
        mockMvc.perform(put("/v1/constituencies/{id}", invalid.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalid)))
                .andExpect(status().isBadRequest());
    }

    /**
     * Ensures that a delete request for a valid ID returns HTTP 204 (No Content).
     * <p>
     * DELETE /v1/constituencies/{id}
     */
    @Test
    void shouldReturn204_whenDeleteConstituency() throws Exception {
        // Arrange
        int constituencyId = 1;

        // Act & Assert
        mockMvc.perform(delete("/v1/constituencies/{id}", constituencyId))
                .andExpect(status().isNoContent());
    }

    /**
     * Ensures that trying to delete a non-existing constituency results in HTTP 404.
     * <p>
     * DELETE /v1/constituencies/{id}
     */
    @Test
    void shouldReturn404_whenDeleteConstituencyNotFound() throws Exception {
        // Arrange
        int constituencyId = 999;
        doThrow(new ConstituencyNotFoundException(constituencyId))
                .when(constituencyService).deleteConstituency(999);

        // Act & Assert
        mockMvc.perform(delete("/v1/constituencies/{id}", constituencyId))
                .andExpect(status().isNotFound());
    }

}
