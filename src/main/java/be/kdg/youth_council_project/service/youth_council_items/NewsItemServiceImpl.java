package be.kdg.youth_council_project.service.youth_council_items;

import be.kdg.youth_council_project.controller.api.dtos.youth_council_items.NewsItemDto;
import be.kdg.youth_council_project.domain.platform.User;
import be.kdg.youth_council_project.domain.platform.YouthCouncil;
import be.kdg.youth_council_project.domain.platform.youth_council_items.NewsItem;
import be.kdg.youth_council_project.domain.platform.youth_council_items.comments.NewsItemComment;
import be.kdg.youth_council_project.domain.platform.youth_council_items.like.NewsItemLike;
import be.kdg.youth_council_project.repository.UserRepository;
import be.kdg.youth_council_project.repository.YouthCouncilRepository;
import be.kdg.youth_council_project.repository.news_item.NewsItemCommentRepository;
import be.kdg.youth_council_project.repository.news_item.NewsItemLikeRepository;
import be.kdg.youth_council_project.repository.news_item.NewsItemRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@AllArgsConstructor
public class NewsItemServiceImpl implements NewsItemService {
    private final NewsItemRepository newsItemRepository;
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private final NewsItemLikeRepository newsItemLikeRepository;
    private final NewsItemCommentRepository newsItemCommentRepository;
    private final YouthCouncilRepository youthCouncilRepository;
    private final UserRepository userRepository;

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
    public List<NewsItemLike> getLikesOfNewsItem(NewsItem newsItem) {
        LOGGER.info("NewsItemServiceImpl is running getLikesOfNewsItem");
        List<NewsItemLike> newsItemLikes = newsItemLikeRepository.findById_NewsItem(newsItem);
        LOGGER.debug("Returning newsItemLikes {}", newsItemLikes);
        return newsItemLikes;
    }

    @Override
    public List<NewsItemComment> getCommentsOfNewsItem(NewsItem newsItem) {
        LOGGER.info("NewsItemServiceImpl is running getCommentsOfNewsItem");
        List<NewsItemComment> newsItemComments = newsItemCommentRepository.findByNewsItem(newsItem);
        LOGGER.debug("Returning newsItemComments {}", newsItemComments);
        return newsItemComments;
    }

    @Override
    public NewsItemLike createNewsItemLike(NewsItemLike newsItemLike) {
        LOGGER.info("NewsItemServiceImpl is running createNewsItemLike");
        if (!newsItemLikeRepository.existsByUserIdAndNewsItemId(
                newsItemLike.getId().getLikedBy().getId(),
                newsItemLike.getId().getNewsItem().getId())) { // stops same user liking post more than once
            return newsItemLikeRepository.save(newsItemLike);
        }
        return null;
    }

    @Override
    public NewsItem createNewsItem(NewsItem newsItem) {
        LOGGER.info("NewsItemServiceImpl is running createNewsItem");
        return newsItemRepository.save(newsItem);
    }

    @Override
    public void setYouthCouncilOfNewsItem(NewsItem newsItem, long tenantId) {
        LOGGER.info("NewsItemServiceImpl is running setYouthCouncilOfNewsItem");
        YouthCouncil youthCouncil = youthCouncilRepository.findById(tenantId).orElseThrow(EntityNotFoundException::new);
        LOGGER.debug("NewsItemServiceImpl found youthCouncil {}", youthCouncil);
        newsItem.setYouthCouncil(youthCouncil);
    }

    @Override
    public void setImageOfNewsItem(NewsItem createdNewsItem, byte[] image) {
        LOGGER.info("NewsItemServiceImpl is running setImageOfNewsItem");
        createdNewsItem.setImage(image);
    }

    @Override
    public void setAuthorOfIdea(NewsItem createdNewsItem, long userId, long tenantId) {
        LOGGER.info("NewsItemServiceImpl is running setAuthorOfIdea");
        User user = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
        LOGGER.debug("NewsItemServiceImpl found user {}", user);
        createdNewsItem.setAuthor(user);
    }

