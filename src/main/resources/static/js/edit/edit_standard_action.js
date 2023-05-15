import { getCsrfInfo } from '../common/utils.js';


export function editStandardAction(id, name) {
    return fetch(`/api/standard-actions/${id}`, {
        method: 'PATCH',
        headers: {
            'Content-Type': 'application/json',
            ...getCsrfInfo()
        },
        body: name
    });
}
const editForm = document.querySelector('#edit-standard-action-form');
const editButtons = document.querySelectorAll('.edit-standard-action');
const submitEditButton = editForm.querySelector('button');
editButtons.forEach(button => {
    button.addEventListener('click', async () => {
        let row = button.parentNode.parentNode;
        let id = row.getAttribute('data-standard-action-id');
        editForm.setAttribute('data-standard-action-id', id);
        let name = row.querySelector('.standard-action-name').textContent;
        let input = editForm.querySelector('input');
        input.value = name;
    });
}
);

submitEditButton.addEventListener('click',   () => {
    console.log("submit edit button clicked")
    let id = editForm.getAttribute('data-standard-action-id');
    console.log(id)
    let name = editForm.querySelector('input').value;
    console.log(name)
    let response = editStandardAction(id, name);
    if (response.status === 200) {
        let row = document.querySelector(`tr[data-standard-action-id="${id}"]`);
        row.querySelector('.standard-action-name').textContent = name;
        document.querySelector('.btn-close').click();
    }
    else
        alert("Error: " + response.status);
});
