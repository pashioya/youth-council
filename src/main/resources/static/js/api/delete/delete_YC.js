import { getCsrfInfo } from '../../common/utils.js';
export async function deleteYouthCouncil(id) {
    return fetch(`/api/youth-councils/${id}`, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json',
            ...getCsrfInfo()
        }
    });
}
const deleteButtons = document.querySelectorAll('.delete-youth-council');
deleteButtons.forEach(button => {
        button.addEventListener('click', async () => {
            let row = button.parentNode.parentNode;
            let id = row.getAttribute('data-youth-council-id');
            let response = await deleteYouthCouncil(id);
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
