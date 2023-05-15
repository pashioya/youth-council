package be.kdg.youth_council_project.service.youth_council_items;

import be.kdg.youth_council_project.domain.platform.youth_council_items.Theme;
import be.kdg.youth_council_project.repository.StandardActionRepository;
import be.kdg.youth_council_project.repository.ThemeRepository;
import be.kdg.youth_council_project.repository.idea.IdeaRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
public class ThemeServiceImpl implements ThemeService {
    private final ThemeRepository themeRepository;
    private final IdeaRepository ideaRepository;
    private final StandardActionRepository standardActionRepository;
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
    @Transactional
    public void removeTheme(long themeId) {
        LOGGER.info("ThemeServiceImpl is running removeTheme");
        standardActionRepository.deleteAllByThemeId(themeId);
        ideaRepository.deleteIdeaByThemeId(themeId);
        themeRepository.deleteById(themeId);
    }
}
