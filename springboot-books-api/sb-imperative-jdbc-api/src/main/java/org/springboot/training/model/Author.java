package org.springboot.training.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

//TODO Migrate to Java Records
@Table("authors")
public class Author {
    @Id
    private Long id;
    private String name;
    private String surname;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
