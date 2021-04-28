package org.springboot.training.datasource;

import io.r2dbc.spi.Row;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springboot.training.model.AuthorRef;
import org.springboot.training.model.BookAuthors;
import org.springframework.core.convert.converter.Converter;

import java.util.List;

public class BookAuthorsReadConverter implements Converter<Row, BookAuthors> {
    private static Logger LOG = LoggerFactory.getLogger(BookAuthorsReadConverter.class);

    @Override
    public BookAuthors convert(Row row) {
        final Long id = row.get("id", Long.class);
        final String bookTitle = row.get("title", String.class);
        final String bookDescription = row.get("description", String.class);

        final List<AuthorRef> authors = new java.util.ArrayList<>();

        final JSONArray array = new JSONArray(row.get("authors", String.class));
        array.forEach(item -> {
                    final JSONObject jsonObject = (JSONObject) item;
                    final AuthorRef authorRef = new AuthorRef(Long.parseLong(jsonObject.get("authorId").toString()),
                            jsonObject.get("fullName").toString());
                    authors.add(authorRef);
                }
        );

        LOG.debug("Book with authors: {}", new BookAuthors(id, bookTitle, bookDescription, authors));
        return new BookAuthors(id, bookTitle, bookDescription, authors);
    }
}
