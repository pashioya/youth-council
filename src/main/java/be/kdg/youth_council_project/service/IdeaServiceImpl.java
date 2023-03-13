package be.kdg.youth_council_project.service;


import be.kdg.youth_council_project.domain.platform.User;
import be.kdg.youth_council_project.domain.platform.YouthCouncil;
import be.kdg.youth_council_project.domain.platform.youthCouncilItems.Idea;
import be.kdg.youth_council_project.domain.platform.youthCouncilItems.Theme;
import be.kdg.youth_council_project.repository.IdeaRepository;
import be.kdg.youth_council_project.repository.ThemeRepository;
import be.kdg.youth_council_project.repository.UserRepository;
import be.kdg.youth_council_project.repository.YouthCouncilRepository;
import javax.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IdeaServiceImpl implements IdeaService {

    private final IdeaRepository ideaRepository;

    private final UserRepository userRepository;

    private final ThemeRepository themeRepository;

    private final YouthCouncilRepository youthCouncilRepository;


    public IdeaServiceImpl(IdeaRepository ideaRepository, UserRepository userRepository, ThemeRepository themeRepository, YouthCouncilRepository youthCouncilRepository) {
        this.ideaRepository = ideaRepository;
        this.userRepository = userRepository;
        this.themeRepository = themeRepository;
        this.youthCouncilRepository = youthCouncilRepository;
    }

    @Override
    public void setAuthorOfIdea(Idea idea, long userId) {
        User user = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
        idea.setAuthor(user);

    }

    @Override
    public void setThemeOfIdea(Idea idea, long themeId) {
        Theme theme = themeRepository.findById(themeId).orElseThrow(EntityNotFoundException::new);
        idea.setTheme(theme);
    }

    @Override
    public void setYouthCouncilOfIdea(Idea idea, long youthCouncilId) {
        YouthCouncil youthCouncil = youthCouncilRepository.findById(youthCouncilId).orElseThrow(EntityNotFoundException::new);
        idea.setYouthCouncil(youthCouncil);
    }

    @Override
    public Idea createIdea(Idea idea) {
        return ideaRepository.save(idea);
    }

    public List<Idea> getIdeasOfYouthCouncil(long youthCouncilId) {
        if (youthCouncilRepository.existsById(youthCouncilId)) {
            return ideaRepository.findByYouthCouncilId(youthCouncilId);
        } else {
            throw new EntityNotFoundException();
        }
    }

    public List<Idea> getIdeasOfYouthCouncilAndUser(long youthCouncilId, long userId) {
        if (youthCouncilRepository.existsById(youthCouncilId) && userRepository.existsById(userId)) {
            return ideaRepository.findByYouthCouncilIdAndUserId(youthCouncilId, userId);
        } else {
            throw new EntityNotFoundException();
        }
    }
}