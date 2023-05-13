package be.kdg.youth_council_project.controller.api.dtos;

import be.kdg.youth_council_project.domain.platform.User;
import lombok.*;

import java.time.LocalDateTime;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    private User author;
    private String content;
    private LocalDateTime createdDate;
}
