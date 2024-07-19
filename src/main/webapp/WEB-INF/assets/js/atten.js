'use strict';

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