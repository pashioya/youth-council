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
