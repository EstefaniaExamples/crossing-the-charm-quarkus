const db = require("../models");
const Books = db.books;
const Op = db.Sequelize.Op;

exports.create = (req, res) => {
  if (!req.body.title || !req.body.author || !req.body.description) {
    res.status(400).send({
      message: "Content can not be empty !",
    });
    return;
  }

  const book = {
    title: req.body.title,
    description: req.body.description,
    author: req.body.author,
  };

  Books.create(book)
    .then((data) => {
      res.send(data);
    })
    .catch((err) => {
      res.status(500).send({
        message: err.message || "Some error occurred while create the Notes",
      });
    });
};

exports.findAll = (req, res) => {
  const title = req.query.title;

  Books.findAll()
    .then((data) => {
      res.send(data);
    })
    .catch((err) => {
      res.status(500).send({
        message: err.message || "Some error occured while retrieving Notes",
      });
    });
};

exports.findOne = (req, res) => {
  const id = req.params.id;
  Books.findByPk(id)
    .then((data) => {
      res.send(data);
    })
    .catch((err) => {
      res.status(500).send({
        message: "Error retrieving Notes with id=" + id,
      });
    });
};

exports.update = (req, res) => {
  const id = req.params.id;

  Books.update(req.body, {
    where: { id: id },
  }).then((data) => {
    if (data) {
      res.send({
        message: "Note was updated successfully",
      });
    } else {
      res.send({
        message: `Cannot update Note with id=${id}`,
      });
    }
  });
};

exports.delete = (req, res) => {
  const id = req.params.id;

  Books.destroy({
    where: { id: id },
  }).then((data) => {
    if (data) {
      res.send({
        message: "Book was delete successfully!",
      });
    } else {
      res.send({
        message: `Cannot delete Note with id=${id}`,
      });
    }
  });
};