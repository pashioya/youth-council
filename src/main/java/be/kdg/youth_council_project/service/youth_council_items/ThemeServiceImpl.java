package be.kdg.youth_council_project.service.youth_council_items;

import be.kdg.youth_council_project.domain.platform.youth_council_items.Theme;
import be.kdg.youth_council_project.repository.StandardActionRepository;
import be.kdg.youth_council_project.repository.ThemeRepository;
import be.kdg.youth_council_project.repository.idea.IdeaRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ThemeServiceImpl implements ThemeService {
    private final ThemeRepository themeRepository;
    private final StandardActionRepository standardActionRepository;
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
    public void deleteTheme(Long themeId) {
        LOGGER.info("ThemeServiceImpl is running deleteTheme");
        ideaRepository.findAllByThemeId(themeId).forEach(idea -> {
            idea.setTheme(null);
            ideaRepository.save(idea);
        });
        standardActionRepository.findAllByThemeId(themeId).forEach(standardAction -> {
            standardAction.setTheme(null);
            standardActionRepository.save(standardAction);
        });
        themeRepository.deleteById(themeId);
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
