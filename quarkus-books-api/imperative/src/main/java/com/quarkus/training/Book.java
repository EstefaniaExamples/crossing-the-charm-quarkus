package com.quarkus.training;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.*;
import java.util.List;

@Entity(name = "books")
public class Book extends PanacheEntity {
    public String title;
    public String description;
    @ManyToMany
    public List<Author> author;
}