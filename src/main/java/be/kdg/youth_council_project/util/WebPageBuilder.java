package be.kdg.youth_council_project.util;

public class WebPageBuilder {
    // create builder for webpage
    private boolean callForIdeas = false;
    private boolean callToCompleteQuestionnaire = false;
    private boolean activitiesEnabled = false;
    private boolean newsItemsEnabled = false;
    private boolean actionPointsEnabled = false;
    private boolean electionInformationEnabled = false;

    public WebPageBuilder enableCallForIdeas(){
        this.callForIdeas = true;
        return this;
    }
    public WebPageBuilder enableCallToCompleteQuestionnaire(){
        this.callToCompleteQuestionnaire = true;
        return this;
    }
    public WebPageBuilder enableActivities(){
        this.activitiesEnabled = true;
        return this;
    }
    public WebPageBuilder enableNewsItems(){
        this.newsItemsEnabled = true;
        return this;
    }
    public WebPageBuilder enableActionPoints(){
        this.actionPointsEnabled = true;
        return this;
    }
    public WebPageBuilder enableElectionInformation(){
        this.electionInformationEnabled = true;
        return this;
    }
    public WebPageBuilder withEverything(){
        this.callForIdeas = true;
        this.callToCompleteQuestionnaire = true;
        this.activitiesEnabled = true;
        this.newsItemsEnabled = true;
        this.actionPointsEnabled = true;
        this.electionInformationEnabled = true;
        return this;
    }

    public WebPage build(){
        WebPage webPage = new WebPage();
        webPage.setCallForIdeasEnabled(this.callForIdeas);
        webPage.setCallToCompleteQuestionnaireEnabled(this.callToCompleteQuestionnaire);
        webPage.setActivitiesEnabled(this.activitiesEnabled);
        webPage.setNewsItemsEnabled(this.newsItemsEnabled);
        webPage.setActionPointsEnabled(this.actionPointsEnabled);
        webPage.setElectionInformationEnabled(this.electionInformationEnabled);
        return webPage;
    }


}
