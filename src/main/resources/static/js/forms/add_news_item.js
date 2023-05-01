const form = document.getElementById("submitForm");
const title = document.getElementById("title");
const content = document.getElementById("content");
const image = document.getElementById("image");
const submitButton = document.getElementById("submit-news-item");

submitButton.addEventListener("click", trySubmitForm);

function trySubmitForm(event) {
    const header = document.querySelector('meta[name="_csrf_header"]').content;
    const token = document.querySelector('meta[name="_csrf"]').content;

    let formData = new FormData()
    formData.append("image", image.files[0], "image");
    formData.append('newsItem', new Blob([JSON.stringify({
        "title": title.value,
        "content": content.value,
    })], {
        type: "application/json"
    }), "newsItem");
    console.log(formData);
    fetch('/api/news-items', {
        method: "POST",
        headers: {
            'Accept': 'application/json',
            // specifying content type multipart/form-data gave errors
            [header]: token
        },
        body: formData
    }).then(response => {
        if (response.status === 201) {
            location.href = "/news-items";
        }
    });
}

const big_form = document.getElementById("submitFormBig");
const big_title = document.getElementById("item_title");
const big_content = document.getElementById("description_input");
const big_image = document.getElementById("item_image");
const big_submitButton = document.getElementById("submit-news-item");

big_submitButton.addEventListener("click", trySubmitForm);

function trySubmitForm(event) {
    const header = document.querySelector('meta[name="_csrf_header"]').content;
    const token = document.querySelector('meta[name="_csrf"]').content;

    let formData = new FormData()
    formData.append("image", image.files[0], "image");
    formData.append('newsItem', new Blob([JSON.stringify({
        "title": big_title.value,
        "content": big_content.value,
    })], {
        type: "application/json"
    }), "newsItem");
    console.log(formData);
    fetch('/api/news-items', {
        method: "POST",
        headers: {
            'Accept': 'application/json',
            // specifying content type multipart/form-data gave errors
            [header]: token
        },
        body: formData
    }).then(response => {
        if (response.status === 201) {
            location.href = "/news-items";
        }
    });
}