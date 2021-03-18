module.exports = function(app) {
  const controller = require("../controllers");

  app.route('/books')
    .get(controller.findAll)
    .post(controller.create);


  app.route('/books/:id')
    .get(controller.findOne)
    .put(controller.update)
    .delete(controller.delete);
};