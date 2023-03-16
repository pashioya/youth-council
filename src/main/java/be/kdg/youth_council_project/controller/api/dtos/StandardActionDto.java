package be.kdg.youth_council_project.controller.api.dtos;

import be.kdg.youth_council_project.domain.platform.youthCouncilItems.Theme;
import lombok.*;


@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StandardActionDto {
    private String name;

    private ThemeDto theme;
}
