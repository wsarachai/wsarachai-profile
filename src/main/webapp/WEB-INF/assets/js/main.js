'use strict';

let body = {};
let btnDown = {};
let siteNavigator = {};

const dropButton = function(event) {
    if (siteNavigator.style.display === "") {
        siteNavigator.style.display = "block";
    } else {
        siteNavigator.style.display = "";
    }
    event.stopPropagation();
}

const closeNavigator = function() {
    siteNavigator.style.display = "";
}

const history_back = function() {
    window.history.back();
}

document.addEventListener("DOMContentLoaded", function () {
    body = document.getElementsByTagName("body")[0];
    btnDown = document.getElementById("drop-btn");
    siteNavigator = document.getElementById("site-navigator");

    siteNavigator.addEventListener("click", function(event) { event.stopPropagation(); });
    btnDown.addEventListener("click", dropButton, true);
    body.addEventListener("click", closeNavigator);

    history.pushState(null, null, document.URL);
    window.addEventListener('popstate', function () {
        history.pushState(null, null, document.URL);
    });
});