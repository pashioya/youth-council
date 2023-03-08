package be.kdg.youth_council_project.domain.platform.youthCouncilItems.questionnaire;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Question {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String question;

    @ManyToOne
    @JoinColumn(name="questionnaire_id")
    private Questionnaire questionnaire;

}
