package org.springboot.training.repositories;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.r2dbc.spi.Row;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springboot.training.model.BookAuthor;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

public class BookMapper implements BiFunction<Row, Object, BookAuthor> {

    @Override
    public BookAuthor apply(final Row row, final Object o) {
        final Long id = row.get("id", Long.class);
        final String bookTitle = row.get("title", String.class);
        final String bookDescription = row.get("description", String.class);

        final List<BookAuthor.InnerAuthor> authors = new ArrayList<>();
        if (row.get("authors", String.class) != null) {
            final JSONArray array = new JSONArray(row.get("authors", String.class));
            array.forEach(item ->
                    authors.add(new ObjectMapper().convertValue(((JSONObject) item).toMap(), BookAuthor.InnerAuthor.class))
            );

        }

        return new BookAuthor(id, bookTitle, bookDescription, authors);
    }
}
