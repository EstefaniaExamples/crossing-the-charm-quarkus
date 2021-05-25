package org.springboot.training.persistence;

import org.springboot.training.model.BookAuthorDto;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BookAuthorRepository extends CrudRepository<BookAuthorDto, Long> {

    //TODO Migrate to Text Blocks
    @Query("SELECT b.id, b.title, b.description, " +
            "( SELECT a.id as authorId" +
            "  FROM authors a JOIN books_authors ba ON a.id=ba.author_id " +
            "  WHERE b.id=ba.book_id ) as authors" +
            "  FROM books b")
    List<BookAuthorDto> findAll();
}
