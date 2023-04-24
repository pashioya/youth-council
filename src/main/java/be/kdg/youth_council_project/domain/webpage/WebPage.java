package be.kdg.youth_council_project.domain.webpage;

import be.kdg.youth_council_project.domain.platform.YouthCouncil;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class WebPage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    @ColumnDefault("false")
    private boolean callForIdeasEnabled;
    @ColumnDefault("false")
    private boolean callToCompleteQuestionnaireEnabled;

    @ColumnDefault("false")
    private boolean activitiesEnabled;
    @ColumnDefault("false")
    private boolean newsItemsEnabled;
    @ColumnDefault("false")
    private boolean actionPointsEnabled;
    @ColumnDefault("false")
    private boolean electionInformationEnabled;

    @OneToMany(mappedBy = "page")
    private List<Section> sections;
    @ManyToOne
    @JoinColumn(name = "youth_council_id")
    private YouthCouncil youthCouncil;
    private boolean isHomepage;

    public WebPage(boolean callForIdeasEnabled, boolean callToCompleteQuestionnaireEnabled, boolean activitiesEnabled, boolean newsItemsEnabled, boolean actionPointsEnabled, boolean electionInformationEnabled) {
        this.callForIdeasEnabled = callForIdeasEnabled;
        this.callToCompleteQuestionnaireEnabled = callToCompleteQuestionnaireEnabled;
        this.activitiesEnabled = activitiesEnabled;
        this.newsItemsEnabled = newsItemsEnabled;
        this.actionPointsEnabled = actionPointsEnabled;
        this.electionInformationEnabled = electionInformationEnabled;
    }
    public void addSection(Section section) {
        this.sections.add(section);
    }
}
