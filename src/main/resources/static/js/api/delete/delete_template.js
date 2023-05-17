import { getCsrfInfo } from '../../common/utils.js';
export async function deleteTemplate(id) {
    return fetch(`/api/webpage-templates/${id}`, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json',
            ...getCsrfInfo()
        }
    });
}
const deleteButtons = document.querySelectorAll('.delete-template');
deleteButtons.forEach(button => {
        button.addEventListener('click', async () => {
            let row = button.parentNode;
            let id = row.getAttribute('data-template-id');
            let response = await deleteTemplate(id);
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
