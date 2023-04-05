package be.kdg.youth_council_project.service;

import be.kdg.youth_council_project.controller.mvc.viewmodels.NewsItemViewModel;
import be.kdg.youth_council_project.domain.platform.youth_council_items.NewsItem;
import be.kdg.youth_council_project.repository.NewsItemRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
@AllArgsConstructor
public class NewsItemServiceImpl implements NewsItemService{
    private final NewsItemRepository newsItemRepository;
    private final Logger LOGGER = Logger.getLogger(NewsItemServiceImpl.class.getName());
    @Override
    public List<NewsItem> getNewsItemsByYouthCouncilId(long id) {
        return newsItemRepository.findAllByYouthCouncilId(id);
    }

    @Override
    public NewsItemViewModel mapToNewsItemViewModel(NewsItem newsItem) {
        NewsItemViewModel newsItemViewModel = new NewsItemViewModel();
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.map(newsItem, newsItemViewModel);
        LOGGER.info("Mapping news item to news item view model");
        return newsItemViewModel;
    }
}
