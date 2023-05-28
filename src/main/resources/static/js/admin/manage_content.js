import {deleteEntity, fetchEntities} from "../api/api_facade.js"
import {formatDate} from "../common/utils.js";


const editableContent = ["ideas", "action-points", "activities", "news-items", "elections"]


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
                <th scope="col">#</th>
                <th scope="col">Starts at</th>
                <th scope="col">Ends at</th>
                <th scope="col">Title</th>
                <th scope="col">Description</th>
            `
            break;
        case "news-items":
            tableHeader.innerHTML = `
                <th scope="col">#</th> 
                <th scope="col">Title</th>
                <th scope="col">Description</th>
                <th scope="col">Author</th>
                <th scope="col">Created at</th>
            `
            break;
        case "elections":
            tableHeader.innerHTML = `
                <th scope="col">#</th>  
                <th scope="col">Title</th>
                <th scope="col">Description</th>
                <th scope="col">Location</th>
                <th scope="col">Starts at</th>
                <th scope="col">Ends at</th>
                <th scope="col">is Active</th>
            `
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
        case "elections":
            return createElectionElements(contentItems)
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



        ideaButtonElement.appendChild(ideaDeleteButton)
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
        for (let i = 0; i < actionPoint.linkedIdeas.length; i++) {
            const idea = actionPoint.linkedIdeas[i]
            const ideaElement = document.createElement("li")
            ideaElement.innerText = idea.title.length > 70 ? idea.title.substring(0, 70) + "..." : idea.title
            ideaElement.classList.add("text-break")
            actionPointLinkedIdeaElement.appendChild(ideaElement)
            if (i < actionPoint.linkedIdeas.length - 1) {
                actionPointLinkedIdeaElement.appendChild(document.createElement("br"))
            }
        }
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
        const activityElement = document.createElement("tr")

        // Id
        const activityIdElement = document.createElement("td")
        activityIdElement.innerText = activity.id
        activityIdElement.classList.add("text-break")
        activityElement.appendChild(activityIdElement)

        // StartDate
        const activityStartDateElement = document.createElement("td")
        activityStartDateElement.innerText = formatDate(new Date(activity.startDate))
        activityStartDateElement.classList.add("text-break")
        activityElement.appendChild(activityStartDateElement)

        // EndDate
        const activityEndDateElement = document.createElement("td")
        activityEndDateElement.innerText = formatDate(new Date(activity.endDate))
        activityEndDateElement.classList.add("text-break")
        activityElement.appendChild(activityEndDateElement)

        // Title
        const activityTitleElement = document.createElement("td")
        activityTitleElement.innerText = activity.name
        activityTitleElement.classList.add("text-break")
        activityElement.appendChild(activityTitleElement)

        // Description
        const activityDescriptionElement = document.createElement("td")
        activityDescriptionElement.innerText = activity.description
        activityDescriptionElement.classList.add("text-break")
        activityElement.appendChild(activityDescriptionElement)

        // Delete button
        const activityButtonElement = document.createElement("td")
        const activityDeleteButton = document.createElement("button")
        activityDeleteButton.classList.add("btn")
        activityDeleteButton.classList.add("btn-danger")
        activityDeleteButton.innerHTML = '<i class="bi bi-trash"></i>'
        activityDeleteButton.addEventListener("click", async () => {
            await deleteEntity("activities", activity.id)
            activityElement.remove()
        })

        activityButtonElement.appendChild(activityDeleteButton)
        activityElement.appendChild(activityButtonElement)

        activityElements.push(activityElement)
    })
    return activityElements
}

const createNewsItemElements = (newsItems) => {
    console.log(newsItems)
    const newsItemElements = []
    newsItems.forEach((newsItem) => {
        const newsItemElement = document.createElement("tr")

        // Id
        const newsItemIdElement = document.createElement("td")
        newsItemIdElement.innerText = newsItem.id
        newsItemIdElement.classList.add("text-break");
        newsItemElement.appendChild(newsItemIdElement);


        // Title
        const newsItemTitleElement = document.createElement("td")
        newsItemTitleElement.innerText = newsItem.title
        newsItemTitleElement.classList.add("text-break");
        newsItemElement.appendChild(newsItemTitleElement);

        // Description
        const newsItemDescriptionElement = document.createElement("td")
        newsItemDescriptionElement.innerText = newsItem.content
        newsItemDescriptionElement.classList.add("text-break");
        newsItemElement.appendChild(newsItemDescriptionElement);

        // Author
        const newsItemAuthorElement = document.createElement("td")
        newsItemAuthorElement.innerText = newsItem.author
        newsItemAuthorElement.classList.add("text-break");
        newsItemElement.appendChild(newsItemAuthorElement);

        // Created at
        const newsItemCreatedAtElement = document.createElement("td")
        newsItemCreatedAtElement.innerText = formatDate(new Date(newsItem.createdDate))
        newsItemCreatedAtElement.classList.add("text-break");
        newsItemElement.appendChild(newsItemCreatedAtElement);

        // Delete button
        const newsItemButtonElement = document.createElement("td")
        const newsItemDeleteButton = document.createElement("button")
        newsItemDeleteButton.classList.add("btn")
        newsItemDeleteButton.classList.add("btn-danger")
        newsItemDeleteButton.innerHTML = '<i class="bi bi-trash"></i>'
        newsItemDeleteButton.addEventListener("click", async () => {
                await deleteEntity("news-items", newsItem.id)
                newsItemElement.remove()
            }
        )
        newsItemButtonElement.appendChild(newsItemDeleteButton)
        newsItemElement.appendChild(newsItemButtonElement)

        newsItemElements.push(newsItemElement);
    })
    return newsItemElements
}

const createElectionElements = (elections) => {
    const electionElements = []
    elections.forEach((election) => {
        console.log(election)
        const electionElement = document.createElement("tr")

        // Id
        const electionIdElement = document.createElement("td")
        electionIdElement.innerText = election.id
        electionIdElement.classList.add("text-break");
        electionElement.appendChild(electionIdElement);

        // Title
        const electionTitleElement = document.createElement("td")
        electionTitleElement.innerText = election.title
        electionTitleElement.classList.add("text-break");
        electionElement.appendChild(electionTitleElement);

        // Description
        const electionDescriptionElement = document.createElement("td")
        electionDescriptionElement.innerText = election.description
        electionDescriptionElement.classList.add("text-break");
        electionElement.appendChild(electionDescriptionElement);

        // Location
        const electionLocationElement = document.createElement("td")
        electionLocationElement.innerText = election.location
        electionLocationElement.classList.add("text-break");
        electionElement.appendChild(electionLocationElement);

        // StartDate
        const electionStartDateElement = document.createElement("td")
        electionStartDateElement.innerText = formatDate(new Date(election.startDate))
        electionStartDateElement.classList.add("text-break");
        electionElement.appendChild(electionStartDateElement);

        // EndDate
        const electionEndDateElement = document.createElement("td")
        electionEndDateElement.innerText = formatDate(new Date(election.endDate))
        electionEndDateElement.classList.add("text-break");
        electionElement.appendChild(electionEndDateElement);

        // Is Active
        const electionIsActiveElement = document.createElement("td")
        electionIsActiveElement.innerHTML = election.active ? '<i class="bi bi-check"></i>' : '<i class="bi' +
            ' bi-x"></i>'
        electionIsActiveElement.classList.add("text-break");
        electionElement.appendChild(electionIsActiveElement);

        // Delete button
        const electionButtonElement = document.createElement("td")
        const electionDeleteButton = document.createElement("button")
        electionDeleteButton.classList.add("btn")
        electionDeleteButton.classList.add("btn-danger")
        electionDeleteButton.innerHTML = '<i class="bi bi-trash"></i>'
        electionDeleteButton.addEventListener("click", async () => {
                await deleteEntity("elections", election.id)
                electionElement.remove()
            }
        )
        // Edit button
        const electionEditButton = document.createElement("button")
        electionEditButton.classList.add("btn")
        electionEditButton.classList.add("btn-primary")
        electionEditButton.innerHTML = '<i class="bi bi-pencil"></i>'
        electionEditButton.addEventListener("click", async () => {
            window.location.href = `/dashboard/manage-content/elections/${election.id}`
        })

        electionButtonElement.appendChild(electionEditButton)
        electionButtonElement.appendChild(electionDeleteButton)
        electionElement.appendChild(electionButtonElement)

        electionElements.push(electionElement);
    })
    return electionElements
}
