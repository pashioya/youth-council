import {deleteEntity} from "../api/api_facade.js";

const deleteButtons = document.querySelectorAll('#delete-entity');

deleteButtons.forEach(button => {
    button.addEventListener('click', async () => {
        const id = parseInt(button.getAttribute('data-id'));
        const response = await deleteEntity('elections', id);
        if (response.status === 204) {
            const row = button.parentNode.parentNode
            row.remove();
        } else {
            alert("Something went wrong");
        }
    });
});
