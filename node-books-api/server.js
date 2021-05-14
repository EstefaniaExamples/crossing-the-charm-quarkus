var express = require('express'),
   app = express(),
   port = process.env.PORT || 3001,
   bodyParser = require('body-parser');;

app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());

const bookRoutes = require('./api/routes');
app.use(bookRoutes);

app.use(function(req, res) {
   res.status(404).send({url: req.originalUrl + ' not found'})
 });
 
var server = app.listen(port, function () {
   var host = server.address().address
   var port = server.address().port
   console.log("Example app listening at http://%s:%s", host, port)
})