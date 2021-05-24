package org.springboot.training.persistence;

import org.springboot.training.model.Book;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {

    //@Modifying
    @Query("SELECT b.id, b.title, b.description, " +
            "( SELECT a.id as authorId" +
            "  FROM authors a JOIN books_authors ba ON a.id=ba.author_id " +
            "  WHERE b.id=ba.book_id ) as authors" +
            "  FROM books b")
    Iterable<Book> myQuery();
}
