package be.kdg.youth_council_project.controller.api;

import be.kdg.youth_council_project.controller.api.dtos.SectionDto;
import be.kdg.youth_council_project.domain.webpage.Section;
import be.kdg.youth_council_project.tenants.TenantId;
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

    @PatchMapping("/{sectionId}")
    public ResponseEntity<SectionDto> updateWebPageSection(@PathVariable long sectionId, @PathVariable long webpageId,
                                                           @RequestBody SectionDto sectionDto) {
        LOGGER.info("WebPageController is running updateWebPageSection");
        Section section = sectionService.updateSection(sectionId,webpageId, modelMapper.map(sectionDto, Section.class));
        return ResponseEntity.ok(modelMapper.map(section, SectionDto.class));
    }

    @PostMapping
    public ResponseEntity<SectionDto> addSection(@TenantId long tenantId,@PathVariable long webpageId,
                                                 @RequestBody SectionDto sectionDto) {
        LOGGER.info("WebPageController is running addSection");
        return ResponseEntity.ok(modelMapper.map(
                sectionService.addSection(tenantId, webpageId, modelMapper.map(
                        sectionDto,
                        Section.class)),
                SectionDto.class));
    }
    @DeleteMapping("/{sectionId}")
    public ResponseEntity deleteSection(@TenantId long tenantId, @PathVariable long sectionId,
                                        @PathVariable long webpageId) {
        LOGGER.info("WebPageController is running deleteSection");
        sectionService.deleteSection(tenantId,sectionId,webpageId);
        return ResponseEntity.ok().build();
    }
}
