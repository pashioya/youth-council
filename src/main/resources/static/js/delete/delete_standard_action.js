import {getCsrfInfo} from '../common/utils.js';

export async function deleteStandardAction(id) {
    return fetch(`/api/standard-actions/${id}`, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json',
            ...getCsrfInfo()
        }
    });
}
const deleteButtons = document.querySelectorAll('.delete-standard-action');
deleteButtons.forEach(button => {
    button.addEventListener('click', async () => {
        let row = button.parentNode.parentNode;
        let id = row.getAttribute('data-standard-action-id');
        let response = await deleteStandardAction(id);
        if (response.status === 204) {
            row.remove();
        } else {
            alert("Something went wrong");
        }
    });
}
);
