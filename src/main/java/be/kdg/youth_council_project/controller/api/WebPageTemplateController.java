package be.kdg.youth_council_project.controller.api;


import be.kdg.youth_council_project.controller.api.dtos.*;
import be.kdg.youth_council_project.domain.webpage.InformativePageTemplate;
import be.kdg.youth_council_project.domain.webpage.InformativePageTemplateSection;
import be.kdg.youth_council_project.service.InformativePageTemplateService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/api/webpage-templates")
public class WebPageTemplateController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private final InformativePageTemplateService templateService;

    @PostMapping
    public ResponseEntity<InformativePageTemplateDto> addWebPageTemplate(@RequestBody @Valid NewInformativePageTemplateDto newInformativePageTemplateDto){
        LOGGER.info("WebPageController is running addWebPageTemplate");
        String title = newInformativePageTemplateDto.getTitle();
        List<InformativePageTemplateSection> sections = newInformativePageTemplateDto.getHeadingsBodies().entrySet().stream().map(set -> new InformativePageTemplateSection(set.getKey(), set.getValue())).collect(Collectors.toList());
        InformativePageTemplate template = templateService.addWebPageTemplate(title, sections);
        return new ResponseEntity<>(
                new InformativePageTemplateDto(
                        template.getId(),
                        template.getTitle(),
                        template.getSections().stream().map(
                                section -> new SectionDto(section.getHeader(), section.getBody())).toList()
                ),
                HttpStatus.OK);
    }
}
