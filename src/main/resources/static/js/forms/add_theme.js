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
form.addEventListener("submit", async function (event) {
        event.preventDefault()
        if (!form.checkValidity()) {
            event.stopPropagation()
        }
        let response = await addTheme(newTheme.value);
        console.log(response.status)
        console.log(newTheme.value)
        if (response.status === 201) {
            // add new theme to list
            let newTheme = await response.json();
            let themeList = document.querySelector("tbody")
            let newThemeRow = document.createElement("tr");

            newThemeRow.innerHTML = `
            <td class="text-primary theme-name">${newTheme.name}</td>
            <td class="text-primary theme-name">0</td>
        `;
            themeList.appendChild(newThemeRow);
            document.querySelector(".btn-close").click();
            form.reset();

        } else {
            console.log("error {}" + response.status)
        }

        form.classList.add('was-validated')
    }
);