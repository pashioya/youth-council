package be.kdg.youth_council_project.config.faker;

import be.kdg.youth_council_project.domain.platform.youthCouncilItems.StandardAction;
import be.kdg.youth_council_project.domain.platform.youthCouncilItems.Theme;
import be.kdg.youth_council_project.repository.StandardActionRepository;
import be.kdg.youth_council_project.repository.ThemeRepository;
import be.kdg.youth_council_project.util.RandomUtil;
import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class FakeThemeCreator {
    private final Faker faker;
    private final ThemeRepository themeRepository;
    private final StandardActionRepository standardActionRepository;

    public void createFakeThemes(int amount) {
        for (int i = 0; i < amount; i++) {
            Theme theme = new Theme();
            theme.setName(faker.food().vegetable());
            themeRepository.save(theme);
            for (int i1 = 0; i1 < RandomUtil.getRandomInt(1, 3); i1++) {
                StandardAction standardAction = new StandardAction();
                standardAction.setName(faker.food().dish());
                standardAction.setTheme(theme);
                standardActionRepository.save(standardAction);
            }
        }
    }
}
