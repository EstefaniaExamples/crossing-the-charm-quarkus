package org.springboot.training.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springboot.training.model.Author;
import org.springboot.training.model.BookAuthorDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookAuthorMapper implements RowMapper<BookAuthorDto> {
    private final ObjectMapper objectMapper;

    public BookAuthorMapper(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public BookAuthorDto mapRow(final ResultSet resultSet, int i) throws SQLException {
        List<Author> authors = new ArrayList<>();
        try {
            authors = objectMapper.readValue(
                    resultSet.getObject("authors").toString(), new TypeReference<>() {});
        } catch (final JsonProcessingException e) {
            e.printStackTrace();
        }

        return new BookAuthorDto(
                resultSet.getLong("id"),
                resultSet.getString("title"),
                resultSet.getString("description"),
                authors);
    }
}

