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

const item_description = document.getElementById("item_description");
item_description.style.display = "none";
function showInput(event){
    item_description.style.display = "block";
}