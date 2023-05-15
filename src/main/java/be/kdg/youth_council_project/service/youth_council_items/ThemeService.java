package be.kdg.youth_council_project.service.youth_council_items;

import be.kdg.youth_council_project.domain.platform.youth_council_items.Theme;

import java.util.List;

public interface ThemeService {

    Theme getThemeById(Long themeId);

    List<Theme> getAllThemes();
    void removeTheme(long themeId, long tenantId);
}
