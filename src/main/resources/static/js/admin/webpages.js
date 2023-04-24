const header = document.querySelector('meta[name="_csrf_header"]').content;
const token = document.querySelector('meta[name="_csrf"]').content;

/**
 * Saves the webpage
 * @param sectionId - the id of the section to be saved
 * @param webpageId - the id of the webpage the section is on
 */
const saveSection = (sectionId,webpageId) =>{
    const section = {
        "heading": document.getElementById(sectionId+ "-heading").innerText,
        "body": document.getElementById(sectionId+ "-body").innerText,
    }
    fetch(`/api/webpages/${webpageId}/sections/${sectionId}`, {
        method: "PATCH",
        headers: {
            "Content-Type": "application/json",
            "Accepts": "application/json",
            [header]: token
        },
        body: JSON.stringify(section)
    })
}

/**
 * Adds a section to a webpage
 * @param webpageId - the id of the webpage the section is on
 */
const addSection = (webpageId) =>{
    const section = {
        "heading": document.getElementById("heading").innerText,
        "body": document.getElementById("body").innerText,
    }
    fetch(`/api/webpages/${webpageId}/sections`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Accepts": "application/json",
            [header]: token
        },
        body: JSON.stringify(section)
    }).then(
        response => location.reload()
    )
}

/**
 * Deletes a section from a webpage
 * @param sectionId - the id of the section to be deleted
 * @param webpageId - the id of the webpage the section is on
 */
const deleteSection = (sectionId, webpageId) =>{
    fetch(`/api/webpages/${webpageId}/sections/${sectionId}`, {
        method: "DELETE",
        headers: {
            [header]: token
        }
    }).then(
        response => location.reload()
    )
}

/**
 * Adds a webpage
 */
const addPage = () =>{
    const callForIdeasEnabled = document.getElementById("callForIdeasEnabled").checked;
    const actionPointsEnabled = document.getElementById("actionPointsEnabled").checked;
    const activitiesEnabled = document.getElementById("activitiesEnabled").checked;
    const newsItemsEnabled = document.getElementById("newsItemsEnabled").checked;
    const electionInformationEnabled = document.getElementById("electionInformationEnabled").checked;
    const title = document.getElementById("title").value;

    const webpage = {
        "title": title,
        "callForIdeasEnabled": callForIdeasEnabled,
        "actionPointsEnabled": actionPointsEnabled,
        "activitiesEnabled": activitiesEnabled,
        "newsItemsEnabled": newsItemsEnabled,
        "electionInformationEnabled": electionInformationEnabled
    }
    fetch(`/api/webpages`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Accepts": "application/json",
            [header]: token
        },
        body: JSON.stringify(webpage)
    }).then(
        // go to the new page
        response => response.json().then(data => window.location.replace(`/dashboard/webpages/${data.id}`))
    );
}

/**
 *  Deletes a webpage
 * @param webpageId - the id of the webpage to be deleted
 */
const deletePage = (webpageId) =>{
    fetch(`/api/webpages/${webpageId}`, {
        method: "DELETE",
        headers: {
            [header]: token
        }
    }).then(
        response => window.location.replace(`/dashboard`)
    )
}

const savePage = (webpageId) =>{
    const callForIdeasEnabled = document.getElementById("callForIdeasEnabled").checked;
    const actionPointsEnabled = document.getElementById("actionPointsEnabled").checked;
    const activitiesEnabled = document.getElementById("activitiesEnabled").checked;
    const newsItemsEnabled = document.getElementById("newsItemsEnabled").checked;
    const electionInformationEnabled = document.getElementById("electionInformationEnabled").checked;
    const title = document.getElementById("title").value;

    const webpage = {
        "title": title,
        "callForIdeasEnabled": callForIdeasEnabled,
        "actionPointsEnabled": actionPointsEnabled,
        "activitiesEnabled": activitiesEnabled,
        "newsItemsEnabled": newsItemsEnabled,
        "electionInformationEnabled": electionInformationEnabled
    }
    console.log(webpage)
    fetch(`/api/webpages/${webpageId}`, {
        method: "PATCH",
        headers: {
            "Content-Type": "application/json",
            "Accepts": "application/json",
            [header]: token
        },
        body: JSON.stringify(webpage)
    }).then(
        response => location.reload()
    )
}

