'use strict';

let btnLecture = {};
let btnLab = {};
let imgLec = [];
let imgLab = [];

function attenStatusChange(type, index, enrollmentId, week) {
    const status = document.getElementById('attendance-' + type + '-status-' + index).value;
    const url = _ctx + 'api/v1/atten/update';

    const data = JSON.stringify({
        "type": type,
        "status": status,
        "enrollmentId": enrollmentId,
        "week": week
    });

    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: data
    }).then(response => {
        if (response.ok) {
            console.log("success");
        } else {
            console.log("fail");
        }
    });
}

function hideElements(elements) {
    elements.forEach(element => {
        element.classList.add('hide');
    });
}

function showElements(elements) {
    elements.forEach(element => {
        element.classList.remove('hide');
    });
}

function init() {
    btnLecture = document.getElementById('btn-lec');
    btnLab = document.getElementById('btn-lab');
    imgLec = document.querySelectorAll('.img-lec')
    imgLab = document.querySelectorAll('.img-lab');

    btnLecture.addEventListener('click', () => {
        hideElements(imgLab);
        showElements(imgLec);
        btnLab.classList.remove('hide');
        btnLecture.classList.add('hide');
    });
    btnLab.addEventListener('click', () => {
        hideElements(imgLec);
        showElements(imgLab);
        btnLab.classList.add('hide');
        btnLecture.classList.remove('hide');
    });
}

window.addEventListener("DOMContentLoaded", (event) => {
    init();
});