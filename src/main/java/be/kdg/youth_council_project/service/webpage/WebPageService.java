package be.kdg.youth_council_project.service.webpage;

import be.kdg.youth_council_project.controller.api.dtos.SocialMediaLinkDto;
import be.kdg.youth_council_project.controller.api.dtos.UpdatedLinksDto;
import be.kdg.youth_council_project.domain.platform.YouthCouncil;
import be.kdg.youth_council_project.domain.platform.youth_council_items.SocialMediaLink;
import be.kdg.youth_council_project.domain.webpage.WebPage;

import java.util.List;

public interface WebPageService {
    WebPage getHomePageByYouthCouncilId(long youthCouncilId);

    WebPage updateWebPage(long youthCouncilId, WebPage webPage);

    WebPage createHomePageOfYouthCouncil(YouthCouncil youthCouncil);

    List<WebPage> getAllWebPagesByYouthCouncilId(long tenantId);

    WebPage getWebPageById(long webpageId);

    WebPage addWebPage(long tenantId, WebPage webPage);

    void deleteWebPage(long tenantId, long webPageId);

    List<WebPage> getInformativePagesByYouthCouncilId(long tenantId);
    SocialMediaLinkDto mapSocialMediaLinkToDto(SocialMediaLink socialMediaLink);

    List<SocialMediaLink> getSocialMediaLinks(long tenantId);

    List<SocialMediaLink> updateSocialMediaLinks(long tenantId, UpdatedLinksDto updatedLinksDto);
}
