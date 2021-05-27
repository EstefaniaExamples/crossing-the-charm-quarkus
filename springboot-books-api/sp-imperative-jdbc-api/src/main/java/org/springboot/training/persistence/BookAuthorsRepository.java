package org.springboot.training.persistence;

import org.springboot.training.mapper.BookAuthorMapper;
import org.springboot.training.model.BookAuthorDto;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookAuthorsRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public BookAuthorsRepository(final NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<BookAuthorDto> findAll(){
        final String sql = "SELECT b.id, b.title, b.description, " +
                "( SELECT json_agg(json_build_object('authorId', a.id, 'fullName', concat_ws(' ', a.name, a.surname))) " +
                "  FROM authors a JOIN books_authors ba ON a.id=ba.author_id " +
                "  WHERE b.id=ba.book_id ) as authors" +
                "  FROM books b";

        return jdbcTemplate.query(sql, new MapSqlParameterSource(), new BookAuthorMapper());
    }
}
