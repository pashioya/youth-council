package be.kdg.youth_council_project.domain.platform;

import be.kdg.youth_council_project.domain.platform.style.Style;
import be.kdg.youth_council_project.domain.platform.youthCouncilItems.ActionPoint;
import be.kdg.youth_council_project.domain.platform.youthCouncilItems.Activity;
import be.kdg.youth_council_project.domain.platform.youthCouncilItems.Idea;
import be.kdg.youth_council_project.domain.platform.youthCouncilItems.SocialMediaLink;
import jakarta.persistence.*;
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
    private List<Membership> memberships;
    @OneToMany(mappedBy = "youthCouncil")
    private List<Activity> activities;
    @OneToMany(mappedBy = "youthCouncil")
    private List<Idea> ideas;
    @OneToMany(mappedBy = "youthCouncil")
    private List<ActionPoint> actionPoints;
    @OneToMany(mappedBy = "youthCouncil")
    private List<SocialMediaLink> socialMediaLinks;

    public String getMunicipalityName(){
        return municipality.getName();
    }


}
