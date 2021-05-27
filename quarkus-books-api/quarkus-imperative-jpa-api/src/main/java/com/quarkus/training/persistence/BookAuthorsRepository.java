package com.quarkus.training.persistence;

import com.quarkus.training.model.Book;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class BookAuthorsRepository {
    private static final String FIND_ALL = "SELECT b.id, b.title, b.description, " +
            "( SELECT json_agg(json_build_object('id', a.id, 'name', a.name, 'surname', a.surname)) " +
            "  FROM authors a JOIN books_authors ba ON a.id=ba.author_id " +
            "  WHERE b.id=ba.book_id ) as authors" +
            "  FROM books b";
    private static final String FIND_BY_ID = "SELECT b.id, b.title, b.description, " +
            "( SELECT json_agg(json_build_object('id', a.id, 'name', a.name, 'surname', a.surname)) " +
            "  FROM authors a JOIN books_authors ba ON a.id=ba.author_id " +
            "  WHERE b.id=ba.book_id ) as authors" +
            "  FROM books b" +
            "  WHERE b.id=(?1)";

    private final EntityManager entityManager;

    public BookAuthorsRepository(final EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    public List<Book> findAll() {
        return (List<Book>) entityManager.createNativeQuery(FIND_ALL, Book.class).getResultList();
    }

    public Optional<Book> findById(final Long id) {
        final Query query =  entityManager.createNativeQuery(FIND_BY_ID, Book.class);
        query.setParameter(1, id);
        return Optional.of((Book) query.getSingleResult());
    }
}
