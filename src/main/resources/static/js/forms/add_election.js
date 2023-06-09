import {getCsrfInfo} from "../common/utils.js";

const title = document.getElementById('election-title');
const description = document.getElementById('election-description');
const location = document.getElementById('election-location');
const startDate = document.getElementById('election-start-date');
const endDate = document.getElementById('election-end-date');
const isActive = document.getElementById('election-is-active');
const form = document.getElementById('submit-form');


/**
 * This function is called when the form is submitted.
 * It checks if the form is valid, and if so, it sends a POST request to the server.
 * @param event
 */
const trySubmitForm = (event) => {
    if (!form.checkValidity()) {
        event.preventDefault();
        event.stopPropagation();
    }


    if (startDate.value > endDate.value) {
        alert('Start date must be before end date');
        return;
    }

    const formData = JSON.stringify({
        title: title.value,
        description: description.value,
        location: location.value,
        startDate: startDate.value,
        endDate: endDate.value,
        isActive: isActive.checked
    });
    console.log(formData);
    fetch('/api/elections', {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            ...getCsrfInfo()
        },
        body: JSON.stringify({
            title: title.value,
            description: description.value,
            location: location.value,
            startDate: startDate.value,
            endDate: endDate.value,
            active: isActive.checked
        })
    }).then(response => {
        if (response.status === 201) {
            window.reload();
        } else {
            alert('Something went wrong');
        }
    });
}

form.addEventListener('submit', trySubmitForm);
