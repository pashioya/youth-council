import {getCsrfInfo} from '../common/utils.js';

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
            let div = row.parentNode.parentNode;
            let newsItemDiv = div.parentNode;
            let id = newsItemDiv.getAttribute('data-news-item-id');
            let response = await deleteNewsItem(id);
            if (response.status === 204) {
                newsItemDiv.remove();
            } else {
                alert("Something went wrong");
            }
        });
    }
);
