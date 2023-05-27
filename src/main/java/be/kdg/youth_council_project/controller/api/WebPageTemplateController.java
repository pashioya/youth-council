package be.kdg.youth_council_project.controller.api;


import be.kdg.youth_council_project.controller.api.dtos.InformativePageTemplateDto;
import be.kdg.youth_council_project.controller.api.dtos.NewInformativePageTemplateDto;
import be.kdg.youth_council_project.controller.api.dtos.SectionDto;
import be.kdg.youth_council_project.domain.webpage.InformativePageTemplate;
import be.kdg.youth_council_project.domain.webpage.InformativePageTemplateSection;
import be.kdg.youth_council_project.service.webpage.InformativePageTemplateService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/webpage-templates")
public class WebPageTemplateController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private final InformativePageTemplateService templateService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_GENERAL_ADMINISTRATOR')")
    public ResponseEntity<InformativePageTemplateDto> addWebPageTemplate(@RequestBody @Valid NewInformativePageTemplateDto newInformativePageTemplateDto) {
        LOGGER.info("WebPageController is running addWebPageTemplate");
        InformativePageTemplate template = templateService.addWebPageTemplate(newInformativePageTemplateDto.getTitle(), newInformativePageTemplateDto.getHeadingsBodies().entrySet().stream().map(set -> new InformativePageTemplateSection(set.getKey(), set.getValue())).toList());
        return new ResponseEntity<>(
                new InformativePageTemplateDto(
                        template.getId(),
                        template.getTitle(),
                        template.getSections().stream().map(
                                section -> new SectionDto(section.getHeader(), section.getBody())).toList()
                ),
                HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<InformativePageTemplateDto>> getWebPageTemplates() {
        List<InformativePageTemplate> templates = templateService.getTemplates();
        return new ResponseEntity<>(templates.stream().map(template -> new InformativePageTemplateDto(
                template.getId(),
                template.getTitle(),
                template.getSections().stream().map(
                        section -> new SectionDto(section.getHeader(), section.getBody())).toList()
        )).toList(), HttpStatus.OK);
    }
}
