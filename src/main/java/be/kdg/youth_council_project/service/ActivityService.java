package be.kdg.youth_council_project.service;

import be.kdg.youth_council_project.domain.platform.youth_council_items.Activity;

import java.util.List;

public interface ActivityService {

    List<Activity> getActivitiesByYouthCouncilId(long tenantId);
}
