package nl.hva.ict.sm3.backend.api;

import nl.hva.ict.sm3.backend.model.*;
import nl.hva.ict.sm3.backend.service.NationalService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller class for retrieving the national party results of an election.
 */
@RestController
@RequestMapping("national-results")
public class NationalController {
    private final NationalService nationalService;

    /**
     * Constructor that sets the national party result service.
     * @param nationalService service handling national election results
     */
    public NationalController(NationalService nationalService) {
        this.nationalService = nationalService;
    }

    /**
     * Retrieves the national results (votes per party) for a given election year.
     *
     * @param year the year of the election
     * @return List of parties with their national vote counts
     */
    @GetMapping
    public Election getNationalResults(@RequestParam int year) {
        return nationalService.getNationalPartyResults(year);
    }
}
