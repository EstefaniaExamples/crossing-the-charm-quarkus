const db = require("../config/db.config");

class Book {
  async getAllBooks() {
    let results = await db
    .query(`select b.*,
          (
              select json_agg(json_build_object('id', a.id, 'name', a.name, 'surname', a.surname))
              from authors a
              join books_authors ba on a.id=ba.author_id
              where b.id=ba.book_id
          ) as authors
          from books b`)
    .catch(console.log);
    return results.rows;
  }

  async getBookById(bookId) {
    let results = await db
    .query(`SELECT b.id, b.title, b.description,
          (
                select json_agg(json_build_object('id', a.id, 'name', a.name, 'surname', a.surname))
                FROM authors a JOIN books_authors ba ON a.id=ba.author_id
                WHERE b.id=ba.book_id
            ) as authors
          FROM books b  WHERE b.id=$1`, [parseInt(bookId)])
    .catch(console.log);
    return results.rows;
  }
}

module.exports = Book;