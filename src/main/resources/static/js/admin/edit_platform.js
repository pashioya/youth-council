const header = document.querySelector('meta[name="_csrf_header"]').content;
const token = document.querySelector('meta[name="_csrf"]').content;

/**
 * Adds an email to the list of emails of the youth council admins
 * @param ycId the id of the youth council
 */
const submitForm = (ycId) => {
    const form = document.getElementById("submitForm");
    if (!form.checkValidity()) {
        form.reportValidity();
        return;
    }
    const email = document.getElementById("email").value;
    fetch(`/api/admin/youth-councils/${ycId}/admins`, {
        method: "POST",
        headers: {
            'Content-Type': 'application/json',
            [header]: token
        },
        body: email
    }).then(() => {
        window.location.reload();
    })
}
