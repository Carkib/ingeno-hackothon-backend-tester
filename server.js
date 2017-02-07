var express = require('express')
var fs = require('fs');
var app = express()


function readJSONFile(filename, callback) {
  fs.readFile(filename, function (err, data) {
    if(err) {
      callback(err);
      return;
    }
    try {
      callback(null, JSON.parse(data));
    } catch(exception) {
      callback(exception);
    }
  });
}

app.get('/', function (req, res) {
    readJSONFile('results.json', function (err, json) {
      if(err) { throw err; }
        console.log(json);
        res.send(json)
    });
})

app.listen(3000)
