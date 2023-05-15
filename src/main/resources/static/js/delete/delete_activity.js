const deleteButtons = document.querySelectorAll('div .btn-danger');

for (const deleteButton of deleteButtons) {
    deleteButton.addEventListener('click', deleteClicked);
}

function getCsrfInfo() {
    const header = document.querySelector('meta[name="_csrf_header"]').content
    const token = document.querySelector('meta[name="_csrf"]').content
    return {
        [header]: token
    }
}

function deleteClicked(event) {
    const div = event.target.parentNode.parentNode;
    const divId = div.id
    const activitiesId = +divId.substring(divId.indexOf('_') + 1);


    fetch(`/api/activities/${activitiesId}`, {
        method: 'DELETE',
        headers: {
            ...getCsrfInfo()
        }
    })
        .then(handleDeletionResponse)
}

function handleDeletionResponse(response) {
    if (response.status === 204) {
        const activitiesId = +response.url.substring(response.url.lastIndexOf('/') + 1);
        const div = document.querySelector(`#activity_${activitiesId}`);
    }
}
