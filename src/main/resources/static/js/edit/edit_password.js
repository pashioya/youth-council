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
const userId = document.querySelector("section").getAttribute("data-userId");
editPasswordButton.addEventListener('click', async () => {
        editPasswordContainer.classList.toggle("d-none");
    }
);

editPasswordForm.addEventListener('submit', async (event) => {
    if (!editPasswordForm.checkValidity()) {
        event.preventDefault()
        event.stopPropagation()
    }
    if (editPasswordInput.value !== editPasswordInputConfirm.value) {
        alert("Passwords do not match");
        editPasswordForm.classList.add('was-validated')
        event.preventDefault()
        event.stopPropagation()
        editPasswordForm.classList.add('was-validated')
    }

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

    editPasswordForm.classList.add('was-validated')
});