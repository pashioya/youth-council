package be.kdg.youth_council_project.service.webpage;

import be.kdg.youth_council_project.domain.platform.YouthCouncil;
import be.kdg.youth_council_project.domain.webpage.WebPage;

import java.util.List;
import java.util.Optional;

public interface WebPageService {
    WebPage getHomePageByYouthCouncilId(long youthCouncilId);

    WebPage updateWebPage(long youthCouncilId, WebPage map);

    WebPage createHomePageOfYouthCouncil(YouthCouncil youthCouncil);

    List<WebPage> getAllWebPagesByYouthCouncilId(long tenantId);
}
