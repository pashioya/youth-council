import {getCsrfInfo} from "../common/utils.js";

export async function deleteTheme(themeId) {
    return fetch('/api/themes/' + themeId, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json;charset=utf-8',
            ...getCsrfInfo()
        }
    });
}

const deleteButton = document.querySelector(".delete-theme");

const url = window.location.href;
const themeId = url.substring(url.lastIndexOf('/') + 1);
deleteButton.addEventListener("click", async function () {
        let response = await deleteTheme(themeId);
        if (response.status === 204) {
            window.location.href = "/dashboard/themes";
        } else
            alert("Error: " + response.status);
    }
);
