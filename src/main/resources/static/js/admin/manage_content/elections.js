import {updateEntity} from "../../api/api_facade.js";


addEventListener("submit", (event) => {
    event.preventDefault();
    const form = event.target;
    updateElection(form);
})

const updateElection = async (target) => {
    const title = document.getElementById("title");
    const description = document.getElementById("description");
    const startDate = document.getElementById("startDate");
    const endDate = document.getElementById("endDate");
    const location = document.getElementById("location");
    const isActive = document.getElementById("isActive");
    const electionId = window.location.pathname.split("/").pop();
    if (startDate.value > endDate.value) {
        alert("Start date must be before end date");
        return;
    }
    const data = {
        title: title.value,
        description: description.value,
        location: location.value,
        startDate: startDate.value,
        endDate: endDate.value,
        active: isActive.checked
    };
    await updateEntity("elections", data);
}



