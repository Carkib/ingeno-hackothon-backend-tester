var express = require('express');
var fs = require('fs');
var app = express();
var path = require('path');

app.use(express.static(path.join(__dirname, 'public')));

app.use(function (req, res, next) {
    res.header("Access-Control-Allow-Origin", "*");
    res.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
    next();
});

function readJSONFile(filename, callback) {
    fs.readFile(filename, function (err, data) {
        if (err) {
            callback(err);
            return;
        }
        try {
            callback(null, JSON.parse(data));
        } catch (exception) {
            callback(exception);
        }
    });
}

app.get('/results.json', function (req, res) {
    readJSONFile('../results.json', function (err, json) {
        if (err) {
            throw err;
        }
        res.send(json)
    });
});


app.listen(3000);
