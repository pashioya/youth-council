package be.kdg.youth_council_project.domain.platform.youthCouncilItems.questionnaire;

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
@NoArgsConstructor
@AllArgsConstructor
public class Questionnaire {

    private Long id;
    private String name;
    private String description;
    private LocalDateTime dateAdded;

    private List<Question> questions;
    private YouthCouncil youthCouncil;

}
