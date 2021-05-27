package com.quarkus.training.model;

import java.util.HashSet;
import java.util.Set;

public class Book {
    private Long id;
    private String title;
    private String description;

    private Set<Author> authors = new HashSet<>();

    public Book() {
    }

    public Book(Long id, String title, String description, Set<Author> authors) {
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

    public Set<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }
}