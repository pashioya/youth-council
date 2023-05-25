const tableBody = document.getElementById("webpage-table-body");
const templatesCard = document.getElementById("templates-card");
fetch("/api/webpage-templates",
    {
        headers: {
            Accept: "application/json"
        }
    }).then(response => {
    if (response.status === 200) {
        response.json().then(handleTemplates)
    } else {
        console.error(response.status)
            }
});

function handleTemplates(templates) {
    if (templates.length === 0) {
        templatesCard.innerHTML = "<h5>No templates have been added yet.</h5>"
    } else {
        for (let template of templates) {
            tableBody.innerHTML += `
                <tr class="table-row">
                    <td class="text-primary">${template.title}</td>
                    <td>
                        <a class="btn btn-outline-dark" id="edit-template-${template.id}">Edit</a>
                        <a class="btn btn-dark" id="delete-template-${template.id}">Delete</a>
                    </td>
                </tr>`
        }
    }
}
