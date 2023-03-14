package be.kdg.youth_council_project.controller.mvc.viewmodels;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class YouthCouncilViewModel {
    private Long id;
    private String name;
    private String logo;
    private String municipalityName;
    private WebPageViewModel homePage;
}
