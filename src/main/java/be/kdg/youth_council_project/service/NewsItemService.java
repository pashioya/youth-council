package be.kdg.youth_council_project.service;

import be.kdg.youth_council_project.controller.mvc.viewmodels.NewsItemViewModel;
import be.kdg.youth_council_project.domain.platform.youthCouncilItems.NewsItem;

import java.util.List;

public interface NewsItemService {
    List<NewsItem> getNewsItemsByYouthCouncilId(long id);

    NewsItemViewModel mapToNewsItemViewModel(NewsItem newsItem);
}
