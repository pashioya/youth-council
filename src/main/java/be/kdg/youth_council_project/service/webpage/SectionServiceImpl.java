package be.kdg.youth_council_project.service.webpage;

import be.kdg.youth_council_project.controller.api.dtos.SectionDto;
import be.kdg.youth_council_project.domain.webpage.Section;
import be.kdg.youth_council_project.repository.webpage.SectionRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@AllArgsConstructor
public class SectionServiceImpl implements SectionService{
    @Autowired
    private final SectionRepository sectionRepository;
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
    public Section updateSection(long webpageId, long sectionId, SectionDto sectionDto) {
        return null;
    }
}
