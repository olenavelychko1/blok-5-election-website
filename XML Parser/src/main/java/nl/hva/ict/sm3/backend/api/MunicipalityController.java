package nl.hva.ict.sm3.backend.api;

import nl.hva.ict.sm3.backend.model.Constituency;
import nl.hva.ict.sm3.backend.model.Election;
import nl.hva.ict.sm3.backend.model.Municipality;
import nl.hva.ict.sm3.backend.service.MunicipalityService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Municipality controller class
 */
@RestController
@RequestMapping("municipality")
public class MunicipalityController {
    private final MunicipalityService municipalityService;

    /**
     * Constructor for MunicipalityController
     * @param municipalityService - the municipality service
     */
    public MunicipalityController(MunicipalityService municipalityService) {
        this.municipalityService = municipalityService;
    }

    /**
     * Get party votes for a specific municipality and year
     * @param municipality - the name of the municipality
     * @param year - the year of the election, defaults to 2023
     * @return - a list of PartyVote objects
     */
    @GetMapping
    public Election getMunicipalityVotes(@RequestParam(name = "municipality", required = false) String municipality,
                                               @RequestParam(defaultValue = "2023") int year) {
        return municipalityService.getPartyVotes(municipality, year);
    }

}