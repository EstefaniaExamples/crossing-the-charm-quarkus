package org.quarkus.training;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;
import io.vertx.mutiny.sqlclient.Tuple;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Book {
    @JsonProperty
    private Long id;
    @JsonProperty
    private String title;
    @JsonProperty
    private String description;
    @JsonProperty
    private List<InnerAuthor> authors;

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<InnerAuthor> getAuthors() {
        return authors;
    }

    public static class InnerAuthor {
        @JsonProperty
        private Long authorId;
        @JsonProperty
        private String fullName;

        public InnerAuthor(final Long authorId, final String fullName) {
            this.authorId = authorId;
            this.fullName = fullName;
        }

        public Long getAuthorId() {
            return authorId;
        }

        public String getFullName() {
            return fullName;
        }
    }

    public Book(final Long id, final String title, final String description, final List<InnerAuthor> authors) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.authors = authors;
    }

    // The from method converts a Row instance to a Fruit instance.
    // It is extracted as a convenience for the implementation of the other data management methods:
    private static Book from(final Row row) {

        final List<InnerAuthor> authors = new ArrayList<>();
        if (row.getString("authors") != null) {
            final JSONArray array = new JSONArray( row.getString("authors"));
            array.forEach(item ->
                    authors.add(new ObjectMapper().convertValue(((JSONObject) item).toMap(), InnerAuthor.class))
            );

        }

        return new Book(row.getLong("id"),
                row.getString("title"),
                row.getString("description"),
                authors);
    }

    public static Multi<Book> findAll(final PgPool client) {
        return client.query("SELECT b.id, b.title, b.description, " +
                "( SELECT json_agg(json_build_object('authorId', a.id, 'fullName', concat_ws(' ', a.name, a.surname))) " +
                "  FROM authors a JOIN books_authors ba ON a.id=ba.author_id " +
                "  WHERE b.id=ba.book_id ) as authors" +
                "  FROM books b;")
                .execute()
                .onItem().transformToMulti(set -> Multi.createFrom().iterable(set))
                .onItem().transform(Book::from);
    }

    public static Uni<Book> findById(final PgPool client, final Long id) {
        return client.preparedQuery("SELECT id, title, description, author FROM books WHERE id = $1")
                .execute(Tuple.of(id))
                .onItem().transform(RowSet::iterator)
                .onItem().transform(iterator -> iterator.hasNext() ? from(iterator.next()) : null);
    }

    public Uni<Long> save(final PgPool client) {
        return client.preparedQuery("INSERT INTO books (title, description, author) VALUES ($1, $2, $3) RETURNING id")
                .execute(Tuple.of(title, description, author))
                .onItem().transform(pgRowSet -> pgRowSet.iterator().next().getLong("id"));
    }

    public static Uni<Boolean> delete(final PgPool client, Long id) {
        return client.preparedQuery("DELETE FROM books WHERE id = $1")
                .execute(Tuple.of(id))
                .onItem().transform(pgRowSet -> pgRowSet.rowCount() == 1);
    }

}
