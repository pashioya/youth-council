package be.kdg.youth_council_project.config.faker;

import be.kdg.youth_council_project.domain.platform.youthCouncilItems.Activity;
import be.kdg.youth_council_project.repository.ActivityRepository;
import be.kdg.youth_council_project.repository.YouthCouncilRepository;
import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@AllArgsConstructor
public class FakeActivityCreator {
    private final ActivityRepository activityRepository;
    private final YouthCouncilRepository youthCouncilRepository;
    private final Faker faker;

    private final List<Activity> activities;

    public List<Activity> createFakeActivities(int amount) {
        for (int i = 0; i < amount; i++) {
            Activity activity = new Activity();
            activity.setName(faker.job().keySkills());
            activity.setDescription(faker.job().position());
            activity.setEndDate(LocalDateTime.from(LocalDateTime.now().plusDays(10)));
            activity.setStartDate(LocalDateTime.now());
            activity.setYouthCouncil(youthCouncilRepository.findAll().get(0));
            activities.add(activity);
            activityRepository.save(activity);
        }
        return activities;
    }
}
