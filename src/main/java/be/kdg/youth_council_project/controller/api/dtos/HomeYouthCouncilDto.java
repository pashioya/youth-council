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
    private boolean isMember;

    public HomeYouthCouncilDto(Long id, String name, String municipalityName, String slug) {
        this.id = id;
        this.name = name;
        this.municipalityName = municipalityName;
        this.slug = slug;
    }
}
