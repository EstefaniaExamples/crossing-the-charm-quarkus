package org.springboot.training.service;

import org.springboot.training.model.Book;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Service
public class BookAuthorsService {
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

    @PersistenceContext
    private EntityManager entityManager;

    public List<Book> getAllBooks() {
        return entityManager.createNativeQuery(FIND_ALL, Book.class).getResultList();
    }


    public Book getBookById(final Long id) {
        final Query query =  entityManager.createNativeQuery(FIND_BY_ID, Book.class);
        query.setParameter(1, id);
        return (Book) query.getSingleResult();
    }
}
