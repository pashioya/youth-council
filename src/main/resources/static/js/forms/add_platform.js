let enterYouthCouncilAdminEmailButton = document.getElementById("enter");
let input = document.getElementById("new-email");
let ul = document.getElementById("yc-admin-emails");
const nameInput = document.getElementById("yc-name");
const municipalityInput = document.getElementById("municipality");
const logoInput = document.getElementById("logo");
const subdomainInput = document.getElementById("yc-subdomain")
const youthCouncilForm = document.getElementById("submitForm")
const ycsPlatformTable = document.querySelector("tbody")

const header = document.querySelector('meta[name="_csrf_header"]').content;
const token = document.querySelector('meta[name="_csrf"]').content;
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

const submitButton = document.getElementById("submit_youth_council");
submitButton.addEventListener("click", submitYouthCouncil);

function submitYouthCouncil() {
    let formData = new FormData()
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
}


let tableRows = document.querySelectorAll(".table-row");
tableRows.forEach(row => {
    row.addEventListener("click", function () {
        window.location.href = row.dataset.href;
    })
})
