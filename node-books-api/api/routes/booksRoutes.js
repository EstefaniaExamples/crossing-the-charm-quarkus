module.exports = function(app) {
  const db = require('../models/queries')

  app.route('/books')
    .get(db.getBooks)
    .post(db.createBook);


  app.route('/books/:id')
    .get(db.getBookById)
    .put(db.updateBook)
    .delete(db.deleteBook);
};