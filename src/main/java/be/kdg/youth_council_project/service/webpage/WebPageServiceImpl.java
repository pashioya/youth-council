package be.kdg.youth_council_project.service.webpage;

import be.kdg.youth_council_project.domain.platform.YouthCouncil;
import be.kdg.youth_council_project.domain.webpage.WebPage;
import be.kdg.youth_council_project.repository.YouthCouncilRepository;
import be.kdg.youth_council_project.repository.webpage.InformativePageRepository;
import be.kdg.youth_council_project.repository.webpage.WebPageRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@AllArgsConstructor
public class WebPageServiceImpl implements WebPageService{
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private final WebPageRepository webPageRepository;
    private final YouthCouncilRepository youthCouncilRepository;
    private final InformativePageRepository infoPageRepository;



    @Override
    public WebPage getHomePageByYouthCouncilId(long youthCouncilId) {
        LOGGER.info("WebPageServiceImpl is running getHomePageByYouthCouncilId");
        return webPageRepository.findYouthCouncilHomePage(youthCouncilId).orElse(createHomePageOfYouthCouncil(youthCouncilRepository.findById(youthCouncilId).orElseThrow(EntityNotFoundException::new)));
    }

    @Override
    public WebPage updateWebPage(long youthCouncilId, WebPage updatedWebPage) {
        LOGGER.info("WebPageServiceImpl is running updateWebPage");
        WebPage webPage =
                webPageRepository.findById(updatedWebPage.getId()).orElseThrow(EntityNotFoundException::new);
        if(webPage.getTitle() != null && !webPage.getTitle().isBlank() && !webPage.getTitle().isEmpty()) {
            webPage.setTitle(updatedWebPage.getTitle());
        }
        webPage.setCallForIdeasEnabled(updatedWebPage.isCallForIdeasEnabled());
        webPage.setActivitiesEnabled(updatedWebPage.isActivitiesEnabled());
        webPage.setNewsItemsEnabled(updatedWebPage.isNewsItemsEnabled());
        webPage.setActionPointsEnabled(updatedWebPage.isActionPointsEnabled());
        webPage.setElectionInformationEnabled(updatedWebPage.isElectionInformationEnabled());
        return webPageRepository.save(webPage);
    }

    @Override
    public WebPage createHomePageOfYouthCouncil(YouthCouncil youthCouncil) {
        if(webPageRepository.findYouthCouncilHomePage(youthCouncil.getId()).isPresent()) {
            return webPageRepository.findYouthCouncilHomePage(youthCouncil.getId()).get();
        }
        WebPage homepage = new WebPage();
        homepage.setYouthCouncil(youthCouncil);
        homepage.setTitle(youthCouncil.getName());
        homepage.setSections(List.of());
        homepage.setHomepage(true);
        return webPageRepository.save(homepage);
    }

    @Override
    public List<WebPage> getAllWebPagesByYouthCouncilId(long tenantId) {
        LOGGER.info("WebPageServiceImpl is running getAllWebPagesByYouthCouncilId");
        return webPageRepository.findAllByYouthCouncilId(tenantId).orElse((List.of(createHomePageOfYouthCouncil(youthCouncilRepository.findById(tenantId).orElseThrow(EntityNotFoundException::new)))));
    }

    @Override
    public WebPage getWebPageById(long webpageId) {
        LOGGER.info("WebPageServiceImpl is running getWebPageById");
        return webPageRepository.findById(webpageId).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public WebPage addWebPage(long tenantId, WebPage webPage) {
        LOGGER.info("WebPageServiceImpl is running addWebPage");
        YouthCouncil youthCouncil = youthCouncilRepository.findById(tenantId).orElseThrow(EntityNotFoundException::new);
        webPage.setYouthCouncil(youthCouncil);
        return webPageRepository.save(webPage);
    }

    @Override
    public void deleteWebPage(long tenantId, long webPageId) {
        LOGGER.info("WebPageServiceImpl is running deleteWebPage");
        WebPage webPage = webPageRepository.findById(webPageId).orElseThrow(EntityNotFoundException::new);
        if(webPage.isHomepage()) {
            throw new IllegalArgumentException("Cannot delete homepage");
        }
        webPageRepository.deleteById(webPageId);
    }

    @Override
    public List<WebPage> getInformativePagesByYouthCouncilId(long tenantId) {
        LOGGER.info("WebPageServiceImpl is running getInformativePagesByYouthCouncilId");
        return webPageRepository.findALlInformativePagesByYouthCouncilId(tenantId).orElseThrow(EntityNotFoundException::new);
    }

}
