const textAreas = document.getElementsByTagName("textarea");

for (const textArea of textAreas) {
    textArea.addEventListener("keyup", (event) => {
        textAreaAdjust((textArea));
    })
}

function textAreaAdjust(element) {
    element.style.height = "0px";
    element.style.height = element.scrollHeight + "px";
}

function setColumnClass() {
    if (window.innerWidth <= 576) { // check if the viewport width is mobile sized
        let divs = document.getElementsByClassName("col-6"); // select all div elements with class "col-6"
        if (divs != null) {
            for (let div of divs) {
                div.classList.remove("col-6"); // remove the class "col-6"
                div.classList.add("col-12"); // add the class "col-12"
            }
        }
    } else { // revert back to "col-6" class for larger viewports
        let divs = document.getElementsByClassName("col-12"); // select all div elements with class "col-12"
        if (divs != null) {
            for (let div of divs) {
                div.classList.remove("col-12"); // remove the class "col-12"
                div.classList.add("col-6"); // add the class "col-6"
            }
        }
    }
    for (const textArea of textAreas) {
        textAreaAdjust(textArea);
    }
}

window.addEventListener("resize", setColumnClass);

const submitButton = document.getElementById("submit-template");

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