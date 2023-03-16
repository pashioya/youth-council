package be.kdg.youth_council_project.controller.api.dtos;


import lombok.*;

import java.time.LocalDateTime;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IdeaLikeDto {
    private IdeaDto idea;
    private UserDto likedBy;
    private LocalDateTime likedDateTime;
}
