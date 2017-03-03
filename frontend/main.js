import React from "react";
import ReactDOM from "react-dom";
import App from "./App.jsx";
var JSONViewer = require('react-json-viewer');

function readTextFile(file, callback) {
    var rawFile = new XMLHttpRequest();
    rawFile.overrideMimeType("application/json");
    rawFile.open("GET", file, true);
    rawFile.onreadystatechange = function () {
        if (rawFile.readyState === 4 && rawFile.status == "200") {
            callback(rawFile.responseText);
        }
    }
    rawFile.send(null);
}

setTimeout("location.reload(true);", '5000');

readTextFile("../results.json", function (text) {
    var todos = JSON.parse(text);
    // ReactDOM.render(<JSONViewer json={todos}></JSONViewer>, document.getElementById('app'));
    ReactDOM.render(<App json={todos}></App>, document.getElementById('app'));
});
