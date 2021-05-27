package org.springboot.training.model;

import java.util.List;

public class BookAuthorDto {
    private Long id;
    private String title;
    private String description;
    private List<CustomAuthorRef> authors;

    public BookAuthorDto() {
    }

    public BookAuthorDto(Long id, String title, String description, List<CustomAuthorRef> authors) {
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

    public List<CustomAuthorRef> getAuthors() {
        return authors;
    }

    public void setAuthors(List<CustomAuthorRef> authors) {
        this.authors = authors;
    }
}
