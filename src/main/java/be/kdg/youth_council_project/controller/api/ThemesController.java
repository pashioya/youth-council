package be.kdg.youth_council_project.controller.api;

import be.kdg.youth_council_project.controller.api.dtos.ThemeDto;
import be.kdg.youth_council_project.service.youth_council_items.ThemeService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/themes")
@AllArgsConstructor
public class ThemesController {
    private final ThemeService themeService;
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private final ModelMapper modelMapper;
    @GetMapping
    public ResponseEntity<List<ThemeDto>> getAllThemes(){
        LOGGER.info("ThemesController is running getAllThemes");
        var themes = themeService.getAllThemes();
        if (themes.isEmpty()) {
            return new ResponseEntity<>(
                    HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(
                    themes.stream().map(theme -> modelMapper.map(theme, ThemeDto.class)).toList(),
                    HttpStatus.OK);
        }
    }

    @DeleteMapping("/{themeId}")
    public ResponseEntity<HttpStatus> deleteTheme(@PathVariable("themeId") long themeId){
        LOGGER.info("GeneralAdminDashboardController is running deleteTheme");
        themeService.removeTheme(themeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
