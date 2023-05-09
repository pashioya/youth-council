function getCsrfInfo() {
    const header = document.querySelector('meta[name="_csrf_header"]').content
    const token = document.querySelector('meta[name="_csrf"]').content
    return {
        [header]: token
    }
}

function deleteClicked(event) {
    const div = event.target.parentNode;
    const divId = div.id;
    const userId = +divId.substring(divId.indexOf('_') + 1);

    fetch(`/api/users/${userId}`, {
        method: 'DELETE',
        headers: {
            ...getCsrfInfo()
        }
    })
        .then(handleDeletionResponse)
}
const deleteButton = document.getElementById("deleteButton");
deleteButton.addEventListener('click', deleteClicked);

function handleDeletionResponse(response) {
    if (response.status === 204) {
        const userId = +response.url.substring(response.url.lastIndexOf('/') + 1);
        const div = document.querySelector(`#user_${userId}`);
    }
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

const change = document.getElementById("change");
change.addEventListener("click", showInput);

const changePasswordInput = document.getElementById("changePasswordInput");
changePasswordInput.style.display = "none";
const changePasswordButton = document.getElementById("changePasswordButton");
changePasswordButton.style.display = "none";
function showInput(){
    changePasswordInput.style.display = "block";
    changePasswordButton.style.display = "block";
}
