package be.kdg.youth_council_project.service;


import be.kdg.youth_council_project.domain.platform.User;
import be.kdg.youth_council_project.domain.platform.YouthCouncil;
import be.kdg.youth_council_project.domain.platform.youthCouncilItems.Idea;
import be.kdg.youth_council_project.domain.platform.youthCouncilItems.Theme;
import be.kdg.youth_council_project.repository.IdeaRepository;
import be.kdg.youth_council_project.repository.ThemeRepository;
import be.kdg.youth_council_project.repository.UserRepository;
import be.kdg.youth_council_project.repository.YouthCouncilRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class IdeaServiceImpl implements IdeaService{

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
    public boolean setAuthorOfIdea(Idea idea, long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            idea.setAuthor(userOptional.get());
            return true;
        }
        return false;
    }

    @Override
    public boolean setThemeOfIdea(Idea idea, long themeId) {
        Optional<Theme> themeOptional = themeRepository.findById(themeId);
        if (themeOptional.isPresent()) {
            idea.setTheme(themeOptional.get());
            return true;
        }
        return false;
    }

    @Override
    public boolean setYouthCouncilOfIdea(Idea idea, int youthCouncilId){
        Optional<YouthCouncil> youthCouncilOptional = youthCouncilRepository.findById(youthCouncilId);
        if (youthCouncilOptional.isPresent()) {
            idea.setYouthCouncil(youthCouncilOptional.get());
            return true;
        }
        return false;
    }

    @Override
    public Idea createIdea(Idea idea) {
        return ideaRepository.save(idea);
    }
}
