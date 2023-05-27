let enterYouthCouncilAdminEmailButton = document.getElementById("enter");
let input = document.getElementById("new-email");
let ul = document.getElementById("yc-admin-emails");
const nameInput = document.getElementById("yc-name");
const municipalityInput = document.getElementById("municipality");
const logoInput = document.getElementById("logo");
const subdomainInput = document.getElementById("yc-subdomain")
const youthCouncilForm = document.getElementById("submitForm")

const header = document.querySelector('meta[name="_csrf_header"]').content;
const token = document.querySelector('meta[name="_csrf"]').content;


export async function getYouthCouncils() {
    return fetch(`/api/youth-councils`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            [header]: token
        }
    });
}

enterYouthCouncilAdminEmailButton.addEventListener("click", function () {
    if (!input.checkValidity()) {
        input.reportValidity();
        return;
    }

    let emails = document.querySelectorAll("#yc-admin-emails li");
    for (let i = 0; i < emails.length; i++) {
        if (emails[i].textContent === input.value) {
            return;
        }
    }
    let li = document.createElement("li");
    li.className = 'list-group-item';
    console.log(input.value)
    li.textContent = input.value;
    ul.appendChild(li);
    input.value = "";
})
youthCouncilForm.addEventListener("submit", async function (event) {

    if (!youthCouncilForm.checkValidity()) {
        event.preventDefault()
        event.stopPropagation()
    }

    let formData = new FormData()

    if (logoInput.files[0] === undefined) {
        youthCouncilForm.classList.add('was-validated')
        return;
    }
    formData.append("logo", logoInput.files[0], "logo");
    formData.append('youthCouncil', new Blob([JSON.stringify({
        "name": nameInput.value,
        "municipalityName": municipalityInput.value,
        "subdomainName": subdomainInput.value
    })], {
        type: "application/json"
    }), "youthCouncil");
    fetch('/api/youth-councils', {
        method: "POST",
        headers: {
            'Accept': 'application/json',
            [header]: token
        },
        body: formData
    }).then(response => {
        if (response.status === 201) {
            location.reload();
        } else if (response.status === 400) {
            alert("Please check fields and try again")
        }
    })

    youthCouncilForm.classList.add('was-validated')
})

let tableRows = document.querySelectorAll(".table-row");
tableRows.forEach(row => {
    row.addEventListener("click", function () {
        window.location.href = row.dataset.href;
    })
})
