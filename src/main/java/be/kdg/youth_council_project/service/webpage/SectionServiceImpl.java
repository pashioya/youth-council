package be.kdg.youth_council_project.service.webpage;

import be.kdg.youth_council_project.controller.api.dtos.SectionDto;
import be.kdg.youth_council_project.domain.webpage.Section;
import be.kdg.youth_council_project.domain.webpage.WebPage;
import be.kdg.youth_council_project.repository.webpage.SectionRepository;
import be.kdg.youth_council_project.repository.webpage.WebPageRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@AllArgsConstructor
public class SectionServiceImpl implements SectionService{
    private final SectionRepository sectionRepository;
    private final WebPageRepository webPageRepository;
    private final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(this.getClass());

    public List<Section> getAllSections() {
        LOGGER.info("SectionServiceImpl is running getAllSections");
        return sectionRepository.findAll();
    }
    public Section getSectionById(long id) {
        LOGGER.info("SectionServiceImpl is running getSectionById");
        return sectionRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }
    public void createSection(Section createdSection) {
        LOGGER.info("SectionServiceImpl is running createSection");
        LOGGER.debug("Saving section {}", createdSection);
        sectionRepository.save(createdSection);
    }


    @Override
    public Section updateSection(long sectionId, long webpageId, Section section) {
        LOGGER.info("WebPageServiceImpl is running updateWebPageSection");
        Section sectionToUpdate =
                sectionRepository.findByIdAndPageId(sectionId, webpageId)
                        .orElseThrow(EntityNotFoundException::new);
        sectionToUpdate.setHeading(section.getHeading());
        sectionToUpdate.setBody(section.getBody());
        sectionRepository.save(sectionToUpdate);
        return sectionToUpdate;
    }

    @Override
    public Section addSection(long tenantId, long webPageId, Section section) {
        LOGGER.info("WebPageServiceImpl is running addSection");
        WebPage webPage = webPageRepository.findByIdAndYouthCouncilId(webPageId, tenantId)
                .orElseThrow(EntityNotFoundException::new);
        section.setPage(webPage);
        return sectionRepository.save(section);
    }

    @Override
    public void deleteSection(long tenantId, long sectionId, long webpageId) {
        LOGGER.info("WebPageServiceImpl is running deleteSection");
        Section sectionToDelete =
                sectionRepository.findByIdAndPageId(sectionId, webpageId)
                        .orElseThrow(EntityNotFoundException::new);
        sectionRepository.delete(sectionToDelete);
    }


}
