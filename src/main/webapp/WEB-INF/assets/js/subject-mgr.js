'use strict';

const filterCourses = function() {
    let input, filter, subjectContainer, subjectItems, subjectHeader, i, txtValue;
    input = document.getElementById('searchInput');
    filter = input.value.toUpperCase();
    subjectContainer = document.getElementById('subject-mgr-container');
    subjectItems = subjectContainer.getElementsByClassName('card');

    for (i = 0; i < subjectItems.length; i++) {
        subjectHeader = subjectItems[i].getElementsByClassName('card-header')[0];
        txtValue = subjectHeader.textContent || subjectHeader.innerText;
        if (txtValue.toUpperCase().indexOf(filter) > -1) {
            subjectItems[i].style.display = "";
        } else {
            subjectItems[i].style.display = "none";
        }
    }
}

function onBtnEditClick(id) {
    const section = document.getElementById('subject-'+id+'-section');
    const detail = document.getElementById('subject-'+id+'-detail');

    detail.classList.add('hide');
    section.classList.remove('hide');
}

function onSaveNewSubject() {
    const url = _ctx + 'api/v1/subject/update';
    const section = document.getElementById('subject-new');
    const frm = document.getElementById('subject-new-form');

    const subjectData = JSON.stringify({
        "id": frm[0].value,
        "code": frm[1].value,
        "type": frm[2].value,
        "thaiName": frm[3].value,
        "engName": frm[4].value,
        "credit": frm[5].value,
        "creditDetail": frm[6].value,
        "curriculum": frm[7].value,
        "description": frm[8].value
    });

    section.classList.add('hide');

    return Promise.all([updateSubject(url, subjectData)])
        .then(results => {
            console.log('Subject updated successfully:', results);
        })
        .catch(error => {
            console.error('Error updating subject:', error);
        });
}

function onSaveSubject(id) {
    const url = _ctx + 'api/v1/subject/update';
    const section = document.getElementById('subject-'+id+'-section');
    const frm = document.getElementById('subject-'+id+'-form');
    const detail = document.getElementById('subject-'+id+'-detail');

    const subjectData = JSON.stringify({
        "id": frm[0].value,
        "code": frm[1].value,
        "type": frm[2].value,
        "thaiName": frm[3].value,
        "engName": frm[4].value,
        "credit": frm[5].value,
        "creditDetail": frm[6].value,
        "curriculum": frm[7].value,
        "description": frm[8].value
    });

    detail.classList.remove('hide');
    section.classList.add('hide');

    return Promise.all([updateSubject(url, subjectData)])
        .then(results => {
            console.log('Subject updated successfully:', results);
        })
        .catch(error => {
            console.error('Error updating subject:', error);
        });
}

function onCancelSubject(id) {
    const section = document.getElementById('subject-'+id+'-section');
    const detail = document.getElementById('subject-'+id+'-detail');
    const newSection = document.getElementById('add-new');

    detail.classList.remove('hide');
    section.classList.add('hide');
    newSection.classList.add('hide');
}

function onNewSubject() {
    const newSection = document.getElementById('add-new');

    newSection.classList.remove('hide');
}

function updateSubject(url, subjectData) {
    return fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: subjectData
    }).then(response => {
        if (!response.ok) {
            throw new Error(`Failed to update attendance for enrollment ID: ${enrollmentId}`);
        }
        console.log("success");
    });
}

document.addEventListener("DOMContentLoaded", function () {
    const buttons = document.querySelectorAll('.edit');
    const saveButtons = document.querySelectorAll('.frm-save');
    const cancelButtons = document.querySelectorAll('.frm-cancel');
    const addBtn = document.querySelector(".add-new");

    buttons.forEach(button => {
        button.addEventListener('click', onBtnEditClick.bind(null, button.getAttribute("data-id")));
    });

    saveButtons.forEach(button => {
        button.addEventListener('click', onSaveSubject.bind(null, button.getAttribute("data-id")));
    });

    cancelButtons.forEach(button => {
        button.addEventListener('click', onCancelSubject.bind(null, button.getAttribute("data-id")))
    });
});