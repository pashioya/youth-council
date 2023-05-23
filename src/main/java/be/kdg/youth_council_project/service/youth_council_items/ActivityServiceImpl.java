package be.kdg.youth_council_project.service.youth_council_items;

import be.kdg.youth_council_project.domain.platform.YouthCouncil;
import be.kdg.youth_council_project.domain.platform.youth_council_items.Activity;
import be.kdg.youth_council_project.repository.ActivityRepository;
import be.kdg.youth_council_project.repository.YouthCouncilRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@AllArgsConstructor
public class ActivityServiceImpl implements ActivityService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private final ActivityRepository activityRepository;
    private final YouthCouncilRepository youthCouncilRepository;


    @Override
    public List<Activity> getActivitiesByYouthCouncilId(long tenantId) {
        LOGGER.info("ActivityServiceImpl is running getActivitiesByYouthCouncilId");
        YouthCouncil youthCouncil = youthCouncilRepository.findById(tenantId).orElseThrow(EntityNotFoundException::new);
        LOGGER.debug("Searching for activities of youth council {}", youthCouncil);
        List<Activity> activities = activityRepository.findByYouthCouncil(youthCouncil);
        LOGGER.debug("Returning activities {}", activities);
        return activities;
    }

    @Override
    public void setYouthCouncilOfActivity(Activity activity, long tenantId) {
        LOGGER.info("ActivityServiceImpl is running setYouthCouncilOfActivity");
        YouthCouncil youthCouncil = youthCouncilRepository.findById(tenantId).orElseThrow(EntityNotFoundException::new);
        LOGGER.debug("Setting youth council {} to activity {}", youthCouncil, activity);
        activity.setYouthCouncil(youthCouncil);
    }

    @Override
    public Activity createActivity(Activity createdActivity) {
        LOGGER.info("ActivityServiceImpl is running createActivity");
        LOGGER.debug("Saving activity {}", createdActivity);
        activityRepository.save(createdActivity);
        return createdActivity;
    }

    @Override
    public void deleteActivity(long activityId, long tenantId) {
        LOGGER.info("ActivityServiceImpl is running deleteActivity");
        activityRepository.deleteById(activityId);
    }
}
