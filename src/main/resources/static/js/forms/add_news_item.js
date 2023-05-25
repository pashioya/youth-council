import {getCsrfInfo} from "../common/utils.js";

const form = document.getElementById("submitForm");
const title = document.getElementById("title");
const content = document.getElementById("content");
const image = document.getElementById("image");

form.addEventListener("submit", trySubmitForm);

function trySubmitForm(event) {
    event.preventDefault()
    if (!form.checkValidity()) {
        event.stopPropagation()
    }


    let formData = new FormData()
    if (image.files[0] === undefined) {
        form.classList.add('was-validated')
        return;
    }
    formData.append("image", image.files[0], "image");
    formData.append('newsItem', new Blob([JSON.stringify({
        "title": title.value,
        "content": content.value,
    })], {
        type: "application/json"
    }), "newsItem");
        fetch('/api/news-items', {
        method: "POST",
        headers: {
            'Accept': 'application/json',
            ...getCsrfInfo()
        },
        body: formData
    }).then(response => {
        if (response.status === 201) {
            console.log("success")
        }
    });
    form.classList.add('was-validated')
}
