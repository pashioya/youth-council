package be.kdg.youth_council_project.controller.api.dtos;

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
