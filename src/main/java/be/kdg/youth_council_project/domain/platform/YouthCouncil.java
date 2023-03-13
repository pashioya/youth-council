package be.kdg.youth_council_project.domain.platform;

import be.kdg.youth_council_project.domain.platform.style.Style;
import be.kdg.youth_council_project.domain.platform.youthCouncilItems.ActionPoint;
import be.kdg.youth_council_project.domain.platform.youthCouncilItems.Activity;
import be.kdg.youth_council_project.domain.platform.youthCouncilItems.Idea;
import be.kdg.youth_council_project.domain.platform.youthCouncilItems.SocialMediaLink;
import be.kdg.youth_council_project.domain.platform.youthCouncilItems.questionnaire.Questionnaire;
import be.kdg.youth_council_project.util.WebPage;
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
    private String name;
    private String logo;

    @OneToOne
    private Municipality municipality;

    @OneToOne
    @JoinColumn(name = "style_id")
    private Style style;
    @OneToMany(mappedBy = "youthCouncil")
    @ToString.Exclude
    private List<Membership> memberships;
    @OneToMany(mappedBy = "youthCouncil")
    @ToString.Exclude
    private List<Activity> activities;
    @OneToMany(mappedBy = "youthCouncil")
    @ToString.Exclude
    private List<Idea> ideas;
    @OneToMany(mappedBy = "youthCouncil")
    @ToString.Exclude
    private List<ActionPoint> actionPoints;
    @OneToMany(mappedBy = "youthCouncil")
    @ToString.Exclude
    private List<SocialMediaLink> socialMediaLinks;

    @OneToOne
    private Questionnaire questionnaire;

    @OneToOne
    private WebPage homePage;

    public String getMunicipalityName(){
        return municipality.getName();
    }


}
