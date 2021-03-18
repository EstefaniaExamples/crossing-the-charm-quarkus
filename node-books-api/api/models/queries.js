const Pool = require('pg').Pool

// docker run --name books-postgres -e POSTGRES_USER=book -e POSTGRES_PASSWORD=book -e POSTGRES_DB=books_database -d -p 5432:5432 postgres
// psql -U book books_database

const pool = new Pool({
  user: 'book',
  host: 'localhost',
  database: 'books_database',
  password: 'book',
  port: 5432,
})

const getBooks = (request, response) => {
  pool.query('SELECT * FROM book ORDER BY id ASC', (error, results) => {
      if (error) {
      throw error
      }
      response.status(200).json(results.rows)
  })
}

const getBookById = (request, response) => {
  const id = parseInt(request.params.id)
  pool.query('SELECT * FROM book WHERE id = $1', [id], (error, results) => {
      if (error) {
      throw error
      }
      response.status(200).json(results.rows)
  })
}

const createBook = (request, response) => {
  const { title, author, description } = request.body

  pool.query('INSERT INTO book (title, author, description) VALUES ($1, $2, $3) RETURNING id', [title, author, description], (error, results) => {
      if (error) {
      throw error
      }
      response.status(201).send(`Book added with ID: ${results.id}`)
  })
}

const updateBook = (request, response) => {
  const id = parseInt(request.params.id)
  const { name, email, description } = request.body

  pool.query(
    'UPDATE book SET title = $1, description = $2, author = $3 WHERE id = $4',
    [name, email, description, id],
    (error, results) => {
      if (error) {
        throw error
      }
      response.status(200).send(`Book modified with ID: ${id}`)
    }
  )
}

const deleteBook = (request, response) => {
  const id = parseInt(request.params.id)

  pool.query('DELETE FROM book WHERE id = $1', [id], (error, results) => {
    if (error) {
      throw error
    }
    response.status(200).send(`Book deleted with ID: ${id}`)
  })
}

module.exports = {
  getBooks,
  getBookById,
  createBook,
  updateBook,
  deleteBook,
}