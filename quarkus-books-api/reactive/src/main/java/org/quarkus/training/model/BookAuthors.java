package org.quarkus.training.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.core.json.JsonArray;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;
import io.vertx.mutiny.sqlclient.Tuple;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BookAuthors {
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

    @Override
    public String toString() {
        return "BookAuthors{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", authors=" + authors +
                '}';
    }

    public static class InnerAuthor {
        @JsonProperty
        private Long authorId;
        @JsonProperty
        private String fullName;

        public InnerAuthor() {
        }

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

        @Override
        public String toString() {
            return "InnerAuthor{" +
                    "authorId=" + authorId +
                    ", fullName='" + fullName + '\'' +
                    '}';
        }
    }

    public BookAuthors(final Long id, final String title, final String description, final List<InnerAuthor> authors) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.authors = authors;
    }





    // The from method converts a Row instance to a Fruit instance.
    // It is extracted as a convenience for the implementation of the other data management methods:
    private static BookAuthors from(final Row row) {
        final ObjectMapper objectMapper = new ObjectMapper();
        final List<InnerAuthor> authors = new ArrayList<>();
        final JsonArray array = (JsonArray) row.getValue("authors");
        array.forEach(item -> {
                    try {
                        authors.add(objectMapper.readValue(item.toString(), InnerAuthor.class));
                    } catch (JsonProcessingException e) {
                        authors.add(new InnerAuthor());
                    }
                }
        );

        return new BookAuthors(row.getLong("id"),
                row.getString("title"),
                row.getString("description"),
                authors);
    }

    public static Multi<BookAuthors> findAll(final PgPool client) {
        return client.query("SELECT b.id, b.title, b.description, " +
                "( SELECT json_agg(json_build_object('authorId', a.id, 'fullName', concat_ws(' ', a.name, a.surname))) " +
                "  FROM authors a JOIN books_authors ba ON a.id=ba.author_id " +
                "  WHERE b.id=ba.book_id ) as authors" +
                "  FROM books b;")
                .execute()
                .onItem().transformToMulti(set -> Multi.createFrom().iterable(set))
                .onItem().transform(BookAuthors::from);
    }

    public static Uni<BookAuthors> findById(final PgPool client, final Long id) {
        return client.preparedQuery("SELECT b.id, b.title, b.description, " +
                "( SELECT json_agg(json_build_object('authorId', a.id, 'fullName', concat_ws(' ', a.name, a.surname))) " +
                "  FROM authors a JOIN books_authors ba ON a.id=ba.author_id " +
                "  WHERE b.id=ba.book_id ) as authors" +
                "  FROM books b " +
                "  WHERE b.id=$1")
                .execute(Tuple.of(id))
                .onItem().transform(RowSet::iterator)
                .onItem().transform(iterator -> iterator.hasNext() ? from(iterator.next()) : null);
    }
}
