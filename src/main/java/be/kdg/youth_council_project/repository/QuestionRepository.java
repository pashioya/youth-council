package be.kdg.youth_council_project.repository;

import be.kdg.youth_council_project.domain.platform.youth_council_items.questionnaire.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}
