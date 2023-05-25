import {getCsrfInfo} from "../common/utils.js";

/**
 * @type {HTMLFormElement}
 */
const form = document.getElementById("submitForm");
const theme = document.getElementById("theme");
const description = document.getElementById("description");
const imageInput = document.getElementById('idea-image');
form.addEventListener("submit", trySubmitForm);


function trySubmitForm(event) {
    if (!form.checkValidity()) {
        event.preventDefault()
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

    formData.append('idea', new Blob([JSON.stringify({
        "description": description.value,
        "themeId": theme.value,
    })], {
        type: "application/json"
    }), "idea");

    fetch('/api/ideas', {
        method: "POST",
        headers: {
            'Accept': 'application/json',
            ...getCsrfInfo()
        },
        body: formData
    }).then(response => {
        if (response.status === 201) {
            location.href = "/ideas";
            response.json().then(showNewIdea)
        }
    });

    form.classList.add('was-validated')
}


