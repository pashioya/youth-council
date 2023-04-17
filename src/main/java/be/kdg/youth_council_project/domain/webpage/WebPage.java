package be.kdg.youth_council_project.domain.webpage;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "page_type")
public abstract class WebPage {
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

    public WebPage(boolean callForIdeasEnabled, boolean callToCompleteQuestionnaireEnabled, boolean activitiesEnabled, boolean newsItemsEnabled, boolean actionPointsEnabled, boolean electionInformationEnabled) {
        this.callForIdeasEnabled = callForIdeasEnabled;
        this.callToCompleteQuestionnaireEnabled = callToCompleteQuestionnaireEnabled;
        this.activitiesEnabled = activitiesEnabled;
        this.newsItemsEnabled = newsItemsEnabled;
        this.actionPointsEnabled = actionPointsEnabled;
        this.electionInformationEnabled = electionInformationEnabled;
    }
}
