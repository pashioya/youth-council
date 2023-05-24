submitButton = document.getElementById("submit-template");
submitButton.addEventListener("click", submitTemplate);

function submitTemplate() {
    const header = document.querySelector('meta[name="_csrf_header"]').content;
    const token = document.querySelector('meta[name="_csrf"]').content;
    const sections = document.getElementsByName("section");
    const templateTitle = document.getElementById("title");
    const templateContents = {};
    for (let section of sections) {
        let heading = section.querySelector('.heading').value;
        let paragraph = section.querySelector('.paragraph').value;
        templateContents[heading] = paragraph;
    }
    fetch('/api/webpage-templates', {
        method: "POST",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            [header]: token
        },
        body: JSON.stringify({
            "title": templateTitle.innerText,
            "headingsBodies": templateContents
        })
    }).then(response => {
        if (response.status === 201) {
            window.location.href = "/dashboard/page-templates";
        } else if (response.status === 400) {
            alert("Please check fields and try again")
        }
    })
}