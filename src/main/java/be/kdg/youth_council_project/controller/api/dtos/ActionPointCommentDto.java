package be.kdg.youth_council_project.controller.api.dtos;

import be.kdg.youth_council_project.domain.platform.youthCouncilItems.comments.ActionPointComment;
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
