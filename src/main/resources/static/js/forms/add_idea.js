/**
 * @type {HTMLFormElement}
 */
const form = document.getElementById("submitForm");
const theme = document.getElementById("theme");
const description = document.getElementById("description");
const submitButton = document.getElementById("submit-idea");
const imageInput = document.getElementById('idea-image');
const ideasColumn = document.getElementById("ideas");
submitButton.addEventListener("click", trySubmitForm);


function trySubmitForm(event) {
    const header = document.querySelector('meta[name="_csrf_header"]').content;
    const token = document.querySelector('meta[name="_csrf"]').content;
    let formData = new FormData()
    const files = imageInput.files;

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
            [header]: token
        },
        body: formData
    }).then(response => {
        if (response.status === 201) {
            location.href = "/ideas";
            // response.json().then(showNewIdea)
        }
    });
}


