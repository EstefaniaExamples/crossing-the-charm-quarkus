const express = require("express");
const router = express.Router();
const Book = require('../services');

router.get('/books', async (req, res) => {
    try {
        let books = await new Book().getAllBooks();
        return res.send(books);
    } catch (err) {
        console.error(`Error while posting quotes `, err.message);
        next(err);
    }
});

router.get('/books/:id', async (req, res) => {
    try {
        const id = req.params.id;
        let book = await new Book().getBookById(id);
        return res.send(book);
    } catch (err) {
        console.error(`Error while posting quotes `, err.message);
        next(err);
    }
});


module.exports = router;