import {getCsrfInfo} from "../common/utils.js";

export async function addStandardAction(themeId, name) {
    return fetch(`/api/standard-actions/` + themeId, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            ...getCsrfInfo()
        },
        body: name.value
    });
}

const form = document.getElementById("submitForm");
const newStandardAction = document.getElementById("new-standard-action");
const url = window.location.href;
const themeId = url.substring(url.lastIndexOf('/') + 1);

// run add Standard Action function when submit button is clicked
form.addEventListener("submit", async function (event) {
    event.preventDefault()
    if (!form.checkValidity()) {
        event.stopPropagation()
    }

    let response = await addStandardAction(themeId, newStandardAction);
    if (response.status === 201) {
        //     add new standard action to list
        let newStandardAction = await response.json();
        let standardActionList = document.querySelector("tbody")
        let newStandardActionRow = document.createElement("tr");

        newStandardActionRow.innerHTML = `
            <td class="text-primary standard-action-name">${newStandardAction.name}</td>
            <td>
                <a class="btn btn-primary edit-standard-action">Edit</a>
            </td>
            <td>
                <button class="btn btn-danger delete-standard-action">Delete</button>
            </td>
        `;
        standardActionList.appendChild(newStandardActionRow);
        document.querySelector(".btn-close").click();
        form.reset();

    }
    form.classList.add('was-validated')
});