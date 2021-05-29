package org.springboot.training.persistence;

import org.springboot.training.model.Book;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BookRepository extends CrudRepository<Book, Long> {
    @Query(value = "SELECT b.id as id, b.title as title, b.description as description, " +
            "( SELECT json_agg(json_build_object('id', a.id, 'name', a.name, 'surname', a.surname)) " +
            "  FROM authors a JOIN books_authors ba ON a.id=ba.author_id " +
            "  WHERE b.id=ba.book_id ) as authors" +
            "  FROM books b")
    List<Book> nativeFindAll();

    @Query(value = "SELECT b.id as id, b.title as title, b.description as description, " +
            "( SELECT json_agg(json_build_object('id', a.id, 'name', a.name, 'surname', a.surname)) " +
            "  FROM authors a JOIN books_authors ba ON a.id=ba.author_id " +
            "  WHERE b.id=ba.book_id ) as authors" +
            "  FROM books b" +
            "  WHERE b.id=:bookId" )
    Book nativeFindById(final Long id);
}
