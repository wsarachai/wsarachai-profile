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

    jQuery.ajax ({
        url: url,
        type: "POST",
        data: data,
        dataType: "json",
        contentType: "application/json; charset=utf-8",
        success: function(){
            console.log("success");
        }
    });
}