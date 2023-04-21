package be.kdg.youth_council_project.controller.api;

import be.kdg.youth_council_project.controller.api.dtos.SectionDto;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import be.kdg.youth_council_project.service.webpage.SectionService;

@RestController
@RequestMapping("/api/webpages/{webpageId}/sections")
@AllArgsConstructor
public class SectionController {
    private final SectionService sectionService;
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private final ModelMapper modelMapper;

    @PutMapping("/{sectionId}")
    public ResponseEntity<SectionDto> updateSection(@PathVariable long webpageId, @PathVariable long sectionId, @RequestBody SectionDto sectionDto) {
        LOGGER.info("SectionController is running updateSection");
        return ResponseEntity.ok(modelMapper.map(sectionService.updateSection(webpageId, sectionId, sectionDto), SectionDto.class));
    }

}
