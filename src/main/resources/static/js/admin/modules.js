import {getWebPage} from "../api/web_page_api.js";

const callForIdeasEnabled = document.getElementById("callForIdeasEnabled");
const actionPointsEnabled = document.getElementById("actionPointsEnabled");
const activitiesEnabled = document.getElementById("activitiesEnabled");
const newsItemsEnabled = document.getElementById("newsItemsEnabled");
const electionInformationEnabled = document.getElementById("electionInformationEnabled");

getWebPage(1).then((webPage) => {
    if(webPage.callForIdeasEnabled) {
        callForIdeasEnabled.checked=true;
    }
    if(webPage.actionPointsEnabled) {
        actionPointsEnabled.checked=true;
    }
    if(webPage.activitiesEnabled) {
        activitiesEnabled.checked=true;
    }
    if(webPage.newsItemsEnabled) {
        newsItemsEnabled.checked=true;
    }
    if(webPage.electionInformationEnabled) {
        electionInformationEnabled.checked=true;
    }
});

document.getElementById("saveModules").addEventListener("click", (event) => {
    const header = document.querySelector('meta[name="_csrf_header"]').content;
    const token = document.querySelector('meta[name="_csrf"]').content;

    fetch(`/api/webpages`, {
        method: "PATCH",
        headers: {
            "Content-Type": "application/json",
            [header]: token
        },
        body: JSON.stringify({
            callForIdeasEnabled: callForIdeasEnabled.checked,
            actionPointsEnabled: actionPointsEnabled.checked,
            activitiesEnabled: activitiesEnabled.checked,
            newsItemsEnabled: newsItemsEnabled.checked,
            electionInformationEnabled: electionInformationEnabled.checked
        })
    })
        .then(response => {
            // reload page
            location.reload();
        });

});
