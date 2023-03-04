package be.kdg.youth_council_project.domain.platform;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Section {
    private Long id;
    private String header;
    private String body;
    private String image;
}
