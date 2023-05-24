import {getCsrfInfo} from "../common/utils.js";

export async function addTheme(name) {
    return fetch(`/api/themes`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            ...getCsrfInfo()
        },
        body: name
    });
}


const form = document.getElementById("submitForm");
const newTheme = form.querySelector("input");
const submitButton = document.getElementById("submit-theme-name");
submitButton.addEventListener("click", async function () {
        console.log(newTheme.value);
        console.log(newTheme);
        let response = await addTheme(newTheme.value);
        if (response.status === 201) {
            // add new theme to list
            let newTheme = await response.json();
            let themeList = document.querySelector("tbody")
            let newThemeRow = document.createElement("tr");

            newThemeRow.innerHTML = `
            <td class="text-primary theme-name">${newTheme.name}</td>
            <td class="text-primary theme-name">0</td>
            <td>
                <a class="btn btn-primary edit-theme">Edit</a>
            </td>
            <td>
                <button class="btn btn-danger delete-theme">Delete</button>
            </td>
        `;
            themeList.appendChild(newThemeRow);
            document.querySelector(".btn-close").click();
            form.reset();

        } else
            alert("Error: " + response.status);
    }
);