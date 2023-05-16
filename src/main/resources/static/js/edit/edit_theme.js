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
const submitButton = document.getElementById("submit-new-theme-name");
const url = window.location.href;
const themeId = url.substring(url.lastIndexOf('/') + 1);
submitButton.addEventListener("click", async function () {
        let response = await editTheme(themeId, newTheme.value);
        if (response.status === 200) {
            window.location.reload();
        }
    }
);


