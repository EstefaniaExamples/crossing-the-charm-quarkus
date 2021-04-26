package org.springboot.training.model;

import java.util.List;

public class BookAuthor {
    private Long id;
    private String title;
    private String description;
    private List<InnerAuthor> authors;

    public BookAuthor() {
    }

    public BookAuthor(final Long id, final String title, final String description, final List<InnerAuthor> authors) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.authors = authors;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<InnerAuthor> getAuthors() {
        return authors;
    }

    public void setAuthors(List<InnerAuthor> authors) {
        this.authors = authors;
    }

    public static class InnerAuthor {
        private Long authorId;
        private String fullName;

        public InnerAuthor() {
        }

        public InnerAuthor(Long authorId, String fullName) {
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
}
