package be.kdg.youth_council_project.domain.platform;

import be.kdg.youth_council_project.domain.platform.style.Style;
import be.kdg.youth_council_project.domain.platform.youth_council_items.questionnaire.Questionnaire;
import be.kdg.youth_council_project.domain.webpage.HomePage;
import be.kdg.youth_council_project.domain.webpage.WebPage;
import javax.persistence.*;
import lombok.*;

import java.util.List;

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

    @Lob
    @ToString.Exclude
    private byte[] logo;

    @OneToOne
    @JoinColumn(name="municipality_id")
    private Municipality municipality;

    @OneToOne
    @JoinColumn(name = "style_id")
    private Style style;

    @OneToOne
    private Questionnaire questionnaire;

    @OneToOne(mappedBy="youthCouncil")
    @ToString.Exclude
    private HomePage homePage;

    @OneToMany(mappedBy="membershipId.youthCouncil")
    @ToString.Exclude
    private List<Membership> members;

    public String getMunicipalityName(){
        return municipality.getName();
    }
}
