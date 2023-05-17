package be.kdg.youth_council_project.service.youth_council_items;

import be.kdg.youth_council_project.domain.platform.youth_council_items.Theme;

import java.util.List;

public interface ThemeService {

    Theme getThemeById(Long themeId);

    List<Theme> getAllThemes();

    Theme createTheme(Theme theme);

    void deleteTheme(Long themeId);

    Theme updateTheme(long id, String name);
}
