package be.kdg.youth_council_project.service;


import be.kdg.youth_council_project.domain.platform.User;
import be.kdg.youth_council_project.domain.platform.YouthCouncil;
import be.kdg.youth_council_project.domain.platform.youthCouncilItems.ActionPoint;
import be.kdg.youth_council_project.domain.platform.youthCouncilItems.Idea;
import be.kdg.youth_council_project.domain.platform.youthCouncilItems.Theme;
import be.kdg.youth_council_project.domain.platform.youthCouncilItems.like.IdeaLike;
import be.kdg.youth_council_project.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;


import java.util.List;
import java.util.Optional;

@Service
public class IdeaServiceImpl implements IdeaService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final IdeaRepository ideaRepository;
    private final IdeaLikeRepository ideaLikeRepository;

    private final UserRepository userRepository;

    private final ThemeRepository themeRepository;

    private final YouthCouncilRepository youthCouncilRepository;

    private final MembershipRepository membershipRepository;


    public IdeaServiceImpl(IdeaRepository ideaRepository, IdeaLikeRepository ideaLikeRepository, UserRepository userRepository, ThemeRepository themeRepository, YouthCouncilRepository youthCouncilRepository, MembershipRepository membershipRepository) {
        this.ideaRepository = ideaRepository;
        this.ideaLikeRepository = ideaLikeRepository;
        this.userRepository = userRepository;
        this.themeRepository = themeRepository;
        this.youthCouncilRepository = youthCouncilRepository;
        this.membershipRepository = membershipRepository;
    }

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
        return ideaRepository.findByYouthCouncil(youthCouncil);
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
    public void setIdeaOfIdeaLike(IdeaLike ideaLike, long ideaId) {
        LOGGER.info("IdeaServiceImpl is running setIdeaOfIdeaLike");
        Idea idea = ideaRepository.findById(ideaId).orElseThrow(EntityNotFoundException::new);
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
    public List<String> getImagesOfIdea(long ideaId){
        LOGGER.info("IdeaServiceImpl is running getImagesOfIdea");
        return ideaRepository.getImagesByIdeaId(ideaId);
    }

    @Override
    public Idea getIdeaById(long youthCouncilId, long ideaId) {
        LOGGER.info("IdeaServiceImpl is running getIdeaById");
        Idea idea = ideaRepository.findById(ideaId).orElseThrow(EntityNotFoundException::new);
        if (idea.getYouthCouncil().getId() == youthCouncilId){
            // youthCouncilId in URL must be for a youth council that owns the requested ActionPoint
            return idea;
        }
        throw new EntityNotFoundException(String.format("Idea with id %d does not belong to YouthCouncil with id %d", ideaId, youthCouncilId));
    }
}