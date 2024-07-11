"use strict";

let selectFile = {};
let fileInfo = {};
let chooseFileBtn = {};

const init = function() {
    selectFile = document.getElementById("input-file");
    fileInfo = document.getElementById("file-info");
    chooseFileBtn = document.getElementById("choose_file_btn");

    selectFile.addEventListener("change", () => {
        let file = selectFile.files[0];
        fileInfo.innerHTML = "File: " + file.name + "<br>" + "Size: " + file.size + " bytes";
    });
    chooseFileBtn.addEventListener("click", () => {
        selectFile.click();
    });
};

window.addEventListener("DOMContentLoaded", (event) => {
    init();
});