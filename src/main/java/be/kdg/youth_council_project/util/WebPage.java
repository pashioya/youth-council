package be.kdg.youth_council_project.util;


import be.kdg.youth_council_project.domain.platform.Section;
import be.kdg.youth_council_project.domain.platform.YouthCouncil;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;

@Entity
@Table(name = "webpage")
@Getter
@Setter
public class WebPage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;
    @Transient
    private List<Section> sections;

    private boolean callForIdeasEnabled;
    private boolean callToCompleteQuestionnaireEnabled;
    private boolean activitiesEnabled;
    private boolean newsItemsEnabled;
    private boolean actionPointsEnabled;
    private boolean electionInformationEnabled;

    @OneToOne
    private YouthCouncil youthCouncil;

}
