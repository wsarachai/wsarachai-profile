'use strict';

const filterCourses = function() {
    let input, filter, courseContainer, courseItems, courseHeader, i, txtValue;
    input = document.getElementById('searchInput');
    filter = input.value.toUpperCase();
    courseContainer = document.querySelector('.course-container .row');
    courseItems = courseContainer.getElementsByClassName('course-item');

    for (i = 0; i < courseItems.length; i++) {
        courseHeader = courseItems[i].getElementsByClassName('course-header')[0];
        txtValue = courseHeader.textContent || courseHeader.innerText;
        if (txtValue.toUpperCase().indexOf(filter) > -1) {
            courseItems[i].style.display = "";
        } else {
            courseItems[i].style.display = "none";
        }
    }
}

let btnDown = {};
let siteNavigator = {};

const dropButton = function() {
    if (siteNavigator.style.display === "") {
        siteNavigator.style.display = "block";
    } else {
        siteNavigator.style.display = "";
    }
}

document.addEventListener("DOMContentLoaded", function () {
    btnDown = document.getElementById("drop-btn");
    siteNavigator = document.getElementById("site-navigator");

    btnDown.addEventListener("click", dropButton);
});