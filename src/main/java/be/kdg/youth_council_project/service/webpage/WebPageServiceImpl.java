package be.kdg.youth_council_project.service.webpage;

import be.kdg.youth_council_project.controller.api.dtos.SocialMediaLinkDto;
import be.kdg.youth_council_project.controller.api.dtos.UpdatedLinksDto;
import be.kdg.youth_council_project.domain.platform.YouthCouncil;
import be.kdg.youth_council_project.domain.platform.youth_council_items.SocialMedia;
import be.kdg.youth_council_project.domain.platform.youth_council_items.SocialMediaLink;
import be.kdg.youth_council_project.domain.webpage.WebPage;
import be.kdg.youth_council_project.repository.SocialMediaLinkRepository;
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
    private final SocialMediaLinkRepository socialMediaLinkRepository;



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
    @Override
    public List<SocialMediaLink> getSocialMediaLinks(long tenantId) {
        LOGGER.info("WebPageServiceImpl is running getSocialMediaLinks");
        return socialMediaLinkRepository.findAllByYouthCouncilId(tenantId);
    }

    @Override
    public SocialMediaLinkDto mapSocialMediaLinkToDto(SocialMediaLink socialMediaLink) {
        LOGGER.info("WebPageServiceImpl is running mapSocialMediaLinkToDto");
        SocialMediaLinkDto socialMediaLinkDto = new SocialMediaLinkDto();
        socialMediaLinkDto.setId(socialMediaLink.getId());
        socialMediaLinkDto.setSocialMedia(socialMediaLink.getSocialMedia().name());
        socialMediaLinkDto.setLink(socialMediaLink.getLink());
        return socialMediaLinkDto;
    }

    @Override
    public List<SocialMediaLink> updateSocialMediaLinks(long tenantId, UpdatedLinksDto updatedLinksDto) {
        LOGGER.info("WebPageServiceImpl is running updateSocialMediaLinks");
        List<SocialMediaLink> socialMediaLinks = socialMediaLinkRepository.findAllByYouthCouncilId(tenantId);
        // Find the facebook link
        SocialMediaLink facebookLink =
                socialMediaLinks
                        .stream()
                        .filter(socialMediaLink -> socialMediaLink.getSocialMedia().equals(SocialMedia.FACEBOOK))
                        .findFirst().
                        orElse(new SocialMediaLink(SocialMedia.FACEBOOK));
        // Find the instagram link
        SocialMediaLink instagramLink =
                socialMediaLinks
                        .stream()
                        .filter(socialMediaLink -> socialMediaLink.getSocialMedia().equals(SocialMedia.INSTAGRAM))
                        .findFirst()
                        .orElse(new SocialMediaLink(SocialMedia.INSTAGRAM));
        // Find the twitter link
        SocialMediaLink twitterLink =
                socialMediaLinks
                        .stream()
                        .filter(socialMediaLink -> socialMediaLink.getSocialMedia().equals(SocialMedia.TWITTER))
                        .findFirst()
                        .orElse(new SocialMediaLink(SocialMedia.TWITTER));

        // Find the tiktok link
        SocialMediaLink tiktokLink =
                socialMediaLinks
                        .stream()
                        .filter(socialMediaLink -> socialMediaLink.getSocialMedia().equals(SocialMedia.TIKTOK))
                        .findFirst()
                        .orElse(new SocialMediaLink(SocialMedia.TIKTOK));

        // Update the links
        facebookLink.setLink(updatedLinksDto.getFacebookLink());
        instagramLink.setLink(updatedLinksDto.getInstagramLink());
        twitterLink.setLink(updatedLinksDto.getTwitterLink());
        tiktokLink.setLink(updatedLinksDto.getTiktokLink());


        // Save the links
        List<SocialMediaLink> links = List.of(facebookLink, instagramLink, twitterLink, tiktokLink);
        links.forEach(link -> link.setYouthCouncil(youthCouncilRepository.findById(tenantId).orElseThrow(EntityNotFoundException::new)));

        List<SocialMediaLink> updatedLinks = socialMediaLinkRepository.saveAll(links);

        return updatedLinks;
    }
}
