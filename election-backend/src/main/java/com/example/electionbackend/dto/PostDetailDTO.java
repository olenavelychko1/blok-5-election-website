package com.example.electionbackend.dto;

/**
 * Data Transfer Object for detailed Post information.
 * Extends PostDTO to include additional details such as author name, comment count, and likes count.
 */
public class PostDetailDTO extends PostDTO {
    private String authorName;
    private int commentCount;
    private int likeCount;

    /**
     * Constructor for PostDetailDTO
     *
     * @param id           - the id of the post
     * @param userId       - the id of the user who created the post
     * @param title        - the title of the post
     * @param content      - the content of the post
     * @param createdAt    - the creation timestamp of the post
     * @param authorName   - the name of the author
     * @param commentCount - the number of comments on the post
     * @param likeCount   - the number of likes on the post
     */
    public PostDetailDTO(
            int id,
            int userId,
            String title,
            String content,
            String createdAt,
            String authorName,
            int commentCount,
            int likeCount
    ) {
        super(id, userId, title, content, createdAt);
        this.authorName = authorName;
        this.commentCount = commentCount;
        this.likeCount = likeCount;
    }

    /* --- Getters and Setters --- */
    public PostDetailDTO() {
        super();
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likesCount) {
        this.likeCount = likesCount;
    }
}