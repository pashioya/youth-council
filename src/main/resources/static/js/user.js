const deleteButtons = document.querySelectorAll('tbody .btn-danger');

for (const deleteButton of deleteButtons) {
    deleteButton.addEventListener('click', deleteClicked);
}

function deleteClicked(event) {
    const tableRow = event.target.parentNode.parentNode;
    const tableRowId = tableRow.id
    const userId = +tableRowId.substring(tableRowId.indexOf('_') + 1);

    fetch(`/api/users/${userId}`, {
        method: 'DELETE'
    })
        .then(handleDeletionResponse)
}

function handleDeletionResponse(response) {
    if (response.status === 204) {
        const userId = +response.url.substring(response.url.lastIndexOf('/') + 1);
        const tableRow = document.querySelector(`#user_${userId}`);
        tableRow.parentNode.removeChild(tableRow);
    }
}

const allSaveButtons = document.querySelectorAll("td > button.btn-primary");
const allPasswordInputs = document.querySelectorAll('td > input.form-control');

for (const saveButton of allSaveButtons) {
    saveButton.addEventListener("click", savePassword);
}

function savePassword(event) {
    const userRow = event.target.parentNode.parentNode;
    const userRowId = userRow.id;
    const userId = +userRowId.substring(userRowId.indexOf('_') + 1);

    fetch(`/api/users/${userId}`, {
        method: "PATCH",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({ "name": userRow.querySelector("input").value })
    })
        .then(response => {
            if (response.status === 204) {
                const saveButton = userRow.querySelector("button");
                saveButton.style.visibility = "hidden";
            }
        });
}

for (const userNameInput of allPasswordInputs) {
    userNameInput.addEventListener("input", userNameChanged);
}

function userNameChanged(event) {
    const userRow = event.target.parentNode.parentNode;
    const saveButton = userRow.querySelector("button");
    saveButton.style.visibility = "visible";
}
