package be.kdg.youth_council_project.domain.platform.youthCouncilItems;

import be.kdg.youth_council_project.domain.platform.YouthCouncil;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
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
