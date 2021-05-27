package com.quarkus.training.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name="books")
public class Book {
    @Id
    public Long id;
    public String title;
    public String description;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "books_authors",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    @JsonManagedReference
    public Set<Author> authors = new HashSet<>();
}