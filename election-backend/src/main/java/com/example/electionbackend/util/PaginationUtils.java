package com.example.electionbackend.util;

import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Utility class for pagination-related operations.
 */
public class PaginationUtils {
    /**
     * Private constructor to prevent instantiation.
     */
    private PaginationUtils() {}

    /**
     * Paginates a list of items based on the provided Pageable object.
     *
     * @param items    the list of items to paginate
     * @param pageable the Pageable object containing pagination information
     * @param <T>      the type of items in the list
     * @return a sublist representing the requested page
     */
    public static <T> List<T> paginate(List<T> items, Pageable pageable) {
        int page = pageable.getPageNumber();
        int size = pageable.getPageSize();

        int from = Math.min(page * size, items.size());
        int to = Math.min(from + size, items.size());

        return items.subList(from, to);
    }
}
