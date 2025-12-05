package com.example.electionbackend.dto;

/**
 * Data Transfer Object for Post information.
 */
public class PostDTO {
    private int id;
    private int userId;
    private String title;
    private String content;
    private String createdAt;

    /**
     * Default constructor for PostDTO
     */
    public PostDTO() {
    }

    /**
     * Constructor for PostDTO
     *
     * @param id        - the id of the post
     * @param title     - the title of the post
     * @param content   - the content of the post
     * @param createdAt - the creation timestamp of the post
     */
    public PostDTO(int id, int userId, String title, String content, String createdAt) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
    }

    /* --- Getters and Setters --- */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
