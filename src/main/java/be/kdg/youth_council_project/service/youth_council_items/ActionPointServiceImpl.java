package be.kdg.youth_council_project.service.youth_council_items;

import be.kdg.youth_council_project.controller.api.dtos.StandardActionDto;
import be.kdg.youth_council_project.controller.api.dtos.ThemeDto;
import be.kdg.youth_council_project.controller.api.dtos.youth_council_items.action_point.ActionPointDto;
import be.kdg.youth_council_project.controller.api.dtos.youth_council_items.action_point.EditActionPointDto;
import be.kdg.youth_council_project.controller.api.dtos.youth_council_items.action_point.LinkedIdeaDto;
import be.kdg.youth_council_project.controller.api.dtos.youth_council_items.action_point.NewActionPointDto;
import be.kdg.youth_council_project.controller.mvc.viewmodels.ActionPointViewModel;
import be.kdg.youth_council_project.controller.mvc.viewmodels.CommentViewModel;
import be.kdg.youth_council_project.controller.mvc.viewmodels.LinkedIdeaViewModel;
import be.kdg.youth_council_project.domain.platform.User;
import be.kdg.youth_council_project.domain.platform.YouthCouncil;
import be.kdg.youth_council_project.domain.platform.youth_council_items.*;
import be.kdg.youth_council_project.domain.platform.youth_council_items.comments.ActionPointComment;
import be.kdg.youth_council_project.domain.platform.youth_council_items.images.ActionPointImage;
import be.kdg.youth_council_project.domain.platform.youth_council_items.like.ActionPointLike;
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
    private final ModelMapper modelMapper;
    private final YouthCouncilRepository youthCouncilRepository;
    private final IdeaRepository ideaRepository;
    private final StandardActionService standardActionService;
    private final UserRepository userRepository;
    private final ActionPointCommentRepository actionPointCommentRepository;
    private final ActionPointLikeRepository actionPointLikeRepository;
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
        return actionPointLikeRepository.findById_ActionPoint(actionPoint);
    }

    private List<ActionPointComment> getCommentsOfActionPoint(ActionPoint actionPoint) {
        LOGGER.info("ActionPointServiceImpl is running getCommentsOfActionPoint");
        return actionPointCommentRepository.findByActionPoint(actionPoint);
    }

    @Override
    public ActionPoint getActionPointById(long actionPointId, long youthCouncilId) {
        LOGGER.info("ActionPointServiceImpl is running getActionPointById");
        LOGGER.info("Found youthCouncil");
        return actionPointRepository.findActionPointByIdAndYouthCouncil_Id(actionPointId, youthCouncilId).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public List<ActionPointImage> getImagesOfActionPoint(long actionPointId) {
        LOGGER.info("ActionPointServiceImpl is running getImagesOfActionPoint");
        return actionPointImageRepository.findByActionPoint_Id(actionPointId);
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
        if (!userRepository.existsById(userId)) {
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
        ActionPoint createdActionPoint = createActionPoint(newActionPoint);

        // Create images for the action point
        if (images != null) {
            for (MultipartFile image : images) {
                try {
                    createdActionPoint.addImage(addImageToActionPoint(createdActionPoint, image));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return createdActionPoint;
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
        getLinkedIdeasOfActionPoint(actionPoint.getId()).forEach(idea -> mappedIdeas.add(new LinkedIdeaDto(idea.getId(), idea.getDescription())));


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

    private List<Idea> getLinkedIdeasOfActionPoint(Long id) {
        LOGGER.info("ActionPointServiceImpl is running getLinkedIdeasOfActionPoint");
        return ideaRepository.findAllByActionPointId(id);
    }

    @Override
    public ActionPointViewModel mapToViewModel(ActionPoint actionPoint, CustomUserDetails user) {
        LOGGER.info("ActionPointServiceImpl is running mapToViewModel");

        actionPoint.setImages(getImagesOfActionPoint(actionPoint.getId()));

        ActionPointViewModel actionPointViewModel = modelMapper.map(actionPoint, ActionPointViewModel.class);
        actionPointViewModel.setNumberOfLikes(actionPointLikeRepository.countByActionPointId(actionPoint.getId()));
        actionPointViewModel.setComments(getCommentsOfActionPoint(actionPoint).stream()
                .map(c -> new CommentViewModel(c.getId(), c.getContent(), c.getAuthor().getUsername(),
                        c.getCreatedDate())).toList());
        actionPointViewModel.setLinkedIdeas(getLinkedIdeasOfActionPoint(actionPoint.getId()).stream()
                .map(i -> new LinkedIdeaViewModel(i.getId(), i.getDescription())).toList());

        if (user != null) {
            actionPointViewModel.setLikedByUser(isLikedByUser(actionPoint.getId(), user.getUserId()));
        } else {
            actionPointViewModel.setLikedByUser(false);
        }

        return actionPointViewModel;
    }

    @Override
    public List<ActionPointComment> getAllCommentsByYouthCouncilId(long tenantId) {
        LOGGER.info("ActionPointServiceImpl is running getAllCommentsByYouthCouncilId");
        return actionPointCommentRepository.findAllByYouthCouncilId(tenantId);
    }

    @Override
    public void deleteActionPoint(long actionPointId, long tenantId) {
        LOGGER.info("ActionPointServiceImpl is running deleteActionPoint");
        actionPointRepository.deleteById(actionPointId);
    }

    @Override
    public List<ActionPointComment> getCommentsByUserId(long userId) {
        LOGGER.info("ActionPointServiceImpl is running getCommentsByUserId");
        return actionPointCommentRepository.findAllByAuthorId(userId);
    }

    @Override
    public void updateActionPoint(long id, EditActionPointDto editActionPointDto, long tenantId) {
        LOGGER.info("ActionPointServiceImpl is running updateActionPoint");
        ActionPoint actionPoint = actionPointRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        actionPoint.setTitle(editActionPointDto.getTitle());
        actionPoint.setDescription(editActionPointDto.getDescription());
        actionPoint.setStatus(ActionPointStatus.valueOf(editActionPointDto.getStatus()));
        actionPoint.setLinkedStandardAction(standardActionService.getStandardActionById(editActionPointDto.getStandardActionId()));
        actionPoint.setLinkedIdeas(editActionPointDto.getLinkedIdeasIds()
                .stream()
                .map(ideaId -> ideaRepository.findById(ideaId).orElseThrow(EntityNotFoundException::new))
                .toList());
        actionPointRepository.save(actionPoint);
    }

    @Override
    public List<ActionPointComment> getAllComments() {
        LOGGER.info("ActionPointServiceImpl is running getAllComments");
        return actionPointCommentRepository.findAll();

    }

    @Override
    public void deleteActionPointComment(long actionPointId, long commentId) {
        LOGGER.info("ActionPointServiceImpl is running deleteActionPointComment");
        ActionPointComment actionPointComment = actionPointCommentRepository.findByIdAndActionPointId(actionPointId, commentId).orElseThrow(EntityNotFoundException::new);
        actionPointCommentRepository.deleteById(actionPointComment.getId());
    }
}
