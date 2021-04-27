package org.springboot.training.model;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class BookAuthors implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String title;
    private String description;
    private List<AuthorRef> authors;

    public BookAuthors() {
    }

    public BookAuthors(final Long id, final String title, final String description, final List<AuthorRef> authors) {
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

    public List<AuthorRef> getAuthors() {
        return authors;
    }

    public void setAuthors(List<AuthorRef> authors) {
        this.authors = authors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookAuthors that = (BookAuthors) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(title, that.title) &&
                Objects.equals(description, that.description) &&
                Objects.equals(authors, that.authors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, authors);
    }

    @Override
    public String toString() {
        return "BookAuthors{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", authors=" + authors +
                '}';
    }
}
