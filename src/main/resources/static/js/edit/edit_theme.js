import {getCsrfInfo} from "../common/utils.js";

export async function editTheme(themeId, name) {
    return fetch(`/api/themes/${themeId}`, {
        method: 'PATCH',
        headers: {
            'Content-Type': 'application/json',
            ...getCsrfInfo()
        },
        body: name
    });
}


const form = document.getElementById("edit-theme-form");
const newTheme = form.querySelector("input");
const url = window.location.href;
const themeId = url.substring(url.lastIndexOf('/') + 1);
form.addEventListener("submit", async function (event) {
    event.preventDefault();
    if (!form.checkValidity()) {
        event.stopPropagation()
    }
        let response = await editTheme(themeId, newTheme.value);
        if (response.status === 200) {
            window.location.reload();
        }
    form.classList.add('was-validated')
    }
);


