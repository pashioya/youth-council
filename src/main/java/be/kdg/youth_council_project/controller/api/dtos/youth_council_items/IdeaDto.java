package be.kdg.youth_council_project.controller.api.dtos.youth_council_items;

import be.kdg.youth_council_project.controller.api.dtos.ThemeDto;
import be.kdg.youth_council_project.controller.api.dtos.UserDto;
import be.kdg.youth_council_project.controller.api.dtos.YouthCouncilDto;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class IdeaDto {
    private Long ideaId;
    private String description;
    private List<String> images;
    private LocalDateTime dateAdded;
    private UserDto author;
    private ThemeDto theme;
    private YouthCouncilDto youthCouncil;

}
