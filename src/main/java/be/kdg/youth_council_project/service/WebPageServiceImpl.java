package be.kdg.youth_council_project.service;

import be.kdg.youth_council_project.repository.WebPageRepository;
import be.kdg.youth_council_project.util.WebPage;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class WebPageServiceImpl implements WebPageService{
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private final WebPageRepository webPageRepository;

    @Override
    public WebPage getWebPageByYouthCouncilId(long youthCouncilId) {
        LOGGER.info("WebPageServiceImpl is running getWebPageByYouthCouncilId");
        return webPageRepository.findByYouthCouncilId(youthCouncilId);
    }
}
