package be.kdg.youth_council_project.controller.api.dtos.youth_council_items;


import be.kdg.youth_council_project.controller.api.dtos.UserDto;
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
