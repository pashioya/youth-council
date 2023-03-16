package be.kdg.youth_council_project.service;

import be.kdg.youth_council_project.domain.platform.youthCouncilItems.ActionPoint;
import be.kdg.youth_council_project.domain.platform.youthCouncilItems.Idea;

import java.util.List;

public interface ActionPointService {

    public List<ActionPoint> getActionPointsByYouthCouncilId(long youthCouncilId);
    public ActionPoint getActionPointById(long youthCouncilId, long actionPointId);

    public List<Idea> getIdeasOfActionPoint(long actionPointId);


    public List<String> getImagesOfActionPoint(long actionPointId);
}
