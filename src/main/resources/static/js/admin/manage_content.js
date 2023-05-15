import { fetchEntities } from "../api/api_facade.js"


const editableContent = ["ideas", "action-points","activities","news-items"]


window.onload = () => {
    populateButtons()
    displayContent(editableContent[0])
}


const populateButtons = () => {
    const buttonContainer = document.getElementById("button-container")
    editableContent.forEach((content) => {
        const button = document.createElement("button")
        button.classList.add("btn")
        button.classList.add("btn-primary")
        button.classList.add("me-3")
        button.innerText = content
        button.addEventListener("click", async () => {
            await displayContent(content)
        })
        buttonContainer.appendChild(button)
    })
}

const displayContent = async (type) => {
    const contentContainer = document.getElementById("content-container")
    contentContainer.innerHTML = ""
    const contentItems = await fetchEntities(type)
    contentContainer.append(...createElements(type, contentItems));
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
        console.log(idea)
        const ideaElement = document.createElement("tr")

        // Id
        const ideaIdElement = document.createElement("td")
        ideaIdElement.innerText = idea.ideaId
        ideaIdElement.classList.add("text-break")
        ideaElement.appendChild(ideaIdElement)

        // Description
        const ideaDescriptionElement = document.createElement("td")
        ideaDescriptionElement.innerText = idea.description
        ideaDescriptionElement.classList.add("text-break")

        // Date
        const ideaDateElement = document.createElement("td")
        ideaDateElement.innerText = idea.dateAdded
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


        ideaElement.classList.add("cursor-pointer")
        ideaElement.appendChild(ideaDescriptionElement)


        // Delete button
        const ideaDeleteElement = document.createElement("td")
        const ideaDeleteButton = document.createElement("button")
        ideaDeleteButton.classList.add("btn")
        ideaDeleteButton.classList.add("btn-danger")
        ideaDeleteButton.innerText = "Delete"
        ideaDeleteButton.addEventListener("click", async () => {
            await fetch(`/api/ideas/${idea.ideaId}`, {
                method: "DELETE"
            })
            await displayContent("ideas")
        })
        ideaDeleteElement.appendChild(ideaDeleteButton)
        ideaElement.appendChild(ideaDeleteElement)

        // Edit button
        const ideaEditElement = document.createElement("td")
        const ideaEditButton = document.createElement("button")
        ideaEditButton.classList.add("btn")
        ideaEditButton.classList.add("btn-primary")
        ideaEditButton.innerText = "Edit"
        ideaEditButton.addEventListener("click", async () => {
            window.location.href = `/admin/edit_idea/${idea.ideaId}`
        })
        ideaEditElement.appendChild(ideaEditButton)
        ideaElement.appendChild(ideaEditElement)


        ideaElements.push(ideaElement)
    })
    return ideaElements
}

const createActionPointElements = (actionPoints) => {
    let actionPointElements = []
    actionPoints.forEach((actionPoint) => {
        const actionPointElement = document.createElement("li")
        actionPointElement.classList.add("list-group-item")
        actionPointElement.innerText = actionPoint.title
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