    @Override
    @Transactional
    public void removeNewsItemLike(long newsItemId, long userId, long youthCouncilId) {
        LOGGER.info("NewsItemServiceImpl is running removeNewsItemLike");
        if (!userRepository.existsById(userId)) {
            throw new EntityNotFoundException();
        }
        NewsItemLike newsItemLike =
                newsItemLikeRepository.findByNewsItemIdAndUserIdAndYouthCouncilId(newsItemId
                        , userId, youthCouncilId).orElseThrow(EntityNotFoundException::new);
        newsItemLikeRepository.delete(newsItemLike);
    }

    @Override
    public boolean isLikedByUser(Long id, long userId) {
        LOGGER.info("NewsItemServiceImpl is running isLikedByUser");
        return newsItemLikeRepository.existsByUserIdAndNewsItemId(userId, id);
    }

    @Override
    public void setUserOfNewsItemLike(NewsItemLike createdNewsItemLike, long userId, long tenantId) {
        LOGGER.info("IdeaServiceImpl is running setUserOfIdeaLike");
        User user = userRepository.findByIdAndYouthCouncilId(userId, tenantId).orElseThrow(EntityNotFoundException::new);
        LOGGER.debug("IdeaServiceImpl found user {}", user);
        createdNewsItemLike.getId().setLikedBy(user);
    }

    @Override
    public void setNewsItemOfNewsItemLike(NewsItemLike createdNewsItemLike, long newsItemId, long tenantId) {
        LOGGER.info("NewsItemServiceImpl is running setNewsItemOfNewsItemLike");
        NewsItem newsItem =
                newsItemRepository.findByIdAndYouthCouncilId(newsItemId, tenantId).orElseThrow(EntityNotFoundException::new);
        LOGGER.debug("NewsItemServiceImpl found newsItem {}", newsItem);
        createdNewsItemLike.getId().setNewsItem(newsItem);

    }

    @Override
    @Transactional
    public void deleteNewsItem(long newsItemId) {
        LOGGER.info("NewsItemServiceImpl is running removeNewsItem");
        newsItemRepository.deleteById(newsItemId);
    }

    @Override
    public List<NewsItemComment> getCommentsByUserId(long userId) {
        LOGGER.info("NewsItemServiceImpl is running getCommentsByUserId");
        List<NewsItemComment> newsItemComments = newsItemCommentRepository.findAllByUserId(userId);
        LOGGER.debug("Returning newsItemComments {}", newsItemComments);
        return newsItemComments;
    }

    @Override
    public List<NewsItemComment> getAllCommentsByYouthCouncilId(long tenantId) {
        LOGGER.info("NewsItemServiceImpl is running getAllCommentsByYouthCouncilId");
        List<NewsItemComment> newsItemComments = newsItemCommentRepository.findAllByYouthCouncilId(tenantId);
        LOGGER.debug("Returning newsItemComments {}", newsItemComments);
        return newsItemComments;
    }

    @Override
    public NewsItemDto mapToDto(NewsItem newsItem) {
        NewsItemDto newsItemDto = new NewsItemDto();
        newsItemDto.setId(newsItem.getId());
        newsItemDto.setTitle(newsItem.getTitle());
        newsItemDto.setContent(newsItem.getContent());
        newsItemDto.setAuthor(newsItem.getAuthor().getUsername());
        newsItemDto.setCreatedDate(newsItem.getCreatedDate());
        return newsItemDto;
    }

    @Override
    public List<NewsItemComment> getAllComments() {
        LOGGER.info("NewsItemServiceImpl is running getAllComments");
        List<NewsItemComment> newsItemComments = newsItemCommentRepository.findAll();
        LOGGER.debug("Returning newsItemComments {}", newsItemComments.size());
        return newsItemComments;
    }
}
