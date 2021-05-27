package org.springboot.training.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.HashSet;
import java.util.Set;

//TODO Migrate to Java Records
@Table("books")
public class Book {
    @Id
    private Long id;
    private String title;
    private String description;

    @MappedCollection(idColumn = "book_id")
    private Set<AuthorRef> authors = new HashSet<>();

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

    public Set<AuthorRef> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<AuthorRef> authors) {
        this.authors = authors;
    }
}
