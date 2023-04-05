package be.kdg.youth_council_project.service;

import be.kdg.youth_council_project.domain.platform.youth_council_items.Theme;
import be.kdg.youth_council_project.repository.ThemeRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ThemeServiceImpl implements ThemeService{
    private final ThemeRepository themeRepository;
    private final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(this.getClass());

    @Override
    public List<Theme> getAllThemes() {
        LOGGER.info("ThemeServiceImpl is running getAllThemes");
        return themeRepository.findAll();
    }
}
