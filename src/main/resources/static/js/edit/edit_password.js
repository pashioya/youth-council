import {getCsrfInfo} from "../common/utils.js";

export async function editPassword(userId, newPassword) {
    return fetch(`/api/users/${userId}`, {
        method: 'PATCH',
        headers: {
            'Content-Type': 'application/json',
            ...getCsrfInfo()
        },
        body: JSON.stringify({password: newPassword})
    });
}

const editPasswordButton = document.getElementById("edit-password");
const editPasswordContainer = document.getElementById("edit-password-container");
const editPasswordForm = document.getElementById("edit-password-form");
const editPasswordInput = document.getElementById("edit-password-input");
const editPasswordInputConfirm = document.getElementById("edit-password-input-confirm");
const editPasswordSubmit = document.getElementById("edit-password-submit");
const userId = document.querySelector("section").getAttribute("data-userId");
editPasswordButton.addEventListener('click', async () => {
        editPasswordContainer.classList.toggle("d-none");
    }
);

editPasswordSubmit.addEventListener('click', async (event) => {
    event.preventDefault();
    if (editPasswordInput.value === editPasswordInputConfirm.value) {
        const response = await editPassword(userId, editPasswordInput.value);
        if (response.ok) {
            editPasswordContainer.classList.toggle("d-none");
            editPasswordInput.value = "";
            editPasswordInputConfirm.value = "";
        } else {
            alert("Something went wrong");
        }
    }
});