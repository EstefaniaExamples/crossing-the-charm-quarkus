module.exports = function(app) {
  const db = require('../models/queries')

  app.route('/users')
    .get(db.getUsers)
    .post(db.createUser);


  app.route('/users/:id')
    .get(db.getUserById)
    .put(db.updateUser)
    .delete(db.deleteUser);
};