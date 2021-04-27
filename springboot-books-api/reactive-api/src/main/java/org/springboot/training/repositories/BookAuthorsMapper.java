package org.springboot.training.repositories;

import io.r2dbc.spi.Row;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springboot.training.model.AuthorRef;
import org.springboot.training.model.BookAuthors;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

public class BookAuthorsMapper implements BiFunction<Row, Object, BookAuthors> {

    @Override
    public BookAuthors apply(final Row row, final Object o) {
        return convertBookAuthors(row);
    }

    private BookAuthors convertBookAuthors(final Row row) {
        final Long id = row.get("id", Long.class);
        final String bookTitle = row.get("title", String.class);
        final String bookDescription = row.get("description", String.class);

        final List<AuthorRef> authors = new ArrayList<>();

        final JSONArray array = new JSONArray(row.get("authors", String.class));
        array.forEach(item -> {
                    final JSONObject jsonObject = (JSONObject) item;
                    final AuthorRef authorRef = new AuthorRef(Long.parseLong(jsonObject.get("authorId").toString()),
                            jsonObject.get("fullName").toString());
                    authors.add(authorRef);
                }
        );

        return new BookAuthors(id, bookTitle, bookDescription, authors);
    }
}
