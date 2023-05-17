package be.kdg.youth_council_project.domain.platform;

import be.kdg.youth_council_project.domain.platform.style.Style;
import be.kdg.youth_council_project.domain.platform.youth_council_items.ActionPoint;
import be.kdg.youth_council_project.domain.platform.youth_council_items.Activity;
import be.kdg.youth_council_project.domain.platform.youth_council_items.Idea;
import be.kdg.youth_council_project.domain.platform.youth_council_items.NewsItem;
import be.kdg.youth_council_project.domain.platform.youth_council_items.questionnaire.Questionnaire;
import be.kdg.youth_council_project.domain.webpage.WebPage;
import lombok.*;

import javax.persistence.*;
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

    @OneToOne
    @ToString.Exclude
    private WebPage homePage;
    @OneToMany
    @ToString.Exclude
    private List<WebPage> informativePages;

    @OneToMany(mappedBy="membershipId.youthCouncil")
    @ToString.Exclude
    private List<Membership> members;

    @OneToMany(mappedBy = "youthCouncil", orphanRemoval = true, cascade = CascadeType.PERSIST)
    List<Idea> ideas;

    @OneToMany(mappedBy = "youthCouncil", orphanRemoval = true, cascade = CascadeType.PERSIST)
    List<Activity> activities;

    @OneToMany(mappedBy = "youthCouncil", orphanRemoval = true, cascade = CascadeType.PERSIST)
    List<ActionPoint> actionPoints;

    @OneToMany(mappedBy = "youthCouncil", orphanRemoval = true, cascade = CascadeType.PERSIST)
    List<NewsItem> newsItems;

    @OneToMany(mappedBy = "youthCouncil", orphanRemoval = true, cascade = CascadeType.PERSIST)
    List<WebPage> webPages;

    public String getMunicipalityName(){
        return municipality.getName();
    }
}
