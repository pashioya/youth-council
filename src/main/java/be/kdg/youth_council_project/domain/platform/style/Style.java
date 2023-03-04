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
    private Long id;

    private Font font;
    private Color primaryColor;
    private Color secondaryColor;

    private YouthCouncil youthCouncil;
}
