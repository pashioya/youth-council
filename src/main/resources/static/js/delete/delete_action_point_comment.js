import {getCsrfInfo} from '../common/utils.js';

export async function deleteActionPointComment(id, commentId) {
    return fetch(`/api/action-points/${id}/comment/${commentId}`, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json',
            ...getCsrfInfo()
        }
    });
}

const deleteButtons = document.querySelectorAll('.delete-action-point-comment');
deleteButtons.forEach(button => {
        button.addEventListener('click', async () => {
            let row = button.parentNode.parentNode;
            let div = row.parentNode;
            let commentId = div.id;
            let ideaDiv = document.getElementById('container');
            let id = ideaDiv.getAttribute('data-action-point-id');
            let response = await deleteActionPointComment(id, commentId);
            if (response.status === 204) {
                div.remove();
            } else {
                alert("Something went wrong");
            }
        });
    }
);
