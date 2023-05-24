import {deleteUserAccount} from "./delete_user.js";

const deleteButton = document.querySelector('#delete_user');

deleteButton.addEventListener('click', async () => {
        const response = await deleteUserAccount();
        if (response.ok) {
            window.location.href = '/';
        } else {
            alert('Something went wrong');
        }
    }
);
