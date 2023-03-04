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
public class Answer {
    private Long id;
    private String answer;
    private Question question;
}
