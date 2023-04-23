package be.kdg.youth_council_project.service.webpage;

import be.kdg.youth_council_project.domain.webpage.InformativePageTemplate;
import be.kdg.youth_council_project.domain.webpage.InformativePageTemplateSection;

import java.util.List;

public interface InformativePageTemplateService {
    InformativePageTemplate addWebPageTemplate(String title, List<InformativePageTemplateSection> sections);

    List<InformativePageTemplate> getTemplates();
}
