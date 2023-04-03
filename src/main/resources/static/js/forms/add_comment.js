let pathname = window.location.pathname;
let parts = pathname.split("/");
let youthCouncilId = parts[2];

let commentSubmissionForm = document.querySelectorAll(".comment-form");


function trySubmitForm(event) {
    event.preventDefault();
    let form = event.target.closest("form");
    let textArea = form.querySelector(".textarea");
    let contentText = textArea.textContent;

    const header = document.querySelector('meta[name="_csrf_header"]').content;
    const token = document.querySelector('meta[name="_csrf"]').content;

    let ideaID = parseInt(form.getAttribute("id").split("-")[3]);

    // check if content is empty
    if (contentText.trim() === "") {
        return;
    }

    fetch(
        "/api/youth-councils/" +
        youthCouncilId +
        "/ideas/" +
        ideaID +
        "/comments",
        {
            method: "POST",
            headers: {
                Accept: "application/json",
                "Content-Type": "application/json",
                [header]: token,
            },
            body: JSON.stringify({
                "content": contentText,
            }),
        }
    ).then((response) => {
        if (response.status === 201) {
            location.reload();
        }
    });
}


commentSubmissionForm.forEach((form) => {
    form.querySelector("button").addEventListener("click", trySubmitForm);
});

