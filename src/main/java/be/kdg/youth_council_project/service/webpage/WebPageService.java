package be.kdg.youth_council_project.service.webpage;

import be.kdg.youth_council_project.controller.api.dtos.SectionDto;
import be.kdg.youth_council_project.domain.platform.YouthCouncil;
import be.kdg.youth_council_project.domain.webpage.Section;
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
}
