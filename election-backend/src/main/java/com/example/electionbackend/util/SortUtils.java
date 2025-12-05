package com.example.electionbackend.util;

import com.example.electionbackend.dto.RegionDTO;
import com.example.electionbackend.exception.InvalidSortPropertyException;
import com.example.electionbackend.model.PartyVote;
import org.springframework.data.domain.Sort;

import java.util.Comparator;
import java.util.List;

/**
 * Utility class for sorting-related operations.
 */
public class SortUtils {
    /**
     * Private constructor to prevent instantiation.
     */
    private SortUtils() {
    }

    /**
     * Sorts a list of RegionDTOs based on the provided Sort object.
     *
     * @param dtos - the list of RegionDTOs to sort
     * @param sort - the Sort object containing sorting information
     * @return - the sorted list of RegionDTOs
     */
    public static List<RegionDTO> sortRegion(List<RegionDTO> dtos, Sort sort) {
        for (Sort.Order order : sort) {
            Comparator<RegionDTO> comparator = switch (order.getProperty()) {
                case "name" -> Comparator.comparing(RegionDTO::getName);
                case "id" -> Comparator.comparing(RegionDTO::getId);
                default -> throw new InvalidSortPropertyException(order.getProperty());
            };

            // Reverse if descending
            if (order.isDescending()) {
                comparator = comparator.reversed();
            }

            return dtos.stream().sorted(comparator).toList();
        }

        // If no sort orders are provided, return the list as is
        return dtos;
    }

    /**
     * Sorts a list of PartyVote objects based on the provided Sort object.
     *
     * @param votes - the list of PartyVote objects to sort
     * @param sort  - the Sort object containing sorting information
     * @return - the sorted list of PartyVote objects
     */
    public static List<PartyVote> sortPartyVotes(List<PartyVote> votes, Sort sort) {
        for (Sort.Order order : sort) {
            Comparator<PartyVote> comparator = switch (order.getProperty()) {
                case "votes" -> Comparator.comparing(PartyVote::getVotes);
                default -> throw new InvalidSortPropertyException(order.getProperty());
            };

            // Reverse if descending
            if (order.isDescending()) {
                comparator = comparator.reversed();
            }

            return votes.stream().sorted(comparator).toList();
        }

        // If no sort orders are provided, return the list as is
        return votes;
    }
}

