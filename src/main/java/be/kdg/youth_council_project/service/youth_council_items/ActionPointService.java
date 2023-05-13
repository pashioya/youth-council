package be.kdg.youth_council_project.service.youth_council_items;

import be.kdg.youth_council_project.domain.platform.youth_council_items.ActionPoint;
import be.kdg.youth_council_project.domain.platform.youth_council_items.Idea;
import be.kdg.youth_council_project.domain.platform.youth_council_items.comments.ActionPointComment;
import be.kdg.youth_council_project.domain.platform.youth_council_items.like.ActionPointLike;

import java.util.List;

public interface ActionPointService {

    List<ActionPoint> getActionPointsByYouthCouncilId(long youthCouncilId);

    ActionPoint getActionPointById(long youthCouncilId, long actionPointId);

    List<Idea> getIdeasOfActionPoint(long actionPointId, long youthCouncilId);


    List<String> getImagesOfActionPoint(long actionPointId);

    void setStatusOfActionPoint(ActionPoint actionPoint, String statusName);

    void setLinkedIdeasOfActionPoint(ActionPoint actionPoint, List<Long> linkedIdeaIds, long youthCouncilId);

    void setStandardActionOfActionPoint(ActionPoint actionPoint, Long standardActionId);

    void setYouthCouncilOfActionPoint(ActionPoint actionPoint, long youthCouncilId);

    ActionPoint createActionPoint(ActionPoint actionPoint);

    void setAuthorOfActionPointComment(ActionPointComment actionPointComment, long userId);

    void setActionPointOfActionPointComment(ActionPointComment actionPointComment, long actionPointId, long youthCouncilId);

    ActionPointComment createActionPointComment(ActionPointComment actionPointComment);


    void setActionPointOfActionPointLike(ActionPointLike createdActionPointLike, long actionPointId, long youthCouncilId);

    void setUserOfActionPointLike(ActionPointLike createdActionPointLike, long userId);

    ActionPointLike createActionPointLike(ActionPointLike actionPointLike);

    void removeActionPointLike(long actionPointId, long userId, long youthCouncilId);

    boolean isLikedByUser(Long id, long userId);

    List<ActionPointComment> getCommentsByUserId(long userId);
}
