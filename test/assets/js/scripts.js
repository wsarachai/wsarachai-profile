"use strict";

let btnDown = {};
let siteNavigator = {};

document.addEventListener("DOMContentLoaded", function () {
  btnDown = document.getElementById("btn-down");
  siteNavigator = document.getElementById("site-navigator");

  btnDown.addEventListener("click", function () {
    if (siteNavigator.style.display === "") {
      siteNavigator.style.display = "block";
    } else {
      siteNavigator.style.display = "";
    }
  });
});
