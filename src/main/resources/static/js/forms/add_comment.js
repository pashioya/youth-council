
// Temporary solution for setting the urls for the comment submission forms
//     should be done automatically by thymeleaf when the data is loaded

let pathname = window.location.pathname;
let parts = pathname.split("/");
let youthCouncilId = parts[2];

// get the idea id from the submit button clicked
let commentSubmissionForm = document.querySelectorAll(".comment-form");
    commentSubmissionForm.forEach((form) => {
    let ideaID = form.getAttribute("id").split("-")[3];
    form.setAttribute("url", "/youth-councils/" + youthCouncilId + "/idea/" + ideaID + "/comments");

    console.log("form url " + form.getAttribute("url"));
    form.querySelector("button").addEventListener("click", trySubmitForm);

});
//     end of temporary solution

function trySubmitForm(event) {
    event.preventDefault();
    let form = event.target.closest("form");
    let content = form.querySelector(".textarea").textContent;
    if (content.value === "") {
        content.classList.add("is-invalid");
        return;
    }

    const header = document.querySelector('meta[name="_csrf_header"]').content;
    const token = document.querySelector('meta[name="_csrf"]').content;



    let ideaID = parseInt(form.getAttribute("url").split("/")[4]);

    console.log("content: " + content);
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
