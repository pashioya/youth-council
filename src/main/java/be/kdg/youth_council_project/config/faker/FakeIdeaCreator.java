package be.kdg.youth_council_project.config.faker;

import be.kdg.youth_council_project.domain.platform.User;
import be.kdg.youth_council_project.domain.platform.youthCouncilItems.ActionPoint;
import be.kdg.youth_council_project.domain.platform.youthCouncilItems.Idea;
import be.kdg.youth_council_project.domain.platform.youthCouncilItems.Theme;
import be.kdg.youth_council_project.repository.ActionPointRepository;
import be.kdg.youth_council_project.repository.IdeaRepository;
import be.kdg.youth_council_project.repository.ThemeRepository;
import be.kdg.youth_council_project.repository.UserRepository;
import be.kdg.youth_council_project.util.RandomUtil;
import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@AllArgsConstructor
@Component
public class FakeIdeaCreator {
    private final Faker faker;
    private final IdeaRepository ideaRepository;
    private final UserRepository userRepository;
    private final ThemeRepository themeRepository;
    private final ActionPointRepository actionPointRepository;

    private final List<Idea> ideas;

    public List<Idea> createFakeIdeas(int amount) {
        for (int i = 0; i < amount; i++) {
            Idea idea = new Idea();
            idea.setDescription(faker.shakespeare().hamletQuote());
            idea.setImages(Collections.singletonList("image"));
            idea.setLikes(faker.random().nextInt(0, 100));
            idea.setTheme((Theme) RandomUtil.getRandomEntity(themeRepository));
            idea.setAuthor((User) RandomUtil.getRandomEntity(userRepository));
            idea.setActionPoint((ActionPoint) RandomUtil.getRandomEntity(actionPointRepository));
            idea.setDateAdded(LocalDateTime.now());
            ideas.add(idea);
            ideaRepository.save(idea);
        }
        return ideas;
    }
}
