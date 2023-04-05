package be.kdg.youth_council_project.domain.platform;

import be.kdg.youth_council_project.domain.platform.Municipality;
import be.kdg.youth_council_project.domain.platform.style.Style;
import be.kdg.youth_council_project.domain.platform.youth_council_items.questionnaire.Questionnaire;
import be.kdg.youth_council_project.util.WebPage;
import javax.persistence.*;
import lombok.*;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class YouthCouncil {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String slug;
    private String name;
    private String logo;

    @OneToOne
    @JoinColumn(name="municipality_id")
    private Municipality municipality;

    @OneToOne
    @JoinColumn(name = "style_id")
    private Style style;


    @OneToOne
    private Questionnaire questionnaire;

    @OneToOne
    private WebPage homePage;

    public String getMunicipalityName(){
        return municipality.getName();
    }


}
