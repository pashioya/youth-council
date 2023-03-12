package be.kdg.youth_council_project.controller.mvc.viewmodels;

import be.kdg.youth_council_project.util.WebPage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class WebPageViewModel {
    private boolean callForIdeasEnabled;
    private boolean callToCompleteQuestionnaireEnabled;
    private boolean activitiesEnabled;
    private boolean newsItemsEnabled;
    private boolean actionPointsEnabled;
    private boolean electionInformationEnabled;

    public WebPageViewModel(WebPage webPage) {
        this.callForIdeasEnabled = webPage.isCallForIdeasEnabled();
        this.callToCompleteQuestionnaireEnabled = webPage.isCallToCompleteQuestionnaireEnabled();
        this.activitiesEnabled = webPage.isActivitiesEnabled();
        this.newsItemsEnabled = webPage.isNewsItemsEnabled();
        this.actionPointsEnabled = webPage.isActionPointsEnabled();
        this.electionInformationEnabled = webPage.isElectionInformationEnabled();
    }
}
