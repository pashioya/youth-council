package be.kdg.youth_council_project.service;

import be.kdg.youth_council_project.domain.platform.YouthCouncil;
import be.kdg.youth_council_project.domain.webpage.WebPage;

public interface WebPageService {
    WebPage getHomePageByYouthCouncilId(long youthCouncilId);

    WebPage updateWebPage(long youthCouncilId, WebPage map);

    WebPage createHomePageOfYouthCouncil(YouthCouncil youthCouncil);
}
