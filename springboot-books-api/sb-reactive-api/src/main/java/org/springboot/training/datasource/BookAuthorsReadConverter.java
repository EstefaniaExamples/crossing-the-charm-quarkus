package org.springboot.training.datasource;

import io.r2dbc.spi.Row;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springboot.training.model.Author;
import org.springboot.training.model.Book;
import org.springframework.core.convert.converter.Converter;

import java.util.HashSet;
import java.util.Set;

public class BookAuthorsReadConverter implements Converter<Row, Book> {
    private static Logger LOG = LoggerFactory.getLogger(BookAuthorsReadConverter.class);

    @Override
    public Book convert(final Row row) {
        final Long id = row.get("id", Long.class);
        final String bookTitle = row.get("title", String.class);
        final String bookDescription = row.get("description", String.class);

        final Set<Author> authors = new HashSet<>();

        final JSONArray array = new JSONArray(row.get("authors", String.class));
        array.forEach(item -> {
                    final JSONObject jsonObject = (JSONObject) item;
                    final Author author = new Author(Long.parseLong(jsonObject.get("id").toString()),
                            jsonObject.get("name").toString(), jsonObject.get("surname").toString());
                    authors.add(author);
                }
        );

        LOG.debug("Book with authors: {}", new Book(id, bookTitle, bookDescription, authors));
        return new Book(id, bookTitle, bookDescription, authors);
    }
}
