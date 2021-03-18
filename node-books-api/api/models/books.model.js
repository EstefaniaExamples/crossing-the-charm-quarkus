const { sequelize, Sequelize } = require(".");

module.exports = (sequelize, Sequelize) => {
  const Book = sequelize.define("book", {
    title: {
      type: Sequelize.STRING(255),
    },
    description: {
      type: Sequelize.STRING(500),
    },
    author: {
      type: Sequelize.STRING(255),
    },
  }, { timestamps: false });
  return Book;
};