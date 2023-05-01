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
enterYouthCouncilAdminEmailButton.addEventListener("click", function () {
    if (input.value.length === 0) {
        return;
    }
    if (input.value.indexOf("@") === -1) {
        alert("Please enter a valid email address");
        return;
    }
    if (input.value.indexOf(".") === -1) {
        alert("Please enter a valid email address");
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

function submitYouthCouncil(event) {
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
            // specifying content type multipart/form-data gave errors
            [header]: token
        },
        body: formData
    }).then(response => {
        if (response.status === 201) {
            alert("Successfully added youth council")
        } else if (response.status === 400) {
            alert("Please check fields and try again")
        }
    })
}
