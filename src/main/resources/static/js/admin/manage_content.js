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
    const contentList = document.createElement("ul")
    const contentItems = await fetchEntities(type)
    console.log(contentItems)
    contentList.append(...createElements(type, contentItems));
    contentContainer.appendChild(contentList)
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
        const ideaElement = document.createElement("li")
        ideaElement.classList.add("list-group-item")
        ideaElement.id = idea.id
        ideaElement.innerText = idea.title
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
