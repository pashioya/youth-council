package be.kdg.youth_council_project.config.faker;

import be.kdg.youth_council_project.domain.platform.youthCouncilItems.questionnaire.Question;
import be.kdg.youth_council_project.domain.platform.youthCouncilItems.questionnaire.Questionnaire;
import be.kdg.youth_council_project.repository.QuestionRepository;
import be.kdg.youth_council_project.repository.QuestionnaireRepository;
import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class FakeQuestionnaireCreator {

    private final QuestionnaireRepository questionnaireRepository;
    private final Faker faker;
    private final QuestionRepository questionRepository;

    public Questionnaire createFakeQuestionnaire() {
            Questionnaire questionnaire = new Questionnaire();
            questionnaire.setName(faker.book().title());
            questionnaire.setDescription(faker.book().genre());
            for(int j = 0; j < 5; j++){
                Question question = new Question();
                question.setQuestion(faker.shakespeare().romeoAndJulietQuote());
                questionRepository.save(question);
            }
            questionnaire.setQuestions(questionRepository.findAll());
            questionnaireRepository.save(questionnaire);
            return questionnaire;
    }

}
