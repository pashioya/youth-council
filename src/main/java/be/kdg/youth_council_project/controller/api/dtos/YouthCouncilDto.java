package be.kdg.youth_council_project.controller.api.dtos;

import be.kdg.youth_council_project.domain.platform.Municipality;
import jakarta.persistence.OneToOne;
import lombok.*;


@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class YouthCouncilDto {
    private Long id;
    private String name;

    private String municipalityName;
}
