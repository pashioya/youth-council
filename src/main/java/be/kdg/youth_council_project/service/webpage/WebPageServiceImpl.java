package be.kdg.youth_council_project.service.webpage;

import be.kdg.youth_council_project.domain.platform.YouthCouncil;
import be.kdg.youth_council_project.domain.webpage.Section;
import be.kdg.youth_council_project.domain.webpage.WebPage;
import be.kdg.youth_council_project.repository.webpage.InformativePageRepository;
import be.kdg.youth_council_project.repository.webpage.WebPageRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@AllArgsConstructor
public class WebPageServiceImpl implements WebPageService{
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private final WebPageRepository webPageRepository;
    private final InformativePageRepository infoPageRepository;



    @Override
    public WebPage getHomePageByYouthCouncilId(long youthCouncilId) {
        LOGGER.info("WebPageServiceImpl is running getHomePageByYouthCouncilId");
        return webPageRepository.findYouthCouncilHomePage(youthCouncilId).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public WebPage updateWebPage(long youthCouncilId, WebPage updatedWebPage) {
        LOGGER.info("WebPageServiceImpl is running updateWebPage");
        WebPage webPage =
                webPageRepository.findById(updatedWebPage.getId()).orElseThrow(EntityNotFoundException::new);
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

    @Override
    public List<WebPage> getAllWebPagesByYouthCouncilId(long tenantId) {
        LOGGER.info("WebPageServiceImpl is running getAllWebPagesByYouthCouncilId");
        return webPageRepository.findAllByYouthCouncilId(tenantId).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public WebPage getWebPageById(long webpageId) {
        LOGGER.info("WebPageServiceImpl is running getWebPageById");
        return webPageRepository.findById(webpageId).orElseThrow(EntityNotFoundException::new);
    }

}
