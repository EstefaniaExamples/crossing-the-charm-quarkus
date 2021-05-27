package org.springboot.training.model;

public class CustomAuthorRef {
    private Long authorId;
    private String fullName;

    public CustomAuthorRef() {
    }

    public CustomAuthorRef(Long authorId, String fullName) {
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

    public static CustomAuthorRef from (final Object object) {
        return new CustomAuthorRef(1L, "pepito");
    }
}
