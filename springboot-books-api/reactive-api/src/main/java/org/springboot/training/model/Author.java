package org.springboot.training.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("authors")
public class Author {
    @Id
    public Long id;
    public String name;
    public String surname;
    public Character sex;
}
