package com.quarkus.training.persistence;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quarkus.training.model.Author;
import com.quarkus.training.model.Book;

import javax.enterprise.context.ApplicationScoped;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

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
            "  WHERE b.id=?";

    private final DataSource dataSource;

    public BookAuthorsRepository(final DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Book> findAll() {
        final List<Book> results = new ArrayList<>();
        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement statement = connection.prepareStatement(FIND_ALL)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    final Set<Author> authors = mountAuthorSet(resultSet);
                    results.add(new Book(
                            resultSet.getLong("id"),
                            resultSet.getString("title"),
                            resultSet.getString("description"),
                            authors));
                }
            }

        } catch (final SQLException e) {
            e.printStackTrace();
        }

        return results;
    }

    public Optional<Book> findById(final Long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_ID)) {
            statement.setObject(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    final Set<Author> authors = mountAuthorSet(resultSet);
                    return Optional.of(new Book(
                            resultSet.getLong("id"),
                            resultSet.getString("title"),
                            resultSet.getString("description"),
                            authors));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    private Set<Author> mountAuthorSet(final ResultSet resultSet) throws SQLException {
        try {
            final ObjectMapper objectMapper = new ObjectMapper();
            final List<Author> array = objectMapper.readValue(resultSet.getObject("authors").toString(),
                    new TypeReference<>() {
                    });
            return new HashSet<>(array);

        } catch (final JsonProcessingException e) {
            e.printStackTrace();
        }

        return new HashSet<>();
    }
}
