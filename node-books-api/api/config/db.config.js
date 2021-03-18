module.exports = {
  HOST: "localhost", 
  USER: "book", 
  PASSWORD: "book",
  DB: "books_database",
  dialect: "postgres",
  pool: {
    max: 5,
    min: 0,
    acquire: 30000,
    idle: 10000,
  },
};