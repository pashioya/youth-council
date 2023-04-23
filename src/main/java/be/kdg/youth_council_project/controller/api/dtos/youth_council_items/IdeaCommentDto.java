package be.kdg.youth_council_project.controller.api.dtos.youth_council_items;

import be.kdg.youth_council_project.controller.api.dtos.UserDto;
import lombok.*;

import java.time.LocalDateTime;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IdeaCommentDto {
    private long ideaCommentId;
    private UserDto author;
    private IdeaDto idea;

    private String content;

    private LocalDateTime createdDate;
}
