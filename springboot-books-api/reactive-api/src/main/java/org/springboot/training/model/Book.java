package org.springboot.training.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("books")
public class Book {
    @Id
    public Long id;
    public String title;
    public String description;

    public Book() {
    }

    public Book(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public Book(Long id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }
}
