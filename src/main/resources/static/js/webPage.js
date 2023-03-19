


const getWebPage = async (ycId) => {
    const response = await fetch(`/api/youth-councils/${ycId}/webpage`);
    const data = await response.json();
    return data;
}

getWebPage(1).then((webPage) => {
    if(webPage.callForIdeasEnabled) {
        document.getElementById("callForIdeas").style.display = "block";
    }
    if(webPage.actionPointsEnabled) {
        document.getElementById("actionPoints").style.display = "block";
    }
    if(webPage.activitiesEnabled) {
        document.getElementById("activities").style.display = "block";
    }
    if(webPage.newsItemsEnabled) {
        document.getElementById("newsItems").style.display = "block";
    }
    if(webPage.electionInformationEnabled) {
        document.getElementById("electionInformation").style.display = "block";
    }
    if(webPage.adminDashboardEnabled) {
        document.getElementById("adminDashboard").style.display = "block";
    }
});

