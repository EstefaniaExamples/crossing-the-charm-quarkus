package org.springboot.training.model;

import org.springframework.data.relational.core.mapping.Table;

//TODO Migrate to Java Records
@Table("books_authors")
public class AuthorRef {
    private Long authorId;

    public AuthorRef(Long authorId) {
        this.authorId = authorId;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }
}
