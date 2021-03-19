package org.quarkus.training;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;
import io.vertx.mutiny.sqlclient.Tuple;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Book {
    @JsonProperty
    public Long id;
    @JsonProperty
    public String title;
    @JsonProperty
    public String description;
    @JsonProperty
    public String author;

    public Book() {
    }

    public Book(final Long id, final String title, final String description, final String author) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.description = description;
    }

    // The from method converts a Row instance to a Fruit instance.
    // It is extracted as a convenience for the implementation of the other data management methods:
    private static Book from(final Row row) {
        return new Book(row.getLong("id"),
                row.getString("title"),
                row.getString("description"),
                row.getString("author"));
    }

    public static Multi<Book> findAll(final PgPool client) {
        return client.query("SELECT id, title, description, author FROM books ORDER BY title ASC")
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
        return client.preparedQuery("INSERT INTO books (id, title, description, author) VALUES ($1, $2, $3) RETURNING id")
                .execute(Tuple.of(id, title, author))
                .onItem().transform(pgRowSet -> pgRowSet.iterator().next().getLong("id"));
    }

    public static Uni<Boolean> delete(final PgPool client, Long id) {
        return client.preparedQuery("DELETE FROM books WHERE id = $1")
                .execute(Tuple.of(id))
                .onItem().transform(pgRowSet -> pgRowSet.rowCount() == 1);
    }

}
