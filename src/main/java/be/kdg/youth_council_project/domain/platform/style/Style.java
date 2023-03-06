package be.kdg.youth_council_project.domain.platform.style;

import be.kdg.youth_council_project.domain.platform.YouthCouncil;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Style {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private Font font;
    @Enumerated(EnumType.STRING)
    private Color primaryColor;
    @Enumerated(EnumType.STRING)
    private Color secondaryColor;
    @OneToOne(mappedBy = "style")
    private YouthCouncil youthCouncil;
}
