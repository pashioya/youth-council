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
    const newsItemId = +divId.substring(divId.indexOf('_') + 1);


    fetch(`/api/news-items/${newsItemId}`, {
        method: 'DELETE',
        headers: {
            ...getCsrfInfo()
        }
    })
        .then(handleDeletionResponse)
}

function handleDeletionResponse(response) {
    if (response.status === 204) {
        const newsItemId = +response.url.substring(response.url.lastIndexOf('/') + 1);
        const div = document.querySelector(`#newsItem_${newsItemId}`);
    }
}

const saveTitleButton = document.getElementById('saveTitle')
const titleInput = document.querySelectorAll('div > input.form-control')

function saveTitle(event) {
    const currentTitle = event.target.parentNode.parentNode
    const currentTitleId = currentTitle.id
    const newsItemId = +currentTitleId.substring(currentTitleId.indexOf('_') + 1)

    fetch(`/api/news-items/${newsItemId}`, {
        method: 'PATCH',
        headers: {
            'Content-Type': 'application/json',
            ...getCsrfInfo()
        },
        body: JSON.stringify({title: currentTitle.querySelector('input').value})
    })
        .then((response) => {
            if (response.status === 204) {
                const saveButton = currentTitle.querySelector('button')
                saveButton.style.visibility = 'hidden'
            }
        })
}

saveTitleButton.addEventListener('click', saveTitle)


function titleChanged(event) {
    const currentTitle = event.target.parentNode.parentNode
    const saveButton = currentTitle.querySelector('button')
    saveButton.style.visibility = 'visible'
}

for (const titleNameInput of titleInput) {
    titleNameInput.addEventListener('input', titleChanged)
}

const saveContentButton = document.getElementById('saveContent')
const contentInput = document.querySelectorAll('div > textarea')

function saveContent(event) {
    const currentContent = event.target.parentNode.parentNode
    const currentContentId = currentContent.id
    const newsItemId = +currentContentId.substring(currentContentId.indexOf('_') + 1)

    fetch(`/api/news-items/${newsItemId}`, {
        method: 'PATCH',
        headers: {
            'Content-Type': 'application/json',
            ...getCsrfInfo()
        },
        body: JSON.stringify({content: currentContent.querySelector('input').value})
    })
        .then((response) => {
            if (response.status === 204) {
                const saveButton = currentContent.querySelector('button')
                saveButton.style.visibility = 'hidden'
            }
        })
}

saveContentButton.addEventListener('click', saveContent)


function contentChanged(event) {
    const currentContent = event.target.parentNode.parentNode
    const saveButton = currentContent.querySelector('button')
    saveButton.style.visibility = 'visible'
}

for (const contentNameInput of contentInput) {
    contentNameInput.addEventListener('input', contentChanged)
}
