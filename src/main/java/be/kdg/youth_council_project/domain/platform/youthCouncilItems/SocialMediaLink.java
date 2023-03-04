package be.kdg.youth_council_project.domain.platform.youthCouncilItems;

import be.kdg.youth_council_project.domain.platform.YouthCouncil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SocialMediaLink {
    private Long id;
    private SocialMedia socialMedia;
    private String link;
    private YouthCouncil youthCouncil;
}
