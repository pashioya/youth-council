package be.kdg.youth_council_project.service;

import be.kdg.youth_council_project.domain.platform.youthCouncilItems.ActionPoint;
import be.kdg.youth_council_project.domain.platform.youthCouncilItems.Idea;
import be.kdg.youth_council_project.domain.platform.youthCouncilItems.comments.ActionPointComment;

import java.util.List;

public interface ActionPointService {

    public List<ActionPoint> getActionPointsByYouthCouncilId(long youthCouncilId);
    public ActionPoint getActionPointById(long youthCouncilId, long actionPointId);

    public List<Idea> getIdeasOfActionPoint(long actionPointId);


    public List<String> getImagesOfActionPoint(long actionPointId);

    public void setStatusOfActionPoint(ActionPoint actionPoint, String statusName);

    public void setLinkedIdeasOfActionPoint(ActionPoint actionPoint, List<Long> linkedIdeaIds, long youthCouncilId);

    public void setStandardActionOfActionPoint(ActionPoint actionPoint, Long standardActionId);

    public void setYouthCouncilOfActionPoint(ActionPoint actionPoint, long youthCouncilId);

    public ActionPoint createActionPoint(ActionPoint actionPoint);

    void setAuthorOfActionPointComment(ActionPointComment actionPointComment, long userId);

    void setActionPointOfActionPointComment(ActionPointComment actionPointComment, long actionPointId);

    ActionPointComment createActionPointComment(ActionPointComment actionPointComment);

    public boolean userAndActionPointInSameYouthCouncil(long userId, long actionPointId, long youthCouncilId);
}
