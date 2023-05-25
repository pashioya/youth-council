import {getCsrfInfo} from "../common/utils.js";

/**
 * @type {HTMLFormElement}
 */
const form = document.getElementById("submitForm");
const submitButton = document.querySelector("#submitForm > div > button");
const title = document.getElementById("title");
const description = document.getElementById("description");
const standardActionId = document.getElementById("standardAction");
const imageInput = document.getElementById('action-point-image');
const status = document.getElementById("status");

submitButton.addEventListener("click", trySubmitForm);

function trySubmitForm(event) {
    event.preventDefault();
    if (!form.checkValidity()) {
        event.stopPropagation()
    }


    let formData = new FormData()
    const files = imageInput.files;

    if (files[0] === undefined) {
        form.classList.add('was-validated')
        return;
    }

    for (let i = 0; i < files.length; i++) {
        formData.append('images', files[i]);
    }
    formData.append('actionPoint', new Blob([JSON.stringify({
        "statusName": status.value,
        "title": title.value,
        "description": description.value,
        "standardActionId": standardActionId.value,
        "status": status.value,
        "video": "youtube.com/video",
        "linkedIdeaIds": []
    })], {
        type: "application/json"
    }), "actionPoint");



    fetch('/api/action-points', {
        method: "POST",
        headers: {
            'Accept': 'application/json',
            ...getCsrfInfo()
        },
        body: formData
    }).then(response => {
        if (response.status === 201) {
            location.href = "/action-points";
        }
    });
    form.classList.add('was-validated')
}
