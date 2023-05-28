package be.kdg.youth_council_project.controller.api;

import be.kdg.youth_council_project.controller.api.dtos.ThemeDto;
import be.kdg.youth_council_project.domain.platform.youth_council_items.Theme;
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
    public ResponseEntity<List<ThemeDto>> getAllThemes() {
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

    @PostMapping
    public ResponseEntity<ThemeDto> addTheme(@RequestBody String name) {
        try {
            Theme theme = themeService.createTheme(new Theme(name));
            return ResponseEntity.status(HttpStatus.CREATED).body(modelMapper.map(theme, ThemeDto.class));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ThemeDto> deleteTheme(@PathVariable long id) {
        try {
            themeService.deleteTheme(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ThemeDto> updateTheme(@PathVariable long id, @RequestBody String name) {
        try {
            Theme theme = themeService.updateTheme(id, name);
            return ResponseEntity.status(HttpStatus.OK).body(modelMapper.map(theme, ThemeDto.class));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
