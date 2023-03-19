/**
 * @type {HTMLFormElement}
 */

let pathname = window.location.pathname;
let parts = pathname.split("/");
let youthCouncilId = parts[2];

// get the idea id from the submit button clicked
let commentSubmissionForm = document.querySelectorAll(".comment-form");
    commentSubmissionForm.forEach((form) => {
    let ideaID = form.getAttribute("id").split("-")[3];
    form.setAttribute("url", "/youth-councils/" + youthCouncilId + "/idea/" + ideaID + "/comments");

    console.log("form url " + form.getAttribute("url"));
    form.querySelector("button").addEventListener("click", trySubmitForm, ideaID);
});

function trySubmitForm(event, ideaID) {
    event.preventDefault();

    const header = document.querySelector('meta[name="_csrf_header"]').content;
    const token = document.querySelector('meta[name="_csrf"]').content;

let content = form.querySelector("input");
    console.log("content: " + content.value);
    fetch(
        "/api/youth-councils/" +
        youthCouncilId +
        "/idea/" +
        ideaID +
        " /comments",
        {
            method: "POST",
            headers: {
                Accept: "application/json",
                "Content-Type": "application/json",
                [header]: token,
            },
            body: JSON.stringify({
                content: content.value,
            }),
        }
    ).then((response) => {
        if (response.status === 201) {
            form.reset();
            form.classList.remove("was-validated");
        }
    });
}
