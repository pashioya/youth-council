package be.kdg.youth_council_project.controller.api.dtos.youth_council_items.action_point;

import be.kdg.youth_council_project.controller.api.dtos.UserDto;
import lombok.*;

import java.time.LocalDateTime;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ActionPointCommentDto {

    private long actionPointCommentId;
    private UserDto author;
    private ActionPointDto actionPoint;

    private String content;

    private LocalDateTime createdDate;
}
