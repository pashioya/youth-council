package be.kdg.youth_council_project.domain.platform;

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

    @ToString.Exclude
    @Lob
    private byte[] logo;

    @OneToOne
    @JoinColumn(name="municipality_id")
    private Municipality municipality;


    @OneToOne
    @ToString.Exclude
    private WebPage homePage;
    @OneToMany
    @ToString.Exclude
    private List<WebPage> informativePages;

    @OneToMany(mappedBy="membershipId.youthCouncil")
    @ToString.Exclude
    private List<Membership> members;

    public String getMunicipalityName(){
        return municipality.getName();
    }
}
