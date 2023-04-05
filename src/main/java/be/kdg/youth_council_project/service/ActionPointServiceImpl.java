package be.kdg.youth_council_project.service;

import be.kdg.youth_council_project.controller.mvc.viewmodels.ActionPointViewModel;
import be.kdg.youth_council_project.domain.platform.User;
import be.kdg.youth_council_project.domain.platform.YouthCouncil;
import be.kdg.youth_council_project.domain.platform.youth_council_items.ActionPoint;
import be.kdg.youth_council_project.domain.platform.youth_council_items.ActionPointStatus;
import be.kdg.youth_council_project.domain.platform.youth_council_items.Idea;
import be.kdg.youth_council_project.domain.platform.youth_council_items.StandardAction;
import be.kdg.youth_council_project.domain.platform.youth_council_items.comments.ActionPointComment;
import be.kdg.youth_council_project.domain.platform.youth_council_items.like.ActionPointLike;
import be.kdg.youth_council_project.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class ActionPointServiceImpl implements ActionPointService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final ActionPointRepository actionPointRepository;

    private final ThemeRepository themeRepository;

    private final YouthCouncilRepository youthCouncilRepository;

    private final IdeaRepository ideaRepository;

    private final StandardActionRepository standardActionRepository;

    private final UserRepository userRepository;

    private final ActionPointCommentRepository actionPointCommentRepository;
    private final ActionPointLikeRepository actionPointLikeRepository;

    private final MembershipRepository membershipRepository;

    public ActionPointServiceImpl(ActionPointRepository actionPointRepository, ThemeRepository themeRepository, YouthCouncilRepository youthCouncilRepository, IdeaRepository ideaRepository, StandardActionRepository standardActionRepository, UserRepository userRepository, ActionPointCommentRepository actionPointCommentRepository, ActionPointLikeRepository actionPointLikeRepository, MembershipRepository membershipRepository) {
        this.actionPointRepository = actionPointRepository;
        this.themeRepository = themeRepository;
        this.youthCouncilRepository = youthCouncilRepository;
        this.ideaRepository = ideaRepository;
        this.standardActionRepository = standardActionRepository;
        this.userRepository = userRepository;
        this.actionPointCommentRepository = actionPointCommentRepository;
        this.actionPointLikeRepository = actionPointLikeRepository;
        this.membershipRepository = membershipRepository;
    }

    @Override
    public List<ActionPoint> getActionPointsByYouthCouncilId(long youthCouncilId) {
        LOGGER.info("ActionPointServiceImpl is running getActionPointsOfYouthCouncil");
        YouthCouncil youthCouncil = youthCouncilRepository.findById(youthCouncilId).orElseThrow(EntityNotFoundException::new);
        return actionPointRepository.findByYouthCouncil(youthCouncil);
    }

    @Override
    @Transactional
    public List<Idea> getIdeasOfActionPoint(long actionPointId){
        LOGGER.info("ActionPointServiceImpl is running getIdeasOfActionPoint");
        ActionPoint actionPoint = actionPointRepository.findById(actionPointId).orElseThrow(EntityNotFoundException::new);
        List<Idea> ideas = actionPoint.getLinkedIdeas();
        LOGGER.info("ActionPointServiceImpl is returning idea list {}", ideas);
        return ideas;
    }

    @Override
    public ActionPoint getActionPointById(long youthCouncilId, long actionPointId) {
        LOGGER.info("ActionPointServiceImpl is running getActionPointById");
        ActionPoint actionPoint = actionPointRepository.findById(actionPointId).orElseThrow(EntityNotFoundException::new);
        if (actionPoint.getYouthCouncil().getId() == youthCouncilId){
            // youthCouncilId in URL must be for a youth council that owns the requested ActionPoint
            return actionPoint;
        }
        throw new EntityNotFoundException(String.format("ActionPoint with id %d does not belong to YouthCouncil with id %d", actionPointId, youthCouncilId));
    }

    @Override
    public List<String> getImagesOfActionPoint(long actionPointId) {
        LOGGER.info("ActionPointServiceImpl is running getImagesOfActionPoint");
        return actionPointRepository.getImagesByActionPointId(actionPointId);
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
            if (idea.getYouthCouncil().getId() == actionPoint.getYouthCouncil().getId()){
                actionPoint.addLinkedIdea(idea);
                idea.addActionPoint(actionPoint);
            }
        });
    }

    @Override
    @Transactional
    public void setStandardActionOfActionPoint(ActionPoint actionPoint, Long standardActionId) {
        LOGGER.info("ActionPointServiceImpl is running setStandardActionOfActionPoint");
        StandardAction standardAction = standardActionRepository.findById(standardActionId).orElseThrow(EntityNotFoundException::new);
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
    public void setActionPointOfActionPointComment(ActionPointComment actionPointComment, long actionPointId) {
        LOGGER.info("ActionPointServiceImpl is running setActionPointOfActionPointComment");
        ActionPoint actionPoint = actionPointRepository.findById(actionPointId).orElseThrow(EntityNotFoundException::new);
        actionPointComment.setActionPoint(actionPoint);
    }

    @Override
    public ActionPointComment createActionPointComment(ActionPointComment actionPointComment) {
        LOGGER.info("ActionPointServiceImpl is running createActionPointComment");
        actionPointCommentRepository.save(actionPointComment);
        return actionPointComment;
    }

    @Override
    public boolean userAndActionPointInSameYouthCouncil(long userId, long actionPointId, long youthCouncilId) {
        LOGGER.info("ActionPointServiceImpl is running userAndIdeaInSameYouthCouncil");
        return actionPointRepository.actionPointBelongsToYouthCouncil(actionPointId, youthCouncilId) && membershipRepository.userIsMemberOfYouthCouncil(userId, youthCouncilId);
    }

    @Override
    public ActionPointViewModel mapActionPointToActionPointViewModel(ActionPoint actionPoint) {
        LOGGER.info("ActionPointServiceImpl is running mapActionPointToActionPointViewModel");
        ActionPointViewModel actionPointViewModel = new ActionPointViewModel();
        actionPointViewModel.setId(actionPoint.getId());
        actionPointViewModel.setTitle(actionPoint.getTitle());
        actionPointViewModel.setDescription(actionPoint.getDescription());
        actionPointViewModel.setDateAdded(actionPoint.getCreatedDate());
        actionPointViewModel.setStatus(actionPoint.getStatus().toString());
        actionPointViewModel.setLikes(actionPointLikeRepository.countAllByActionPointLikeId_ActionPoint(actionPoint));
        return actionPointViewModel;
    }

    @Override
    public void setActionPointOfActionPointLike(ActionPointLike createdActionPointLike, long actionPointId) {
        LOGGER.info("ActionPointServiceImpl is running setActionPointOfActionPointLike");
        ActionPoint actionPoint = actionPointRepository.findById(actionPointId).orElseThrow(EntityNotFoundException::new);
        LOGGER.info("ActionPointServiceImpl is setting action point of action point like to {}", actionPoint);
        createdActionPointLike.getActionPointLikeId().setActionPoint(actionPoint);
    }

    @Override
    public void setUserOfActionPointLike(ActionPointLike createdActionPointLike, long userId) {
        LOGGER.info("ActionPointServiceImpl is running setUserOfActionPointLike");
        User user = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
        LOGGER.info("ActionPointServiceImpl is setting user of action point like to {}", user);
        createdActionPointLike.getActionPointLikeId().setLikedBy(user);
    }

    @Override
    public void createActionPointLike(ActionPointLike actionPointLike) {
        LOGGER.info("ActionPointServiceImpl is running createActionPointLike");
        if(!actionPointLikeRepository.existsByUserIdAndActionPointId(actionPointLike.getActionPointLikeId().getLikedBy().getId(), actionPointLike.getActionPointLikeId().getActionPoint().getId())){
            actionPointLikeRepository.save(actionPointLike);
            LOGGER.info("saving action point like: {}",actionPointLike);
        }
    }

}
