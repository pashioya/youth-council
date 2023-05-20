
import { getCsrfInfo } from '../common/utils.js';
export async function deleteActionPointComment(id) {
    return fetch(`/api/action-points/${id}/comment`, {
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

            let ideaDiv = document.getElementById('container');
            let id = ideaDiv.getAttribute('data-action-point-id');
            let response = await deleteActionPointComment(id);
            if (response.status === 200) {
                div.remove();
            }
            else
            {
                alert("Something went wrong");
            }
        });
    }
);
