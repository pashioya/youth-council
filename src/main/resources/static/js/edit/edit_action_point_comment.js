import {getCsrfInfo} from "../common/utils.js";

export async function editComment(id, content) {
    return fetch(`/api/action-points/${id}`, {
        method: 'PATCH',
        headers: {
            'Content-Type': 'application/json',
            ...getCsrfInfo()
        },
        body: content
    });
}



const editButtons = document.querySelectorAll('.edit-standard-action');
const newComment = document.getElementById("new-comment");
editButtons.forEach(button => {
        button.addEventListener('click', async () => {
            let ideaDiv = document.getElementById('container');
            let id = ideaDiv.getAttribute('data-action-point-id');
            let response = await editComment(id, newComment.value);
            if (response.status === 200) {
                window.location.reload();
            }
        });
    }
);

