package be.kdg.youth_council_project.repository;

import be.kdg.youth_council_project.domain.platform.youthCouncilItems.questionnaire.Questionnaire;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionnaireRepository extends JpaRepository<Questionnaire, Integer> {
}
