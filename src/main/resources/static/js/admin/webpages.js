const header = document.querySelector('meta[name="_csrf_header"]').content;
const token = document.querySelector('meta[name="_csrf"]').content;
const saveSection = (sectionId,webpageId) =>{
    const section = {
        "heading": document.getElementById(sectionId+ "-heading").innerText,
        "body": document.getElementById(sectionId+ "-body").innerText,

    }
    fetch(`/api/webpages/${webpageId}/sections/${sectionId}`, {
        method: "PATCH",
        headers: {
            "Content-Type": "application/json",
            [header]: token
        },
        body: JSON.stringify(section)
    })
}


const addSection = (webpageId) =>{
    const section = {
        "heading": document.getElementById("heading").innerText,
        "body": document.getElementById("body").innerText,
    }
    fetch(`/api/webpages/${webpageId}/sections`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            [header]: token
        },
        body: JSON.stringify(section)
    }).then(
        response => location.reload()
    )
}

const deleteSection = (sectionId, webpageId) =>{
    fetch(`/api/webpages/${webpageId}/sections/${sectionId}`, {
        method: "DELETE",
        headers: {
            "Content-Type": "application/json",
            [header]: token
        }
    }).then(
        response => location.reload()
    )
}