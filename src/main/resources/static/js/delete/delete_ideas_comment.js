import { getCsrfInfo } from '../common/utils.js';
export async function deleteIdeasComment(id) {
    return fetch(`/api/ideas/${id}/comment`, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json',
            ...getCsrfInfo()
        }
    });
}
const deleteButtons = document.querySelectorAll('.delete-idea-comment');
deleteButtons.forEach(button => {
        button.addEventListener('click', async () => {
            let row = button.parentNode.parentNode;
            let div = row.parentNode;

            let ideaDiv = document.getElementById('container');
            let id = ideaDiv.getAttribute('data-idea-id');
            let response = await deleteIdeasComment(id);
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
