import {getCsrfInfo} from "../common/utils.js";

const form = document.getElementById("submitForm");
const name = document.getElementById("name");
const description = document.getElementById("description");
const startDate = document.getElementById("start-date-time");
const endDate = document.getElementById("end-date-time");
const closeButton = document.getElementsByClassName("btn-close");

form.addEventListener("submit", trySubmitForm);

function trySubmitForm(event) {

    if (!form.checkValidity()) {
        event.preventDefault()
        event.stopPropagation()
    }


    if (startDate.value > endDate.value) {
        alert("Start date must be before end date");
        return;
    }

    let formData = new FormData()
    formData.append('activity', new Blob([JSON.stringify({
        "name": name.value,
        "description": description.value,
        "startDate": startDate.value,
        "endDate": endDate.value
    })], {
        type: "application/json"
    }), "activity");
    fetch('/api/activities', {
        method: "POST",
        headers: {
            'Accept': 'application/json',
            ...getCsrfInfo()
        },
        body: formData
    }).then(response => {
        if (response.status === 201) {
            closeButton[0].click();
            response.json().then(data => {
                renderNewActivity(data);
            });
        } else {
            alert("Something went wrong");
        }
    });
}

function renderNewActivity(activity) {
    const activityList = document.getElementById("event-list");
    const activityItem = document.createElement("div");

    const month = new Date(activity.startDate).toLocaleString('default', { month: 'long' });
    const smallDay = activity.startDate.substring(8, 10);

    activityItem.classList.add("bg-light");
    activityItem.classList.add("event");
    activityItem.classList.add("shadow");
    activityItem.classList.add("rounded-3");
    activityItem.classList.add("mt-2");
    activityItem.classList.add("mb-2");
    activityItem.innerHTML = `
                    <div class="container-fluid py-3 row">
                        <div class="date-container rounded text-center col-3">
                            <div class="start-date row">
                                <span class="bg-dark text-white rounded-top fs-6" th:text="${activity.startDate})}">${month}</span>
                                <span class="bg-dark bg-opacity-10 rounded-bottom  fs-6" th:text="${activity.startDate}">${smallDay}</span>
                            </div>
                        </div>
                        <div class="col-9">
                            <div class="row">
                                <h3 class="text-uppercase">
                                    <strong th:text="${activity.name}">${activity.name}</strong>
                                </h3>
                                <!--            start date -->
                                <span>
                            <span class="text-muted date">
                                <small>
                                    Start: <span th:text="${activity.startDate}">${activity.startDate}</span>
                                </small>
                            </span>
                        </span>
                                <!--            end date -->
                                <span>
                            <span class="text-muted date">
                                <small>
                                    End: <span th:text="${activity.endDate}">${activity.endDate}</span>
                                </small>
                            </span>
                        </span>
                                <span th:text="${activity.description}">${activity.description}</span>
                            </div>
                        </div>
                    </div>
    `;
    activityList.appendChild(activityItem);
    form.classList.add('was-validated')
}
