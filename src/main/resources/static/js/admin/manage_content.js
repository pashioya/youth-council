import {deleteEntity, fetchEntities} from "../api/api_facade.js"
import {formatDate} from "../common/utils.js";


const editableContent = ["ideas", "action-points", "activities", "news-items"]


window.onload = () => {
    populateButtons()
    displayContent(editableContent[0])
}


const populateButtons = () => {
    const buttonContainer = document.getElementById("button-container")
    editableContent.forEach((content) => {
        const button = document.createElement("button")
        const classes = ["btn", "btn-outline-primary", "me-3"]
        button.classList.add(...classes)
        button.innerText = content.split("-").join(" ")
        button.addEventListener("click", async () => {
            // Set button active
            const activeButton = document.querySelector(".active")
            if (activeButton) {
                activeButton.classList.remove("active")
            }
            button.classList.add("active")

            await displayContent(content)
        })
        buttonContainer.appendChild(button)
    })
}
const displayContent = async (type) => {
    const contentContainer = document.getElementById("content-container")
    const tableHeader = document.getElementById("table-header")

    contentContainer.innerHTML = ""
    const contentItems = await fetchEntities(type)
    tableHeader.innerHTML = ""
    tableHeader.appendChild(createTableHeader(type))
    contentContainer.append(...createElements(type, contentItems));
}

const createTableHeader = (type) => {
    const tableHeader = document.createElement("tr")
    switch (type) {
        case "ideas":
            tableHeader.innerHTML = `
                <th scope="col">#</th>
                <th scope="col">Created at</th>
                <th scope="col">Theme</th>
                <th scope="col">Author</th>
                <th scope="col" class="d-none d-lg-block">Description</th>
            `
            break;
        case "action-points":
            tableHeader.innerHTML = `
                <th scope="col">#</th>
                <th scope="col">Created at</th>
                <th scope="col">Title</th>
                <th scope="col">Status</th>
                <th scope="col">Linked Ideas</th>
                <th scope="col">Standard Action</th>
            `
            break;
        case "activities":
            tableHeader.innerHTML = `
                <th scope="col">Title</th>
                <th scope="col">Description</th>
                <th scope="col">Author</th>
                <th scope="col">Created at</th>
                <th scope="col">Last modified at</th>
                <th scope="col">Actions</th>
            `
            break;
        case "news-items":
            tableHeader.innerHTML = `
                <th scope="col">Title</th>
                <th scope="col">Description</th>
                <th scope="col">Author</th>
                <th scope="col">Created at</th>
                <th scope="col">Last modified at</th>
                <th scope="col">Actions</th>
            `
            break;
    }
    return tableHeader
}


const createElements = (type, contentItems) => {

    switch (type) {
        case "ideas":
            return createIdeaElements(contentItems)
        case "action-points":
            return createActionPointElements(contentItems)
        case "activities":
            return createActivityElements(contentItems)
        case "news-items":
            return createNewsItemElements(contentItems)
    }

}


const createIdeaElements = (ideas) => {
    const ideaElements = []
    ideas.forEach((idea) => {
        const ideaElement = document.createElement("tr")

        // Id
        const ideaIdElement = document.createElement("td")
        ideaIdElement.innerText = idea.ideaId
        ideaIdElement.classList.add("text-break")
        ideaElement.appendChild(ideaIdElement)

        // Date
        const ideaDateElement = document.createElement("td")
        ideaDateElement.innerText = formatDate(new Date(idea.dateAdded))
        ideaDateElement.classList.add("text-break")
        ideaElement.appendChild(ideaDateElement)

        // Theme
        const ideaThemeElement = document.createElement("td")
        ideaThemeElement.innerText = idea.theme.name
        ideaThemeElement.classList.add("text-break")
        ideaElement.appendChild(ideaThemeElement)

        // Author
        const ideaAuthorElement = document.createElement("td")
        ideaAuthorElement.innerText = idea.author.username
        ideaAuthorElement.classList.add("text-break")
        ideaElement.appendChild(ideaAuthorElement)

        // Description
        const ideaDescriptionElement = document.createElement("td")
        ideaDescriptionElement.innerText = idea.description
        ideaDescriptionElement.classList.add("text-wrap")
        ideaDescriptionElement.style.maxWidth = "300px"
        ideaDescriptionElement.classList.add("d-none")
        ideaDescriptionElement.classList.add("d-lg-table-cell")
        ideaElement.appendChild(ideaDescriptionElement)


        // Delete button
        const ideaButtonElement = document.createElement("td")
        const ideaDeleteButton = document.createElement("button")
        ideaDeleteButton.classList.add("btn")
        ideaDeleteButton.classList.add("btn-danger")
        ideaDeleteButton.innerHTML = '<i class="bi bi-trash"></i>'
        ideaDeleteButton.addEventListener("click", async () => {
            await deleteEntity("ideas", idea.ideaId)
            ideaElement.remove()
        })

        // Edit button
        const ideaEditButton = document.createElement("button")
        ideaEditButton.classList.add("btn")
        ideaEditButton.classList.add("btn-primary")
        ideaEditButton.innerHTML = '<i class="bi bi-pencil"></i>'
        ideaEditButton.addEventListener("click", async () => {
            window.location.href = `/admin/edit_idea/${idea.ideaId}`
        })


        ideaButtonElement.appendChild(ideaDeleteButton)
        ideaButtonElement.appendChild(ideaEditButton)
        ideaElement.appendChild(ideaButtonElement)


        ideaElements.push(ideaElement)
    })
    return ideaElements
}

