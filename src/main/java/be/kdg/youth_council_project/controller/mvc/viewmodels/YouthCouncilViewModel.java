package be.kdg.youth_council_project.controller.mvc.viewmodels;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class YouthCouncilViewModel {
    private Long id;
    private String name;
    private String logo;
    private String municipalityName;

//    private List<ActivityViewModel> activities;
//
//    private List<IdeaViewModel> ideas;
//    private List<ActionPointViewModel> actionPoints;
//    private List<SocialMediaLinkViewModel> socialMediaLinks;
    private WebPageViewModel homePage;
}
