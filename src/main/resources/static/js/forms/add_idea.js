/**
 * @type {HTMLFormElement}
 */
const form = document.getElementById("submitForm");
const theme = document.getElementById("theme");
const description = document.getElementById("description");
const submitButton = document.querySelector("#submitForm > div > button");

submitButton.addEventListener("click", trySubmitForm);

function trySubmitForm(event) {
    event.preventDefault();

    const header = document.querySelector('meta[name="_csrf_header"]').content;
    const token = document.querySelector('meta[name="_csrf"]').content;

        fetch('/api/youth-councils/1/ideas', {
            method: "POST",
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                [header]: token
            },
            body: JSON.stringify({
                "description": description.value,
                "images": ["bald.jpg"],
                "themeId": theme.value,
            })
        }).then(response => {
            if (response.status === 201) {
                form.reset();
                form.classList.remove('was-validated');
            }
        });
}

