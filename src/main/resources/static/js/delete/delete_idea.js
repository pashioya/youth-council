const deleteButtons = document.querySelectorAll('div .btn');

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
    console.log(div)
    const divId = div.id
    const ideaId = +divId.substring(divId.indexOf('_') + 1);


    fetch(`/api/ideas/${ideaId}`, {
        method: 'DELETE',
        headers: {
            ...getCsrfInfo()
        }
    })
        .then(handleDeletionResponse)
}

function handleDeletionResponse(response) {
    if (response.status === 204) {
        const ideaId = +response.url.substring(response.url.lastIndexOf('/') + 1);
        const div = document.querySelector(`#idea_${ideaId}`);
    }
}
