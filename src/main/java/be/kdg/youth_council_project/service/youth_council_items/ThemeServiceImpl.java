package be.kdg.youth_council_project.service.youth_council_items;

import be.kdg.youth_council_project.domain.platform.youth_council_items.ActionPoint;
import be.kdg.youth_council_project.domain.platform.youth_council_items.Idea;
import be.kdg.youth_council_project.domain.platform.youth_council_items.Theme;
import be.kdg.youth_council_project.repository.ThemeRepository;
import be.kdg.youth_council_project.repository.action_point.ActionPointRepository;
import be.kdg.youth_council_project.repository.idea.IdeaRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
public class ThemeServiceImpl implements ThemeService {
    private final ThemeRepository themeRepository;
    private final ActionPointRepository actionPointRepository;
    private final IdeaRepository ideaRepository;
    private final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(this.getClass());

    @Override
    public Theme getThemeById(Long themeId) {
        LOGGER.info("ThemeServiceImpl is running getThemeById");
        return themeRepository.findById(themeId).orElse(null);
    }

    @Override
    public List<Theme> getAllThemes() {
        LOGGER.info("ThemeServiceImpl is running getAllThemes");
        return themeRepository.findAll();
    }

    @Override
    public Theme createTheme(Theme theme) {
        LOGGER.info("ThemeServiceImpl is running createTheme");
        return themeRepository.save(theme);
    }

    @Override
    @Transactional
    public void deleteTheme(Long themeId) {
        LOGGER.info("ThemeServiceImpl is running deleteTheme");
        themeRepository.deleteById(themeId);
        Idea idea = (Idea) ideaRepository.findByThemeId(themeId);
        ActionPoint actionPoint = actionPointRepository.findActionPointByLinkedIdeasId(idea.getId()).orElse(null);
        if (actionPoint == null){
            themeRepository.deleteById(themeId);
        } else {
            LOGGER.info("Cannot delete theme!");
        }
    }

    @Override
    public Theme updateTheme(long id, String name) {
        LOGGER.info("ThemeServiceImpl is running updateTheme");
        Theme theme = themeRepository.findById(id).orElse(null);
        if (theme == null) {
            return null;
        }
        theme.setName(name);
        return themeRepository.save(theme);
    }
}
