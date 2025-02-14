'use strict';

let btnLecture = {};
let btnLab = {};
let imgLec = [];
let imgLab = [];

function saveAttenStatus(url, type, status, enrollmentId, week) {
    return fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            "type": type,
            "status": status,
            "enrollmentId": enrollmentId,
            "week": week
        })
    }).then(response => {
        if (!response.ok) {
            throw new Error(`Failed to update attendance for enrollment ID: ${enrollmentId}`);
        }
        console.log("success");
    });
}

function attenStatusChange(type, index, enrollmentId, week) {
    const url = _ctx + 'api/v1/atten/update';
    const element = document.getElementById('attendance-' + type + '-status-' + index)

    const status = element.value;
    type = element.getAttribute('data-type');
    enrollmentId = element.getAttribute('data-enrollment');
    week = element.getAttribute('data-week');

    return Promise.all([saveAttenStatus(url, type, status, enrollmentId, week)])
        .then(results => {
            console.log('All attendance statuses updated successfully:', results);
        })
        .catch(error => {
            console.error('Error updating attendance statuses:', error);
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

    if (btnLecture) {
        btnLecture.addEventListener('click', () => {
            hideElements(imgLec);
            showElements(imgLab)
            btnLab.classList.remove('hide');
            btnLecture.classList.add('hide');
        });
    }
    if (btnLab) {
        btnLab.addEventListener('click', () => {
            hideElements(imgLab);
            showElements(imgLec);
            btnLab.classList.add('hide');
            btnLecture.classList.remove('hide');
        });
    }
}

function changeAllLecStatus() {
    const lecStatus = document.getElementById('attendance-status-all-lec').value;
    const elements = document.querySelectorAll('.atten-lec');
    changeAllStatus(lecStatus, elements);
}

function changeAllLabStatus() {
    const lecStatus = document.getElementById('attendance-status-all-lab').value;
    const elements = document.querySelectorAll('.atten-lab');
    changeAllStatus(lecStatus, elements);
}

function changeAllStatus(status, elements) {
    let text = "Change all attendance status!\nEither OK or Cancel.";
    if (confirm(text) == true) {
        const url = _ctx + 'api/v1/atten/update';

        const attendancePromises = [];

        elements.forEach(element => {
            element.value = status;
            const type = element.getAttribute('data-type');
            const enrollmentId = element.getAttribute('data-enrollment');
            const week = element.getAttribute('data-week');
            console.log(url, status, type, enrollmentId, week);
            attendancePromises.push(saveAttenStatus(url, type, status, enrollmentId, week));
        });

        return Promise.all(attendancePromises)
            .then(results => {
                console.log('All attendance statuses updated successfully:', results);
            })
            .catch(error => {
                console.error('Error updating attendance statuses:', error);
            });
    }
}

window.addEventListener("DOMContentLoaded", (event) => {
    init();
});