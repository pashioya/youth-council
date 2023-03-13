package be.kdg.youth_council_project.domain.platform.youthCouncilItems;

import be.kdg.youth_council_project.domain.platform.YouthCouncil;
import javax.persistence.*;
import lombok.*;

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
}
