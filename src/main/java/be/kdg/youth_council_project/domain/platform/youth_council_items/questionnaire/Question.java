package be.kdg.youth_council_project.domain.platform.youth_council_items.questionnaire;

import javax.persistence.*;
import lombok.*;


@Getter
@Setter
@ToString
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
