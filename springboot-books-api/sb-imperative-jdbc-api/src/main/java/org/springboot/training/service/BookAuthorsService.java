package org.springboot.training.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springboot.training.model.BookAuthorDto;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookAuthorsService {
    private static final String FIND_ALL_BOOKS = "SELECT b.id, b.title, b.description, " +
            "( SELECT json_agg(json_build_object('id', a.id, 'name', a.name, 'surname', a.surname)) " +
            "  FROM authors a JOIN books_authors ba ON a.id=ba.author_id " +
            "  WHERE b.id=ba.book_id ) as authors" +
            "  FROM books b";
    private static final String FIND_BOOK_BY_ID = "SELECT b.id as id, b.title as title, b.description as description, " +
            "( SELECT json_agg(json_build_object('id', a.id, 'name', a.name, 'surname', a.surname)) " +
            "  FROM authors a JOIN books_authors ba ON a.id=ba.author_id " +
            "  WHERE b.id=ba.book_id ) as authors" +
            "  FROM books b" +
            "  WHERE b.id = ?" ;

    private final JdbcTemplate jdbcTemplate;
    private final ObjectMapper objectMapper;

    public BookAuthorsService(final JdbcTemplate jdbcTemplate,final ObjectMapper objectMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.objectMapper = objectMapper;
    }

    public List<BookAuthorDto> findAll(){
        return jdbcTemplate.query(FIND_ALL_BOOKS, new BookAuthorMapper(objectMapper));
    }

    public BookAuthorDto findBookById(final Long bookId){
        try {
            return jdbcTemplate.queryForObject(FIND_BOOK_BY_ID, new BookAuthorMapper(objectMapper), bookId);
        } catch (final EmptyResultDataAccessException ex) {
            return null;
        }
    }
}
