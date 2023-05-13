package be.kdg.youth_council_project.controller.api.dtos.youth_council_items.action_point;

import be.kdg.youth_council_project.controller.api.dtos.StandardActionDto;
import be.kdg.youth_council_project.domain.platform.youth_council_items.ActionPointStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ActionPointDto {

    private long actionPointId;

    private String title;
    private String description;
    private String video;

    private ActionPointStatus status;
    private List<String> images;
    private LocalDateTime createdDate;

    private List<LinkedIdeaDto> linkedIdeas;

    private StandardActionDto standardAction;

}
