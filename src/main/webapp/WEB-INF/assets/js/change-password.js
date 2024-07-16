'use strict';

$(document).ready(function () {
    const errorMessageGroup = document.getElementById('errorMessageGroup');
    const errorMessage = document.getElementById('errorMessage');

    errorMessageGroup.style.display = 'none';

    $('form').submit(function () {
        if ($('input[name="password"]').val() !== $('input[name="passwordconfirm"]').val()) {
            errorMessage.innerHTML = 'Passwords do not match';
            errorMessageGroup.style.display = 'block';
            return false;
        }
    });
});