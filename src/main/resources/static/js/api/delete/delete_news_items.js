import { getCsrfInfo } from '../common/utils.js';
export async function deleteNewsItem(id) {
    return fetch(`/api/news-items/${id}`, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json',
            ...getCsrfInfo()
        }
    });
}
const deleteButtons = document.querySelectorAll('.delete-news-item');
deleteButtons.forEach(button => {
        button.addEventListener('click', async () => {
            let row = button.parentNode.parentNode;
            let id = row.getAttribute('data-news-item-id');
            let response = await deleteNewsItem(id);
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
