package be.kdg.youth_council_project.service;

import be.kdg.youth_council_project.controller.mvc.viewmodels.ActionPointViewModel;
import be.kdg.youth_council_project.domain.platform.youth_council_items.ActionPoint;
import be.kdg.youth_council_project.domain.platform.youth_council_items.Idea;
import be.kdg.youth_council_project.domain.platform.youth_council_items.comments.ActionPointComment;
import be.kdg.youth_council_project.domain.platform.youth_council_items.like.ActionPointLike;
import be.kdg.youth_council_project.domain.platform.youth_council_items.ActionPoint;
import be.kdg.youth_council_project.domain.platform.youth_council_items.Idea;
import be.kdg.youth_council_project.domain.platform.youth_council_items.comments.ActionPointComment;
import be.kdg.youth_council_project.domain.platform.youth_council_items.like.ActionPointLike;
import be.kdg.youth_council_project.domain.platform.youth_council_items.ActionPoint;
import be.kdg.youth_council_project.domain.platform.youth_council_items.Idea;
import be.kdg.youth_council_project.domain.platform.youth_council_items.comments.ActionPointComment;
import be.kdg.youth_council_project.domain.platform.youth_council_items.like.ActionPointLike;

import java.util.List;

public interface ActionPointService {

    public List<ActionPoint> getActionPointsByYouthCouncilId(long youthCouncilId);
    public ActionPoint getActionPointById(long youthCouncilId, long actionPointId);

    public List<Idea> getIdeasOfActionPoint(long actionPointId, long youthCouncilId);


    public List<String> getImagesOfActionPoint(long actionPointId);

    public void setStatusOfActionPoint(ActionPoint actionPoint, String statusName);

    public void setLinkedIdeasOfActionPoint(ActionPoint actionPoint, List<Long> linkedIdeaIds, long youthCouncilId);

    public void setStandardActionOfActionPoint(ActionPoint actionPoint, Long standardActionId);

    public void setYouthCouncilOfActionPoint(ActionPoint actionPoint, long youthCouncilId);

    public ActionPoint createActionPoint(ActionPoint actionPoint);

    void setAuthorOfActionPointComment(ActionPointComment actionPointComment, long userId);

    void setActionPointOfActionPointComment(ActionPointComment actionPointComment, long actionPointId, long youthCouncilId);

    ActionPointComment createActionPointComment(ActionPointComment actionPointComment);


    void setActionPointOfActionPointLike(ActionPointLike createdActionPointLike, long actionPointId, long youthCouncilId);

    void setUserOfActionPointLike(ActionPointLike createdActionPointLike, long userId);

    ActionPointLike createActionPointLike(ActionPointLike actionPointLike);

    public void removeActionPointLike(long actionPointId, long userId, long youthCouncilId);
}
