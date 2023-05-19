import {getCsrfInfo} from "../common/utils.js";

export async function deleteOwnAccount() {
    return fetch(`/api/users/self`, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json',
            ...getCsrfInfo()
        }
    });
}


const deleteButton = document.querySelector('#delete-account');

deleteButton.addEventListener('click', async () => {
        const response = await deleteOwnAccount();
        if (response.ok) {
            window.location.href = '/';
        } else {
            alert('Something went wrong');
        }
    }
);