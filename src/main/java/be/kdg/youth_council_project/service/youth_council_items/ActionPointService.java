package be.kdg.youth_council_project.service.youth_council_items;

import be.kdg.youth_council_project.controller.api.dtos.youth_council_items.action_point.ActionPointDto;
import be.kdg.youth_council_project.controller.api.dtos.youth_council_items.action_point.EditActionPointDto;
import be.kdg.youth_council_project.controller.api.dtos.youth_council_items.action_point.NewActionPointDto;
import be.kdg.youth_council_project.controller.mvc.viewmodels.ActionPointViewModel;
import be.kdg.youth_council_project.domain.platform.youth_council_items.ActionPoint;
import be.kdg.youth_council_project.domain.platform.youth_council_items.comments.ActionPointComment;
import be.kdg.youth_council_project.domain.platform.youth_council_items.images.ActionPointImage;
import be.kdg.youth_council_project.domain.platform.youth_council_items.like.ActionPointLike;
import be.kdg.youth_council_project.security.CustomUserDetails;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ActionPointService {

    List<ActionPoint> getActionPointsByYouthCouncilId(long youthCouncilId);

    ActionPoint getActionPointById(long youthCouncilId, long actionPointId);


    List<ActionPointImage> getImagesOfActionPoint(long actionPointId);

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

    ActionPoint addFromDto(NewActionPointDto newActionPointDto, List<MultipartFile> images, long tenantId);

    ActionPointDto mapToDto(ActionPoint actionPoint, long tenantId);

    ActionPointImage addImageToActionPoint(ActionPoint actionPoint, MultipartFile image) throws IOException;

    void deleteActionPoint(long actionPointId, long tenantId);

    ActionPointViewModel mapToViewModel(ActionPoint actionPoint, CustomUserDetails user);

    List<ActionPointComment> getAllCommentsByYouthCouncilId(long tenantId);

    void updateActionPoint(long id, EditActionPointDto editActionPointDto, long tenantId);

    List<ActionPointComment> getAllComments();

    void deleteActionPointComment(long actionPointId, long commentId);
}
