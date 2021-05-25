package com.quarkus.training.model;

public class Author {
    private Long authorId;
    public String fullName;

    public Author() {
    }

    public Author(Long authorId, String fullName) {
        this.authorId = authorId;
        this.fullName = fullName;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
