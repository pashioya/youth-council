package be.kdg.youth_council_project.service.youth_council_items;

import be.kdg.youth_council_project.domain.platform.youth_council_items.Activity;

import java.util.List;

public interface ActivityService {

    List<Activity> getActivitiesByYouthCouncilId(long tenantId);

    void setYouthCouncilOfActivity(Activity activity, long tenantId);

    Activity createActivity(Activity createdActivity);

    void deleteActivity(long activityId, long tenantId);
}
