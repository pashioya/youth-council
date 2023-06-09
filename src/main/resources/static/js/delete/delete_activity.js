import {getCsrfInfo} from '../common/utils.js';

export async function deleteActivity(id) {
    return fetch(`/api/activities/${id}`, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json',
            ...getCsrfInfo()
        }
    });
}

const deleteButtons = document.querySelectorAll('.delete-activity');
deleteButtons.forEach(button => {
        button.addEventListener('click', async () => {
            let row = button.parentNode;
            let id = row.getAttribute('data-activity-id');
            let response = await deleteActivity(id);
            if (response.status === 204) {
                row.remove();
            } else {
                alert("Something went wrong");
            }
        });
    }
);
