package com.quarkus.training;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "authors")
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Author extends PanacheEntity {
    public String name;
    public String surname;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "authors")
    @JsonBackReference
    public Set<Book> books = new HashSet<>();
}
