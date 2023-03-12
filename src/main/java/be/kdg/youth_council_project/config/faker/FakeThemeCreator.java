package be.kdg.youth_council_project.config.faker;

import be.kdg.youth_council_project.domain.platform.youthCouncilItems.StandardAction;
import be.kdg.youth_council_project.domain.platform.youthCouncilItems.Theme;
import be.kdg.youth_council_project.repository.StandardActionRepository;
import be.kdg.youth_council_project.repository.ThemeRepository;
import be.kdg.youth_council_project.util.RandomUtil;
import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class FakeThemeCreator {
    private final Faker faker;
    private final ThemeRepository themeRepository;
    private final StandardActionRepository standardActionRepository;

    private final List<Theme> themes;

    public List<Theme> createFakeThemes(int amount) {
        for (int i = 0; i < amount; i++) {
            Theme theme = new Theme();
            theme.setName(faker.food().vegetable());
            theme.setStandardActions(new ArrayList<>());
            for (int i1 = 0; i1 < RandomUtil.getRandomInt(1, 3); i1++) {
                StandardAction standardAction = new StandardAction();
                standardAction.setName(faker.food().dish());
                standardActionRepository.save(standardAction);
                theme.addStandardAction(standardAction);
            }
            themeRepository.save(theme);
            themes.add(theme);
        }
        return themes;
    }
}
