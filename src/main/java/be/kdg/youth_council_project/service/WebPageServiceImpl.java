package be.kdg.youth_council_project.service;

import be.kdg.youth_council_project.domain.platform.YouthCouncil;
import be.kdg.youth_council_project.domain.webpage.WebPage;
import be.kdg.youth_council_project.repository.webpage.InformativePageRepository;
import be.kdg.youth_council_project.repository.webpage.WebPageRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@AllArgsConstructor
public class WebPageServiceImpl implements WebPageService{
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private final WebPageRepository webPageRepository;
    private final InformativePageRepository infoPageRepository;



    @Override
    public WebPage getHomePageByYouthCouncilId(long youthCouncilId) {
        LOGGER.info("WebPageServiceImpl is running getWebPageByYouthCouncilId");
        return webPageRepository.findByYouthCouncilId(youthCouncilId).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public WebPage updateWebPage(long youthCouncilId, WebPage updatedWebPage) {
        LOGGER.info("WebPageServiceImpl is running updateWebPage");
        WebPage webPage =
                webPageRepository.findByYouthCouncilId(youthCouncilId).orElseThrow(EntityNotFoundException::new);
        webPage.setCallForIdeasEnabled(updatedWebPage.isCallForIdeasEnabled());
        webPage.setActivitiesEnabled(updatedWebPage.isActivitiesEnabled());
        webPage.setNewsItemsEnabled(updatedWebPage.isNewsItemsEnabled());
        webPage.setActionPointsEnabled(updatedWebPage.isActionPointsEnabled());
        webPage.setElectionInformationEnabled(updatedWebPage.isElectionInformationEnabled());
        return webPageRepository.save(webPage);
    }

    @Override
    public WebPage createHomePageOfYouthCouncil(YouthCouncil youthCouncil) {
        WebPage homepage = new WebPage();
        homepage.setYouthCouncil(youthCouncil);
        homepage.setTitle(youthCouncil.getName());
        return webPageRepository.save(homepage);
    }
}
