import {deleteOwnAccount} from "./delete_user.js";

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
