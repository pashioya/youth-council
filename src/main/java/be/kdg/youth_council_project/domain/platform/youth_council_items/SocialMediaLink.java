package be.kdg.youth_council_project.domain.platform.youth_council_items;

import be.kdg.youth_council_project.domain.platform.YouthCouncil;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class SocialMediaLink {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private SocialMedia socialMedia;
    private String link;

    @ManyToOne
    @JoinColumn(name="youth_council_id")
    private YouthCouncil youthCouncil;

    public SocialMediaLink(SocialMedia socialMedia) {
        this.socialMedia = socialMedia;
    }
}
