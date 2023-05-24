import {getCsrfInfo} from '../common/utils.js';

export async function deleteUser(adminId) {
    return fetch(`/api/users/${adminId}`, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json',
            ...getCsrfInfo()
        }
    });
}

export async function deleteOwnAccount() {
    return fetch(`/api/users/self`, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json',
            ...getCsrfInfo()
        }
    });
}

export async function deleteUserAccount() {
    return fetch(`/api/users/delete_user`, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json',
            ...getCsrfInfo()
        }
    });
}

const deleteButtons = document.querySelectorAll('.delete-admin');
deleteButtons.forEach(button => {
        button.addEventListener('click', async () => {
            let row = button.parentNode.parentNode;
            let id = row.getAttribute('data-admin-id');
            let response = await deleteUser(id);
            if (response.status === 200) {
                row.remove();
            } else {
                alert("Something went wrong");
            }
        });
    }
);
