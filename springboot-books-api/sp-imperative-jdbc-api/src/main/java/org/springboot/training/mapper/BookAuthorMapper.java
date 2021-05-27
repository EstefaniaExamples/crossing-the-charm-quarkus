package org.springboot.training.mapper;


import org.springboot.training.model.BookAuthorDto;
import org.springboot.training.model.CustomAuthorRef;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class BookAuthorMapper implements RowMapper<BookAuthorDto> {
    @Override
    public BookAuthorDto mapRow(final ResultSet resultSet, int i) throws SQLException {
        return new BookAuthorDto(
                resultSet.getLong("id"),
                resultSet.getString("title"),
                resultSet.getString("description"),
                List.of(CustomAuthorRef.from(resultSet.getObject("authors"))));
    }
}
