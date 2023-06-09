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

editForm.addEventListener('submit',   (event) => {
    console.log("submit edit button clicked")
    if (!editForm.checkValidity()) {
        event.preventDefault()
        event.stopPropagation()
    }

    let id = editForm.getAttribute('data-standard-action-id');
    let name = editForm.querySelector('input').value;
    let response = editStandardAction(id, name);
    let responseJson = response.json();
    if (responseJson.status === 200) {
        let row = document.querySelector(`tr[data-standard-action-id="${id}"]`);
        row.querySelector('.standard-action-name').textContent = name;
        document.querySelector('.btn-close').click();
    }
    else
        alert("Error: " + response);

    editForm.classList.add('was-validated')
});
