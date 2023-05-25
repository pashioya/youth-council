import {getCsrfInfo} from "../common/utils.js";

export async function deleteUser(userId) {
    return fetch(`/api/users/${userId}`, {
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


