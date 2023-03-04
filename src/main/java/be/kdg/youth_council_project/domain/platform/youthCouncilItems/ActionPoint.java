package be.kdg.youth_council_project.domain.platform.youthCouncilItems;

import be.kdg.youth_council_project.domain.platform.YouthCouncil;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ActionPoint {
    private Long id;
    private ActionPointStatus status;
    private String title;
    private String video;
    private String description;
    private List<String> images;
    private LocalDateTime dateAdded;
    private long likes;

    private Idea linkedIdea;
    private StandardAction linkedStandardAction;
    private List<Comment> comments;


    private YouthCouncil youthCouncil;

}
