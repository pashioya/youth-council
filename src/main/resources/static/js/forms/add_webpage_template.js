tButton = document.getElementById("submit-template");

submitButton.addEventListener("click", submitTemplate);


const templateTitle = document.getElementById("title");

function submitTemplate(event) {
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
    console.log(templateContents);
    fetch('/api/webpage-templates', {
        method: "POST",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            [header]: token
        },
        body: JSON.stringify({
            "title": templateTitle.value,
            "headingsBodies": templateContents
        })
    }).then(response => {
        if (response.status === 201) {
            alert("Successfully added page template")
        } else if (response.status === 400) {
            alert("Please check fields and try again")
        }
    })
}