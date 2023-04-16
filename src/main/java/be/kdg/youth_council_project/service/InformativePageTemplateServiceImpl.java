package be.kdg.youth_council_project.service;


import be.kdg.youth_council_project.domain.webpage.InformativePageTemplate;
import be.kdg.youth_council_project.domain.webpage.InformativePageTemplateSection;
import be.kdg.youth_council_project.repository.webpage.InformativePageTemplateRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class InformativePageTemplateServiceImpl implements InformativePageTemplateService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private final InformativePageTemplateRepository templateRepository;

    @Override
    public InformativePageTemplate addWebPageTemplate(String title, List<InformativePageTemplateSection> sections) {
        LOGGER.info("InformativePageTemplateServiceImpl is running addWebPageTemplate");
        InformativePageTemplate template = new InformativePageTemplate();
        template.setTitle(title);
        template.setSections(sections);
        sections.forEach(section -> section.setTemplate(template));
        return templateRepository.save(template);
    }
}
