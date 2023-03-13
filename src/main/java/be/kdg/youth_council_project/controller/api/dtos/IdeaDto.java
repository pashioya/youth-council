package be.kdg.youth_council_project.controller.api.dtos;

import be.kdg.youth_council_project.domain.platform.youthCouncilItems.ActionPoint;
import javax.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class IdeaDto {
    private Long id;
    private String description;

    private List<String> images;
    private LocalDateTime dateAdded;

    private UserDto authorDto;

    private ThemeDto themeDto;

    private YouthCouncilDto youthCouncilDto;

}
