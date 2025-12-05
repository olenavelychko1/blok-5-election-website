package com.example.electionbackend.dto;

import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Generic Page Response DTO to encapsulate paginated data.
 *
 * @param <T> the type of content in the page
 */
public class PageResponse<T> {
    private final List<T> content;
    private final long totalElements;
    private final int totalPages;
    private final int number;
    private final boolean first;
    private final boolean last;

    /**
     * Constructs a PageResponse from a Spring Data Page.
     *
     * @param page the Page object to extract data from
     */
    public PageResponse(Page<T> page) {
        this.content = page.getContent();
        this.totalElements = page.getTotalElements();
        this.totalPages = page.getTotalPages();
        this.number = page.getNumber();
        this.first = page.isFirst();
        this.last = page.isLast();
    }

    /* --- Getters --- */
    public List<T> getContent() { return content; }
    public long getTotalElements() { return totalElements; }
    public int getTotalPages() { return totalPages; }
    public int getNumber() { return number; }
    public boolean isFirst() { return first; }
    public boolean isLast() { return last; }
}