'use strict';

const filterCourses = function() {
    let input, filter, courseContainer, courseItems, courseHeader, i, txtValue;
    input = document.getElementById('searchInput');
    filter = input.value.toUpperCase();
    courseContainer = document.getElementById('course-container');
    courseItems = courseContainer.getElementsByClassName('card');

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