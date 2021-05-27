package org.springboot.training.persistence;

import org.springboot.training.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    @Modifying
    @Query(value = "SELECT b.id as id, b.title as title, b.description as description, " +
            "( SELECT json_agg(json_build_object('authorId', a.id, 'fullName', concat_ws(' ', a.name, a.surname))) " +
            "  FROM authors a JOIN books_authors ba ON a.id=ba.author_id " +
            "  WHERE b.id=ba.book_id ) as authors" +
            "  FROM books b",
            nativeQuery = true)
    List<Book> findAll();
}
