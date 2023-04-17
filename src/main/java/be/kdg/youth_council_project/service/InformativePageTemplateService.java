package be.kdg.youth_council_project.service;

import be.kdg.youth_council_project.domain.webpage.InformativePageTemplate;
import be.kdg.youth_council_project.domain.webpage.InformativePageTemplateSection;

import java.util.List;

public interface InformativePageTemplateService {
    InformativePageTemplate addWebPageTemplate(String title, List<InformativePageTemplateSection> sections);
}
