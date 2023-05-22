import {getCsrfInfo} from '../common/utils.js';

export async function deleteIdea(id) {
    return fetch(`/api/users/self/${id}`, {
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
            let row = button.parentNode.parentNode;
            let div = row.parentNode;
            let id = div.getAttribute('data-idea-id');
            let response = await deleteIdea(id);
            if (response.status === 200) {
                div.remove();
            } else {
                alert("Something went wrong");
            }
        });
    }
);
