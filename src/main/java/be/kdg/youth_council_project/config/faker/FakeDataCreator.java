package be.kdg.youth_council_project.config.faker;

import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class FakeDataCreator {
    private final FakeThemeCreator fakeThemeCreator;
    private final FakeUserCreator fakeUserCreator;
    private final FakeIdeaCreator fakeIdeaCreator;

    private final FakeActivityCreator fakeActivityCreator;
    private final FakeYouthCouncilCreator fakeYouthCouncilCreator;

    public void createFakeThemes(int amount) {
        fakeThemeCreator.createFakeThemes(amount);
    }
    public void createFakeUsers(int amount) {
        fakeUserCreator.createFakeUsers(amount);
    }
    public void createFakeIdeas(int amount) {
        fakeIdeaCreator.createFakeIdeas(amount);
    }
    public void createFakeActivities(int amount) {
        fakeActivityCreator.createFakeActivities(amount);
    }
    public void createFakeYouthCouncils(int amount) {
        fakeYouthCouncilCreator.createFakeYouthCouncils(amount);
    }

}
