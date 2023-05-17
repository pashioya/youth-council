import { getCsrfInfo } from '../common/utils.js';
export async function deleteIdea(id) {
    return fetch(`/api/ideas/${id}`, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json',
            ...getCsrfInfo()
        }
    });
}
const deleteButtons = document.querySelectorAll('.delete-idea');
deleteButtons.forEach(button => {
        button.addEventListener('click', async () => {
            let section = button.parentNode.parentNode;
            let div = section.parentNode.parentNode;
            let parentNode = div.parentNode.parentNode;
            let row = parentNode.parentNode.parentNode;

            let id = row.getAttribute('data-idea-id');
            let response = await deleteIdea(id);
            if (response.status === 200) {
                row.remove();
            }
            else
            {
                alert("Something went wrong");
            }
        });
    }
);
