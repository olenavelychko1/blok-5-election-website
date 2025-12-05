package com.example.electionbackend.controller.api.v1;

import com.example.electionbackend.dto.RegionDTO;
import com.example.electionbackend.interfaces.IElectionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller for handling State related endpoints.
 */
@RestController
@RequestMapping("/v1/elections")
public class ElectionController {
    private final IElectionService electionService;

   public ElectionController(IElectionService electionService) {
        this.electionService = electionService;
   }

    /**
     * Get all elections
     * @return - the list of elections, containing the election ID (TK2025) and state ID (1) for example
     */
    @GetMapping
    public ResponseEntity<List<RegionDTO>> getAll() {
            List<RegionDTO> elections = electionService.getAll();
            return ResponseEntity.ok(elections);
    }
}