const createActionPointElements = (actionPoints) => {
    let actionPointElements = []
    actionPoints.forEach((actionPoint) => {
        const actionPointElement = document.createElement("tr")

        // Id
        const actionPointIdElement = document.createElement("td")
        actionPointIdElement.innerText = actionPoint.actionPointId
        actionPointIdElement.classList.add("text-break")
        actionPointElement.appendChild(actionPointIdElement)


        // Date
        const actionPointDateElement = document.createElement("td")
        actionPointDateElement.innerText = formatDate(new Date(actionPoint.createdDate))
        actionPointDateElement.classList.add("text-break")
        actionPointElement.appendChild(actionPointDateElement)

        // Title
        const actionPointTitleElement = document.createElement("td")
        actionPointTitleElement.innerText = actionPoint.title
        actionPointTitleElement.classList.add("text-break")
        actionPointElement.appendChild(actionPointTitleElement)

        // Status
        const actionPointStatusElement = document.createElement("td")
        actionPointStatusElement.innerText = actionPoint.status
        actionPointStatusElement.classList.add("text-break")
        actionPointElement.appendChild(actionPointStatusElement)

        // Linked Ideas
        const actionPointLinkedIdeaElement = document.createElement("td")
        actionPointLinkedIdeaElement.innerText = actionPoint.linkedIdeas.map((idea) => idea.title).join(", ")
        actionPointLinkedIdeaElement.classList.add("text-break")
        actionPointElement.appendChild(actionPointLinkedIdeaElement)

        // Standard Action
        const actionPointStandardActionElement = document.createElement("td")
        actionPointStandardActionElement.innerText = actionPoint.standardAction.name
        actionPointStandardActionElement.classList.add("text-break")
        actionPointElement.appendChild(actionPointStandardActionElement)


        // Delete button
        const actionPointButtonElement = document.createElement("td")
        const actionPointDeleteButton = document.createElement("button")
        actionPointDeleteButton.classList.add("btn")
        actionPointDeleteButton.classList.add("btn-danger")
        actionPointDeleteButton.innerHTML = '<i class="bi bi-trash"></i>'
        actionPointDeleteButton.addEventListener("click", async () => {
            await deleteEntity("action-points", actionPoint.actionPointId)
            actionPointElement.remove()
        })

        // Edit button
        const actionPointEditButton = document.createElement("button")
        actionPointEditButton.classList.add("btn")
        actionPointEditButton.classList.add("btn-primary")
        actionPointEditButton.innerHTML = '<i class="bi bi-pencil"></i>'
        actionPointEditButton.addEventListener("click", async () => {
            window.location.href = `/dashboard/manage-content/action-points/${actionPoint.actionPointId}`
        })


        actionPointButtonElement.appendChild(actionPointDeleteButton)
        actionPointButtonElement.appendChild(actionPointEditButton)
        actionPointElement.appendChild(actionPointButtonElement)


        actionPointElements.push(actionPointElement)
    })

    console.log(actionPointElements)
    return actionPointElements
}

const createActivityElements = (activities) => {
const activityElements = []
    activities.forEach((activity) => {
        const activityElement = document.createElement("li")
        activityElement.classList.add("list-group-item")
        activityElement.innerText = activity.title
        activityElements.push(activityElement)
    })
    return activityElements
}

const createNewsItemElements = (newsItems) => {
const newsItemElements = []
    newsItems.forEach((newsItem) => {
        const newsItemElement = document.createElement("li")
        newsItemElement.classList.add("list-group-item")
        newsItemElement.innerText = newsItem.title
        newsItemElements.push(newsItemElement)
    })
    return newsItemElements
}
