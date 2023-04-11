package be.kdg.youth_council_project.service;

import be.kdg.youth_council_project.domain.platform.youth_council_items.NewsItem;
import be.kdg.youth_council_project.domain.platform.youth_council_items.comments.NewsItemComment;
import be.kdg.youth_council_project.domain.platform.youth_council_items.like.NewsItemLike;
import be.kdg.youth_council_project.repository.NewsItemCommentRepository;
import be.kdg.youth_council_project.repository.NewsItemLikeRepository;
import be.kdg.youth_council_project.repository.NewsItemRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class NewsItemServiceImpl implements NewsItemService{
    private final NewsItemRepository newsItemRepository;
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private final NewsItemLikeRepository newsItemLikeRepository;
    private final NewsItemCommentRepository newsItemCommentRepository;
    @Override
    public List<NewsItem> getNewsItemsByYouthCouncilId(long id) {
        LOGGER.info("NewsItemServiceImpl is running getNewsItemsByYouthCouncilId");
        List<NewsItem> newsItems = newsItemRepository.findAllByYouthCouncilId(id);
        newsItems.forEach(newsItem -> {
            newsItem.setComments(getCommentsOfNewsItem(newsItem));
            newsItem.setLikes(getLikesOfNewsItem(newsItem));
        }
        );
        LOGGER.debug("NewsItemServiceImpl found newsItems: " + newsItems);
        return newsItems;
    }
    @Override
    public List<NewsItemLike> getLikesOfNewsItem(NewsItem newsItem){
        LOGGER.info("NewsItemServiceImpl is running getLikesOfNewsItem");
        List<NewsItemLike> newsItemLikes = newsItemLikeRepository.findByNewsItemLikeId_NewsItem(newsItem);
        LOGGER.debug("Returning newsItemLikes {}", newsItemLikes);
        return newsItemLikes;
    }
@Override
    public List<NewsItemComment> getCommentsOfNewsItem(NewsItem newsItem){
        LOGGER.info("NewsItemServiceImpl is running getCommentsOfNewsItem");
        List<NewsItemComment> newsItemComments = newsItemCommentRepository.findByNewsItem(newsItem);
        LOGGER.debug("Returning newsItemComments {}", newsItemComments);
        return newsItemComments;
    }
}
