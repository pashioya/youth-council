package be.kdg.youth_council_project.service.youth_council_items;


import be.kdg.youth_council_project.domain.platform.User;
import be.kdg.youth_council_project.domain.platform.YouthCouncil;
import be.kdg.youth_council_project.domain.platform.youth_council_items.Idea;
import be.kdg.youth_council_project.domain.platform.youth_council_items.Theme;
import be.kdg.youth_council_project.domain.platform.youth_council_items.comments.IdeaComment;
import be.kdg.youth_council_project.domain.platform.youth_council_items.images.IdeaImage;
import be.kdg.youth_council_project.domain.platform.youth_council_items.like.IdeaLike;
import be.kdg.youth_council_project.repository.ThemeRepository;
import be.kdg.youth_council_project.repository.UserRepository;
import be.kdg.youth_council_project.repository.YouthCouncilRepository;
import be.kdg.youth_council_project.repository.idea.IdeaCommentRepository;
import be.kdg.youth_council_project.repository.idea.IdeaImageRepository;
import be.kdg.youth_council_project.repository.idea.IdeaLikeRepository;
import be.kdg.youth_council_project.repository.idea.IdeaRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.io.IOException;
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
    private final IdeaCommentRepository ideaCommentRepository;
    private final IdeaImageRepository ideaImageRepository;


    @Override
    public List<Idea> getAllIdeas() {
        LOGGER.info("IdeaServiceImpl is running getAllIdeas");
        return ideaRepository.findAll();
    }

    @Override
    public void setAuthorOfIdea(Idea idea, long userId, long youthCouncilId) {
        LOGGER.info("IdeaServiceImpl is running setAuthorOfIdea");
        User user = userRepository.findByIdAndYouthCouncilId(userId, youthCouncilId).orElseThrow(EntityNotFoundException::new);
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

    private List<IdeaLike> getLikesOfIdea(Idea idea) {
        LOGGER.info("IdeaServiceImpl is running getLikesOfIdea");
        List<IdeaLike> ideaLikes = ideaLikeRepository.findById_Idea(idea);
        LOGGER.debug("Returning ideaLikes {}", ideaLikes);
        return ideaLikes;
    }

    private List<IdeaComment> getCommentsOfIdea(Idea idea) {
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
    public void setIdeaOfIdeaLike(IdeaLike ideaLike, long ideaId, long youthCouncilId) {
        LOGGER.info("IdeaServiceImpl is running setIdeaOfIdeaLike");
        Idea idea = ideaRepository.findByIdAndYouthCouncilId(ideaId, youthCouncilId).orElseThrow(EntityNotFoundException::new);
        LOGGER.debug("IdeaServiceImpl found idea {}", idea);
        ideaLike.getId().setIdea(idea);
    }

    @Override
    public void setUserOfIdeaLike(IdeaLike ideaLike, long userId, long youthCouncilId) {
        LOGGER.info("IdeaServiceImpl is running setUserOfIdeaLike");
        User user = userRepository.findByIdAndYouthCouncilId(userId, youthCouncilId).orElseThrow(EntityNotFoundException::new);
        LOGGER.debug("IdeaServiceImpl found user {}", user);
        ideaLike.getId().setLikedBy(user);
    }

    @Override
    public boolean createIdeaLike(IdeaLike ideaLike) {
        LOGGER.info("IdeaServiceImpl is running createIdeaLike");
        if (!ideaLikeRepository.existsByUserIdAndIdeaId(
                ideaLike.getId().getLikedBy().getId(),
                ideaLike.getId().getIdea().getId())) { // stops same user liking post more than once
            ideaLikeRepository.save(ideaLike);
            return true;
        }
        return false;
    }


    @Override
    public List<IdeaImage> getImagesOfIdea(long ideaId) {
        LOGGER.info("IdeaServiceImpl is running getImagesOfIdea");
        return ideaImageRepository.getImagesByIdeaId(ideaId);
    }

    @Override
    public Idea getIdeaById(long youthCouncilId, long ideaId) {
        LOGGER.info("IdeaServiceImpl is running getIdeaById");
        return ideaRepository.findByIdAndYouthCouncilId(ideaId, youthCouncilId).orElseThrow(EntityNotFoundException::new);
    }


    @Override
    public void setAuthorOfIdeaComment(IdeaComment ideaComment, long userId, long tenantId) {
        LOGGER.info("IdeaServiceImpl is running setAuthorOfIdeaComment");
        User user = userRepository.findByIdAndYouthCouncilId(userId, tenantId).orElseThrow(EntityNotFoundException::new);
        LOGGER.debug("Retrieved user {}", user);
        ideaComment.setAuthor(user);
    }

    @Override
    public void setIdeaOfIdeaComment(IdeaComment ideaComment, long ideaId, long tenantId) {
        LOGGER.info("IdeaServiceImpl is running setIdeaOfIdeaComment");
        Idea idea = ideaRepository.findByIdAndYouthCouncilId(ideaId, tenantId).orElseThrow(EntityNotFoundException::new);
        LOGGER.debug("Retrieved idea {}", idea);
        ideaComment.setIdea(idea);
    }

    @Override
    public IdeaComment createIdeaComment(IdeaComment ideaComment) {
        LOGGER.info("IdeaServiceImpl is running createIdeaComment");
        return ideaCommentRepository.save(ideaComment);
    }

    @Override
    public void removeIdeaLike(long actionPointId, long userId, long youthCouncilID) {
        LOGGER.info("IdeaServiceImpl is running removeIdeaLike");
        if (!userRepository.existsById(userId)) {
            throw new EntityNotFoundException();
        }
        IdeaLike ideaLike = ideaLikeRepository.findByIdeaIdAndUserIdAndYouthCouncilId(actionPointId, userId,
                youthCouncilID).orElseThrow(EntityNotFoundException::new);
        ideaLikeRepository.delete(ideaLike);
    }

    @Override
    @Transactional
    public void removeIdea(long ideaId, long youthCouncilId) {
        LOGGER.info("IdeaServiceImpl is running removeIdea");
        Idea idea = ideaRepository.findByIdAndYouthCouncilId(ideaId, youthCouncilId).orElseThrow(EntityNotFoundException::new);
        ideaRepository.deleteActionPointLinksById(ideaId);
        ideaRepository.delete(idea);
    }

    @Override
    @Transactional
    public void addImageToIdea(Idea createdIdea, MultipartFile image) {
        LOGGER.info("IdeaServiceImpl is running addImageToIdea");
        try {
            IdeaImage ideaImage = new IdeaImage();
            ideaImage.setIdea(createdIdea);
            ideaImage.setImage(image.getBytes());
            ideaImageRepository.save(ideaImage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isLikedByUser(Long id, long userId) {
        LOGGER.info("IdeaServiceImpl is running isLikedByUser");
        return ideaLikeRepository.existsByUserIdAndIdeaId(userId, id);
    }

    @Override
    public List<Idea> getIdeasByUserId(long userId) {
        LOGGER.info("IdeaServiceImpl is running getIdeasByUserId");
        return ideaRepository.findByAuthor(userId);
    }

    @Override
    public List<IdeaComment> getCommentsByUserId(long userId) {
        LOGGER.info("IdeaServiceImpl is running getCommentsByUserId");
        return ideaCommentRepository.findByAuthorId(userId);
    }
}