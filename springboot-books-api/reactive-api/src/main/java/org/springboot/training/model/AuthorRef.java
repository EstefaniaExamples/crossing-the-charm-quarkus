package org.springboot.training.model;

import java.io.Serializable;
import java.util.Objects;

public class AuthorRef implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long authorId;
    private String fullName;

    public AuthorRef() {
    }

    public AuthorRef(final Long authorId, final String fullName) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthorRef authorRef = (AuthorRef) o;
        return Objects.equals(authorId, authorRef.authorId) &&
                Objects.equals(fullName, authorRef.fullName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(authorId, fullName);
    }

    @Override
    public String toString() {
        return "AuthorRef{" +
                "authorId=" + authorId +
                ", fullName='" + fullName + '\'' +
                '}';
    }
}
