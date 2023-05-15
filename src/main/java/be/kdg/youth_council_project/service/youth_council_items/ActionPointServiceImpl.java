package be.kdg.youth_council_project.service.youth_council_items;

import be.kdg.youth_council_project.controller.api.dtos.StandardActionDto;
import be.kdg.youth_council_project.controller.api.dtos.ThemeDto;
import be.kdg.youth_council_project.controller.api.dtos.youth_council_items.action_point.ActionPointDto;
import be.kdg.youth_council_project.controller.api.dtos.youth_council_items.action_point.LinkedIdeaDto;
import be.kdg.youth_council_project.controller.api.dtos.youth_council_items.action_point.NewActionPointDto;
import be.kdg.youth_council_project.controller.mvc.viewmodels.ActionPointViewModel;
import be.kdg.youth_council_project.domain.platform.User;
import be.kdg.youth_council_project.domain.platform.YouthCouncil;
import be.kdg.youth_council_project.domain.platform.youth_council_items.*;
import be.kdg.youth_council_project.domain.platform.youth_council_items.comments.ActionPointComment;
import be.kdg.youth_council_project.domain.platform.youth_council_items.images.ActionPointImage;
import be.kdg.youth_council_project.domain.platform.youth_council_items.like.ActionPointLike;
import be.kdg.youth_council_project.repository.MembershipRepository;
import be.kdg.youth_council_project.repository.ThemeRepository;
import be.kdg.youth_council_project.repository.UserRepository;
import be.kdg.youth_council_project.repository.YouthCouncilRepository;
import be.kdg.youth_council_project.repository.action_point.ActionPointCommentRepository;
import be.kdg.youth_council_project.repository.action_point.ActionPointImageRepository;
import be.kdg.youth_council_project.repository.action_point.ActionPointLikeRepository;
import be.kdg.youth_council_project.repository.action_point.ActionPointRepository;
import be.kdg.youth_council_project.repository.idea.IdeaRepository;
import be.kdg.youth_council_project.security.CustomUserDetails;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
@AllArgsConstructor
public class ActionPointServiceImpl implements ActionPointService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private final ActionPointRepository actionPointRepository;

    private final ThemeRepository themeRepository;

    private final ModelMapper modelMapper;

    private final YouthCouncilRepository youthCouncilRepository;
    private final IdeaRepository ideaRepository;

    private final StandardActionService standardActionService;

    private final UserRepository userRepository;
    private final ActionPointCommentRepository actionPointCommentRepository;
    private final ActionPointLikeRepository actionPointLikeRepository;

    private final MembershipRepository membershipRepository;

    private final ActionPointImageRepository actionPointImageRepository;

    @Override
    public List<ActionPoint> getActionPointsByYouthCouncilId(long youthCouncilId) {
        LOGGER.info("ActionPointServiceImpl is running getActionPointsOfYouthCouncil");
        YouthCouncil youthCouncil = youthCouncilRepository.findById(youthCouncilId).orElseThrow(EntityNotFoundException::new);
        List<ActionPoint> actionPoints = actionPointRepository.findByYouthCouncil(youthCouncil);
        actionPoints.forEach(actionPoint -> {
            actionPoint.setComments(getCommentsOfActionPoint(actionPoint));
            actionPoint.setLikes(getLikesOfActionPoint(actionPoint));
        });
        return actionPoints;
    }

    private List<ActionPointLike> getLikesOfActionPoint(ActionPoint actionPoint) {
        LOGGER.info("ActionPointServiceImpl is running getLikesOfActionPoint");
        List<ActionPointLike> actionPointLikes = actionPointLikeRepository.findById_ActionPoint(actionPoint);
        return actionPointLikes;
    }

    private List<ActionPointComment> getCommentsOfActionPoint(ActionPoint actionPoint) {
        LOGGER.info("ActionPointServiceImpl is running getCommentsOfActionPoint");
        List<ActionPointComment> actionPointComments = actionPointCommentRepository.findByActionPoint(actionPoint);
        return actionPointComments;
    }

    @Override
    @Transactional
    public List<Idea> getIdeasOfActionPoint(long actionPointId, long youthCouncilId) {
        LOGGER.info("ActionPointServiceImpl is running getIdeasOfActionPoint");
        ActionPoint actionPoint = getActionPointById(actionPointId, youthCouncilId);
        List<Idea> ideas = actionPoint.getLinkedIdeas();
        return ideas;
    }

    @Override
    public ActionPoint getActionPointById(long actionPointId, long youthCouncilId) {
        LOGGER.info("ActionPointServiceImpl is running getActionPointById");
        YouthCouncil youthCouncil = youthCouncilRepository.findById(youthCouncilId).orElseThrow(EntityNotFoundException::new);
        ActionPoint actionPoint = actionPointRepository.findByIdAndYouthCouncil(actionPointId, youthCouncil).orElseThrow(EntityNotFoundException::new);
        return actionPoint;
    }

    @Override
    public List<ActionPointImage> getImagesOfActionPoint(long actionPointId) {
        LOGGER.info("ActionPointServiceImpl is running getImagesOfActionPoint");
        return actionPointImageRepository.findByActionPoint_Id(actionPointId);
    }

    @Override
    @Transactional
    public void setStatusOfActionPoint(ActionPoint actionPoint, String statusName) {
        LOGGER.info("ActionPointServiceImpl is running setStatusOfActionPoint");
        ActionPointStatus status = ActionPointStatus.valueOf(statusName);
        actionPoint.setStatus(status);
    }

    @Override
    @Transactional
    public void setLinkedIdeasOfActionPoint(ActionPoint actionPoint, List<Long> linkedIdeaIds, long youthCouncilId) {
        LOGGER.info("ActionPointServiceImpl is running setLinkedIdeasOfActionPoint");
        List<Idea> ideas = linkedIdeaIds.stream()
                .filter(id -> ideaRepository.ideaBelongsToYouthCouncil(id, youthCouncilId))
                .map(id -> ideaRepository.findById(id).get()).toList();
        ideas.forEach(idea -> {
            // Add the linked idea to the action point if they belong to same youth council
            if (idea.getYouthCouncil().getId() == actionPoint.getYouthCouncil().getId()) {
                actionPoint.addLinkedIdea(idea);
                idea.addActionPoint(actionPoint);
            }
        });
    }

    @Override
    @Transactional
    public void setStandardActionOfActionPoint(ActionPoint actionPoint, Long standardActionId) {
        LOGGER.info("ActionPointServiceImpl is running setStandardActionOfActionPoint");
        StandardAction standardAction = standardActionService.getStandardActionById(standardActionId);
        actionPoint.setLinkedStandardAction(standardAction);
    }

    @Override
    @Transactional
    public void setYouthCouncilOfActionPoint(ActionPoint actionPoint, long youthCouncilId) {
        LOGGER.info("ActionPointServiceImpl is running setYouthCouncilOfActionPoint");
        YouthCouncil youthCouncil = youthCouncilRepository.findById(youthCouncilId).orElseThrow(EntityNotFoundException::new);
        actionPoint.setYouthCouncil(youthCouncil);
    }

    @Override
    @Transactional
    public ActionPoint createActionPoint(ActionPoint actionPoint) {
        LOGGER.info("ActionPointServiceImpl is running createActionPoint");
        return actionPointRepository.save(actionPoint);
    }


    @Override
    public void setAuthorOfActionPointComment(ActionPointComment actionPointComment, long userId) {
        LOGGER.info("ActionPointServiceImpl is running setAuthorOfActionPointComment");
        User user = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
        actionPointComment.setAuthor(user);
    }

    @Override
    public void setActionPointOfActionPointComment(ActionPointComment actionPointComment, long actionPointId, long youthCouncilId) {
        LOGGER.info("ActionPointServiceImpl is running setActionPointOfActionPointComment");
        ActionPoint actionPoint = getActionPointById(actionPointId, youthCouncilId);
        actionPointComment.setActionPoint(actionPoint);
    }

    @Override
    @Transactional
    public ActionPointComment createActionPointComment(ActionPointComment actionPointComment) {
        LOGGER.info("ActionPointServiceImpl is running createActionPointComment");
        actionPointCommentRepository.save(actionPointComment);
        return actionPointComment;
    }


    @Override
    public void setActionPointOfActionPointLike(ActionPointLike createdActionPointLike, long actionPointId, long youthCouncilId) {
        LOGGER.info("ActionPointServiceImpl is running setActionPointOfActionPointLike");
        ActionPoint actionPoint = getActionPointById(actionPointId, youthCouncilId);
        LOGGER.info("ActionPointServiceImpl is setting action point of action point like to {}", actionPoint);
        createdActionPointLike.getId().setActionPoint(actionPoint);
    }

    @Override
    public void setUserOfActionPointLike(ActionPointLike createdActionPointLike, long userId) {
        LOGGER.info("ActionPointServiceImpl is running setUserOfActionPointLike");
        User user = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
        LOGGER.info("ActionPointServiceImpl is setting user of action point like to {}", user);
        createdActionPointLike.getId().setLikedBy(user);
    }

    @Override
    public ActionPointLike createActionPointLike(ActionPointLike actionPointLike) {
        LOGGER.info("ActionPointServiceImpl is running createActionPointLike");
        if (!actionPointLikeRepository.existsByUserIdAndActionPointId(actionPointLike.getId().getLikedBy().getId(), actionPointLike.getId().getActionPoint().getId())) {
            LOGGER.info("saving action point like: {}", actionPointLike);
            return actionPointLikeRepository.save(actionPointLike);
        }
        return null;
    }

    @Override
    @Transactional
    public void removeActionPointLike(long actionPointId, long userId, long youthCouncilId) {
        LOGGER.info("ActionPointServiceImpl is running removeActionPointLike");
        if (!userRepository.existsById(userId)){
            throw new EntityNotFoundException();
        }
        ActionPointLike actionPointLike = actionPointLikeRepository.findByActionPointIdAndUserIdAndYouthCouncilId(actionPointId, userId, youthCouncilId).orElseThrow(EntityNotFoundException::new);
        actionPointLikeRepository.delete(actionPointLike);
    }

    @Override
    public boolean isLikedByUser(Long id, long userId) {
        LOGGER.info("ActionPointServiceImpl is running isLikedByUser");
        return actionPointLikeRepository.existsByUserIdAndActionPointId(userId, id);
    }

    @Override
    public ActionPoint addFromDto(NewActionPointDto newActionPointDto, List<MultipartFile> images, long tenantId) {
        ActionPoint newActionPoint = new ActionPoint();
        newActionPoint.setTitle(newActionPointDto.getTitle());
        newActionPoint.setDescription(newActionPointDto.getDescription());
        newActionPoint.setStatus(ActionPointStatus.valueOf(newActionPointDto.getStatusName()));

        newActionPoint.setYouthCouncil(youthCouncilRepository.findById(tenantId).orElseThrow(EntityNotFoundException::new));
        newActionPoint.setCreatedDate(LocalDateTime.now());
        newActionPoint.setLinkedStandardAction(standardActionService.getStandardActionById(newActionPointDto.getStandardActionId()));
        newActionPoint = createActionPoint(newActionPoint);

        // Create images for the action point
        if (images != null) {
            for (MultipartFile image : images) {
                try {
                    newActionPoint.addImage(addImageToActionPoint(newActionPoint, image));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return newActionPoint;
    }

    @Override
    public ActionPointImage addImageToActionPoint(ActionPoint actionPoint, MultipartFile image) throws IOException {
        LOGGER.info("ActionPointServiceImpl is running addImageToActionPoint");
        ActionPointImage actionPointImage = new ActionPointImage();

        actionPointImage.setImage(image.getBytes());
        actionPointImage.setActionPoint(actionPoint);

        actionPointImage.setActionPoint(actionPoint);
        return actionPointImageRepository.save(actionPointImage);
    }

    @Override
    public ActionPointDto mapToDto(ActionPoint actionPoint, long tenantId) {

        // Map images
        List<ActionPointImage> actionPointImages = getImagesOfActionPoint(actionPoint.getId());
        List<String> mappedImages = new ArrayList<>();
        actionPointImages.forEach(image -> mappedImages.add(Base64.getEncoder().encodeToString(image.getImage())));

        // Map ideas
        List<LinkedIdeaDto> mappedIdeas = new ArrayList<>();
//        if (actionPoint.getLinkedIdeas() == null) {
//            List<Idea> ideas = actionPoint.getLinkedIdeas();
//            ideas.forEach(idea -> mappedIdeas.add(new LinkedIdeaDto(idea.getId(), idea.getDescription())));
//        }

        // Map standard action
        StandardAction standardAction = standardActionService.getStandardActionById(actionPoint.getLinkedStandardAction().getId());
        Theme theme = standardAction.getTheme();
        ThemeDto mappedTheme = new ThemeDto(theme.getId(), theme.getName());
        StandardActionDto mappedStandardAction = new StandardActionDto(standardAction.getName(), mappedTheme);


        return new ActionPointDto(
                actionPoint.getId(),
                actionPoint.getTitle(),
                actionPoint.getDescription(),
                actionPoint.getVideo(),
                actionPoint.getStatus(),
                mappedImages,
                actionPoint.getCreatedDate(),
                mappedIdeas,
                mappedStandardAction
                );
    }

    @Override
    public List<ActionPointViewModel> mapToViewModels(List<ActionPoint> actionPoints, CustomUserDetails user, long tenantId) {
        return actionPoints.stream().map(actionPoint -> {
                    actionPoint.setImages(getImagesOfActionPoint(actionPoint.getId()));
                    ActionPointViewModel actionPointViewModel = modelMapper.map(actionPoint,
                            ActionPointViewModel.class);
                    if (user != null) {
                        actionPointViewModel.setLikedByUser(isLikedByUser(actionPoint.getId(),
                                user.getUserId()));
                    }
                    return actionPointViewModel;
                }
        ).toList();
    }

    @Override
    public List<ActionPointComment> getCommentsByUserId(long userId) {
        LOGGER.info("ActionPointServiceImpl is running getCommentsByUserId");
        return actionPointCommentRepository.findAllByAuthorId(userId);
    }

    @Override
    @Transactional
    public void removeActionPoint(long actionPointId, long tenantId) {
        LOGGER.info("ActionPointServiceImpl is running removeActionPoint");
        actionPointCommentRepository.deleteActionPointCommentByActionPointId(actionPointId);
        actionPointLikeRepository.deleteActionPointLikeByActionPointId(actionPointId);
        actionPointRepository.deleteById(actionPointId);
    }
}
