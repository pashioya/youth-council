package be.kdg.youth_council_project.service;


import be.kdg.youth_council_project.controller.mvc.viewmodels.CommentViewModel;
import be.kdg.youth_council_project.controller.mvc.viewmodels.IdeaViewModel;
import be.kdg.youth_council_project.domain.platform.User;
import be.kdg.youth_council_project.domain.platform.YouthCouncil;
import be.kdg.youth_council_project.domain.platform.youth_council_items.Idea;
import be.kdg.youth_council_project.domain.platform.youth_council_items.Theme;
import be.kdg.youth_council_project.domain.platform.youth_council_items.comments.IdeaComment;
import be.kdg.youth_council_project.domain.platform.youth_council_items.like.IdeaLike;
import be.kdg.youth_council_project.repository.*;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@AllArgsConstructor
public class IdeaServiceImpl implements IdeaService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final IdeaRepository ideaRepository;
    private final IdeaLikeRepository ideaLikeRepository;

    private final UserRepository userRepository;

    private final ThemeRepository themeRepository;

    private final YouthCouncilRepository youthCouncilRepository;

    private final MembershipRepository membershipRepository;
    private final IdeaCommentRepository ideaCommentRepository;


    @Override
    public void setAuthorOfIdea(Idea idea, long userId) {
        LOGGER.info("IdeaServiceImpl is running setAuthorOfIdea");
        User user = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
        LOGGER.debug("IdeaServiceImpl found user {}", user);
        idea.setAuthor(user);

    }

    @Override
    public void setThemeOfIdea(Idea idea, long themeId) {
        LOGGER.info("IdeaServiceImpl is running setThemeOfIdea");
        Theme theme = themeRepository.findById(themeId).orElseThrow(EntityNotFoundException::new);
        LOGGER.debug("IdeaServiceImpl found user {}", theme);
        idea.setTheme(theme);
    }

    @Override
    public void setYouthCouncilOfIdea(Idea idea, long youthCouncilId) {
        LOGGER.info("IdeaServiceImpl is running setYouthCouncilOfIdea");
        YouthCouncil youthCouncil = youthCouncilRepository.findById(youthCouncilId).orElseThrow(EntityNotFoundException::new);
        LOGGER.debug("IdeaServiceImpl found youth council {}", youthCouncil);
        idea.setYouthCouncil(youthCouncil);
    }

    @Override
    public Idea createIdea(Idea idea) {
        LOGGER.info("IdeaServiceImpl is running createIdea");
        return ideaRepository.save(idea);
    }

    public List<Idea> getIdeasByYouthCouncilId(long youthCouncilId) {
        LOGGER.info("IdeaServiceImpl is running getIdeasOfYouthCouncil");
        YouthCouncil youthCouncil = youthCouncilRepository.findById(youthCouncilId).orElseThrow(EntityNotFoundException::new);
        LOGGER.debug("Searching for ideas of youth council {}", youthCouncil);
        List<Idea> ideas = ideaRepository.findByYouthCouncil(youthCouncil);
        LOGGER.debug("Returning ideas {}", ideas);
        ideas.forEach(idea -> {
            idea.setComments(getCommentsOfIdea(idea));
            idea.setLikes(getLikesOfIdea(idea));
        });
        return ideas;
    }

    public List<IdeaLike> getLikesOfIdea(Idea idea){
        LOGGER.info("IdeaServiceImpl is running getLikesOfIdea");
        List<IdeaLike> ideaLikes = ideaLikeRepository.findByIdeaLikeId_Idea(idea);
        LOGGER.debug("Returning ideaLikes {}", ideaLikes);
        return ideaLikes;
    }

    public List<IdeaComment> getCommentsOfIdea(Idea idea){
        LOGGER.info("IdeaServiceImpl is running getCommentsOfIdea");
        List<IdeaComment> ideaComments = ideaCommentRepository.findByIdea(idea);
        LOGGER.debug("Returning ideaComments {}", ideaComments);
        return ideaComments;
    }

    public List<Idea> getIdeasByYouthCouncilIdAndUserId(long youthCouncilId, long userId) {
        LOGGER.info("IdeaServiceImpl is running getIdeasOfYouthCouncilAndUser");
        YouthCouncil youthCouncil = youthCouncilRepository.findById(youthCouncilId).orElseThrow(EntityNotFoundException::new);
        LOGGER.debug("IdeaServiceImpl found youth council {}", youthCouncil);
        User user = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
        LOGGER.debug("IdeaServiceImpl found user {}", user);
        return ideaRepository.findByYouthCouncilAndAuthor(youthCouncil, user);

    }

    @Override
    public void setIdeaOfIdeaLike(IdeaLike ideaLike, long ideaId, long tenantId) {
        LOGGER.info("IdeaServiceImpl is running setIdeaOfIdeaLike");
        YouthCouncil youthCouncil = youthCouncilRepository.findById(tenantId).orElseThrow(EntityNotFoundException::new);
        Idea idea = ideaRepository.findByIdAndYouthCouncil(ideaId, youthCouncil).orElseThrow(EntityNotFoundException::new);
        LOGGER.debug("IdeaServiceImpl found idea {}", idea);
        ideaLike.getIdeaLikeId().setIdea(idea);
    }

    @Override
    public void setUserOfIdeaLike(IdeaLike ideaLike, long userId) {
        LOGGER.info("IdeaServiceImpl is running setUserOfIdeaLike");
        User user = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
        LOGGER.debug("IdeaServiceImpl found user {}", user);
        ideaLike.getIdeaLikeId().setLikedBy(user);
    }

    @Override
    public IdeaLike createIdeaLike(IdeaLike ideaLike) {
        LOGGER.info("IdeaServiceImpl is running createIdeaLike");
        if (!ideaLikeRepository.existsByUserIdAndIdeaId(
                ideaLike.getIdeaLikeId().getLikedBy().getId(),
                ideaLike.getIdeaLikeId().getIdea().getId())) { // stops same user liking post more than once
            return ideaLikeRepository.save(ideaLike);
        }
        return null;
    }

    @Override
    public boolean userAndIdeaInSameYouthCouncil(long userId, long ideaId, long youthCouncilId) {
        LOGGER.info("IdeaServiceImpl is running userAndIdeaInSameYouthCouncil");
        return ideaRepository.ideaBelongsToYouthCouncil(ideaId, youthCouncilId) && membershipRepository.userIsMemberOfYouthCouncil(userId, youthCouncilId);
    }

    @Override
    public List<String> getImagesOfIdea(long ideaId) {
        LOGGER.info("IdeaServiceImpl is running getImagesOfIdea");
        return ideaRepository.getImagesByIdeaId(ideaId);
    }

    @Override
    public Idea getIdeaById(long youthCouncilId, long ideaId) {
        LOGGER.info("IdeaServiceImpl is running getIdeaById");
        YouthCouncil youthCouncil = youthCouncilRepository.findById(youthCouncilId).orElseThrow(EntityNotFoundException::new);
        Idea idea = ideaRepository.findByIdAndYouthCouncil(ideaId, youthCouncil).orElseThrow(EntityNotFoundException::new);
        return idea;
    }

    @Override
    public IdeaViewModel mapToIdeaViewModel(Idea idea) {
        LOGGER.info("IdeaServiceImpl is running mapToIdeaViewModel");
        IdeaViewModel ideaViewModel = new IdeaViewModel();
        ideaViewModel.setId(idea.getId());
        ideaViewModel.setDescription(idea.getDescription());
        ideaViewModel.setImages(idea.getImages());
        // Later on, action point should also be linked to an idea
        List<IdeaComment> ideaComments = ideaCommentRepository.findByIdea(idea);
        List<CommentViewModel> commentViewModels = ideaComments.stream().map(c -> new CommentViewModel(c.getId(), c.getContent(), c.getAuthor().getUsername(), c.getCreatedDate())).toList();
        ideaViewModel.setComments(commentViewModels);
        ideaViewModel.setNumberOfLikes(ideaLikeRepository.countAllByIdeaLikeId_Idea(idea));
        ideaViewModel.setTheme(idea.getTheme().getName());
        ideaViewModel.setAuthor(idea.getAuthor().getFirstName() + " " + idea.getAuthor().getLastName());
        ideaViewModel.setDateAdded(idea.getCreatedDate());
        return ideaViewModel;
    }

    @Override
    public void setAuthorOfIdeaComment(IdeaComment ideaComment, long userId) {
        LOGGER.info("IdeaServiceImpl is running setAuthorOfIdeaComment");
        User user = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
        LOGGER.debug("Retrieved user {}", user);
        ideaComment.setAuthor(user);
    }

    @Override
    public void setIdeaOfIdeaComment(IdeaComment ideaComment, long ideaId) {
        LOGGER.info("IdeaServiceImpl is running setIdeaOfIdeaComment");
        Idea idea = ideaRepository.findById(ideaId).orElseThrow(EntityNotFoundException::new);
        LOGGER.debug("Retrieved idea {}", idea);
        ideaComment.setIdea(idea);
    }

    @Override
    public IdeaComment createIdeaComment(IdeaComment ideaComment) {
        LOGGER.info("IdeaServiceImpl is running createIdeaComment");
        return ideaCommentRepository.save(ideaComment);
    }
}