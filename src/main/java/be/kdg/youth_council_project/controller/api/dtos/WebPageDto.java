package be.kdg.youth_council_project.controller.api.dtos;

import be.kdg.youth_council_project.domain.webpage.WebPage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class WebPageDto {
    private long id;
    private String title;

    private boolean callForIdeasEnabled;
    private boolean activitiesEnabled;
    private boolean newsItemsEnabled;
    private boolean actionPointsEnabled;
    private boolean electionInformationEnabled;
    private boolean adminDashboardEnabled;
    public WebPageDto(WebPage webPage) {
        this.title = webPage.getTitle();
        this.callForIdeasEnabled = webPage.isCallForIdeasEnabled();
        this.activitiesEnabled = webPage.isActivitiesEnabled();
        this.newsItemsEnabled = webPage.isNewsItemsEnabled();
        this.actionPointsEnabled = webPage.isActionPointsEnabled();
        this.electionInformationEnabled = webPage.isElectionInformationEnabled();
    }
}
