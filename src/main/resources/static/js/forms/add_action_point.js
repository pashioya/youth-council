/**
 * @type {HTMLFormElement}
 */
const form = document.getElementById("submitForm");
const submitButton = document.querySelector("#submitForm > div > button");
const title = document.getElementById("title");
const description = document.getElementById("description");

submitButton.addEventListener("click", trySubmitForm);

function trySubmitForm(event) {
    event.preventDefault();

    const header = document.querySelector('meta[name="_csrf_header"]').content;
    const token = document.querySelector('meta[name="_csrf"]').content;

    fetch('/api/action-points', {
        method: "POST",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            [header]: token
        },
        body: JSON.stringify({
            "statusName" :  "REALIZED",
            "title" : title.value,
            "video" : "youtube.com/video",
            "description" : description.value,
            "images" : ["picture1.jpg", "picture2.jpg"],
            "linkedIdeaIds" :  [],
            "standardActionId" :  2
        })
    }).then(response => {
        if (response.status === 201) {
            location.href = "/action-points";
        }
    });
}

const action_description = document.getElementById("action_description");
action_description.style.display = "none";
function showInput(event){
    action_description.style.display = "block";
}