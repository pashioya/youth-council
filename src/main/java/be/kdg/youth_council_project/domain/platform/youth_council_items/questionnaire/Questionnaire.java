package be.kdg.youth_council_project.domain.platform.youth_council_items.questionnaire;

import be.kdg.youth_council_project.domain.platform.YouthCouncil;
import javax.persistence.*;

import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Questionnaire {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private LocalDateTime dateAdded;

    @OneToMany(mappedBy = "questionnaire")
    private List<Question> questions;
    @ManyToOne
    @JoinColumn(name="youth_council_id")
    private YouthCouncil youthCouncil;

}
