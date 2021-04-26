package com.quarkus.training;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.util.List;

@Entity(name = "authors")
public class Author extends PanacheEntity {
    public String name;
    public String surname;
    public Character sex;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "author")
    public List<Book> book;
}
