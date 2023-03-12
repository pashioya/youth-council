package be.kdg.youth_council_project.config.faker;

import be.kdg.youth_council_project.domain.platform.User;
import be.kdg.youth_council_project.domain.platform.YouthCouncil;
import be.kdg.youth_council_project.domain.platform.youthCouncilItems.Activity;
import be.kdg.youth_council_project.domain.platform.youthCouncilItems.Idea;
import be.kdg.youth_council_project.domain.platform.youthCouncilItems.Theme;
import be.kdg.youth_council_project.domain.platform.youthCouncilItems.questionnaire.Questionnaire;
import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
public class FakeDataCreator {
    private final FakeThemeCreator fakeThemeCreator;
    private final FakeUserCreator fakeUserCreator;
    private final FakeIdeaCreator fakeIdeaCreator;

    private final FakeActivityCreator fakeActivityCreator;
    private final FakeYouthCouncilCreator fakeYouthCouncilCreator;
    private final FakeQuestionnaireCreator fakeQuestionnaireCreator;

    public List<Theme> createFakeThemes(int amount) {
        return fakeThemeCreator.createFakeThemes(amount);
    }
    public List<User> createFakeUsers(int amount) {
        return fakeUserCreator.createFakeUsers(amount);
    }
    public List<Idea> createFakeIdeas(int amount) {
        return fakeIdeaCreator.createFakeIdeas(amount);
    }
    public List<Activity> createFakeActivities(int amount) {
        return fakeActivityCreator.createFakeActivities(amount);
    }
    public List<YouthCouncil> createFakeYouthCouncils(int amount) {
        return fakeYouthCouncilCreator.createFakeYouthCouncils(amount);
    }

    public Questionnaire createFakeQuestionnaire() {
        return fakeQuestionnaireCreator.createFakeQuestionnaire();
    }
}
