package be.kdg.youth_council_project.controller.mvc.viewmodels;

import java.util.List;

public class WebPageViewModel {
    private Long id;
    private String title;
    private boolean callForIdeasEnabled;
    private boolean callToCompleteQuestionnaireEnabled;
    private boolean activitiesEnabled;
    private boolean newsItemsEnabled;
    private boolean actionPointsEnabled;
    private boolean electionInformationEnabled;
    private List<SectionViewModel> sections;
}
