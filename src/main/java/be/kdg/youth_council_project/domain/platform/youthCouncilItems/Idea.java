package be.kdg.youth_council_project.domain.platform.youthCouncilItems;

import be.kdg.youth_council_project.domain.platform.YouthCouncil;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Idea {
    private Long id;
    private String description;

    @Transient
    private List<String> images;
    private LocalDateTime dateAdded;
    private long likes;

    private StandardAction linkedStandardAction;

    private YouthCouncil youthCouncil;

}
