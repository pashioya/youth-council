package be.kdg.youth_council_project.controller.api.dtos;

import lombok.*;


@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HomeYouthCouncilDto {
    private Long id;
    private String name;
    private String municipalityName;
    private String slug;
}
