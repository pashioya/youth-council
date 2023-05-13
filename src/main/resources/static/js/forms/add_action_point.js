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

    const header = document.querySelector('meta[name="_csrf_header"]').content;
    const token = document.querySelector('meta[name="_csrf"]').content;


    let formData = new FormData()
    const files = imageInput.files;
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
            [header]: token
        },
        body: formData
    }).then(response => {
        if (response.status === 201) {
            location.href = "/action-points";
        }
    });
}
