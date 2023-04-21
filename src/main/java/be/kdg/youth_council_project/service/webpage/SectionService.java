package be.kdg.youth_council_project.service.webpage;

import be.kdg.youth_council_project.controller.api.dtos.SectionDto;
import be.kdg.youth_council_project.domain.webpage.Section;
import be.kdg.youth_council_project.repository.webpage.SectionRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SectionService {
    Section updateSection(long webpageId, long sectionId, SectionDto sectionDto);
}
