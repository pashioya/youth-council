import {getCsrfInfo} from '../common/utils.js';

export async function deleteActionPoint(id) {
    return fetch(`/api/action-points/${id}`, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json',
            ...getCsrfInfo()
        }
    });
}

const deleteButtons = document.querySelectorAll('.delete-action-point');
deleteButtons.forEach(button => {
        button.addEventListener('click', async () => {
            let actionPoint = button.parentNode.parentNode;
            let id = actionPoint.getAttribute('data-action-point-id');
            let response = await deleteActionPoint(parseInt(id));
            if (response.status === 204) {
                actionPoint.remove();
            } else {
                alert("Something went wrong");
            }
        });
    }
);
