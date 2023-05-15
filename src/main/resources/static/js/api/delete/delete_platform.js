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
    const divId = div.id
    const platformId = +divId.substring(divId.indexOf('_') + 1);
    console.log(platformId)


    fetch(`/api/admin/{platformId}`, {
        method: 'DELETE',
        headers: {
            ...getCsrfInfo()
        }
    })
        .then(handleDeletionResponse)
}

function handleDeletionResponse(response) {
    if (response.status === 204) {
        const platformId = +response.url.substring(response.url.lastIndexOf('/') + 1);
        const div = document.querySelector(`#platform_${platformId}`);
    }
}
