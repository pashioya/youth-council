package be.kdg.youth_council_project.service;

import be.kdg.youth_council_project.domain.platform.YouthCouncil;
import be.kdg.youth_council_project.domain.webpage.HomePage;

import be.kdg.youth_council_project.domain.webpage.WebPage;

import be.kdg.youth_council_project.repository.webpage.HomePageRepository;
import be.kdg.youth_council_project.repository.webpage.InformativePageRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@AllArgsConstructor
public class WebPageServiceImpl implements WebPageService{
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private final HomePageRepository homePageRepository;
    private final InformativePageRepository infoPageRepository;



    @Override
    public WebPage getHomePageByYouthCouncilId(long youthCouncilId) {
        LOGGER.info("WebPageServiceImpl is running getWebPageByYouthCouncilId");
        return homePageRepository.findByYouthCouncilId(youthCouncilId).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public WebPage updateWebPage(long youthCouncilId, WebPage updatedWebPage) {
        LOGGER.info("WebPageServiceImpl is running updateWebPage");
        HomePage homePage = homePageRepository.findByYouthCouncilId(youthCouncilId).orElseThrow(EntityNotFoundException::new);
        homePage.setCallForIdeasEnabled(updatedWebPage.isCallForIdeasEnabled());
        homePage.setActivitiesEnabled(updatedWebPage.isActivitiesEnabled());
        homePage.setNewsItemsEnabled(updatedWebPage.isNewsItemsEnabled());
        homePage.setActionPointsEnabled(updatedWebPage.isActionPointsEnabled());
        homePage.setElectionInformationEnabled(updatedWebPage.isElectionInformationEnabled());
        return homePageRepository.save(homePage);
    }

    @Override
    public HomePage createHomePageOfYouthCouncil(YouthCouncil youthCouncil) {
        HomePage homepage = new HomePage();
        homepage.setYouthCouncil(youthCouncil);
        homepage.setTitle(youthCouncil.getName());
        return homePageRepository.save(homepage);
    }
}
