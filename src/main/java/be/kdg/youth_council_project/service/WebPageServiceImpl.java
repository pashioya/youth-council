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

    @Override
    public WebPage updateWebPage(long youthCouncilId, WebPage map) {
        LOGGER.info("WebPageServiceImpl is running updateWebPage");
        WebPage webPage = webPageRepository.findByYouthCouncilId(youthCouncilId);
        webPage.setCallForIdeasEnabled(map.isCallForIdeasEnabled());
        webPage.setActivitiesEnabled(map.isActivitiesEnabled());
        webPage.setNewsItemsEnabled(map.isNewsItemsEnabled());
        webPage.setActionPointsEnabled(map.isActionPointsEnabled());
        webPage.setElectionInformationEnabled(map.isElectionInformationEnabled());
        return webPageRepository.save(webPage);
    }

}
