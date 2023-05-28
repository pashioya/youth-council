package be.kdg.youth_council_project.controller.api;

import be.kdg.youth_council_project.controller.api.dtos.youth_council_items.NewNewsItemDto;
import be.kdg.youth_council_project.controller.api.dtos.youth_council_items.NewsItemDto;
import be.kdg.youth_council_project.domain.platform.youth_council_items.NewsItem;
import be.kdg.youth_council_project.domain.platform.youth_council_items.like.NewsItemLike;
import be.kdg.youth_council_project.domain.platform.youth_council_items.like.NewsItemLikeId;
import be.kdg.youth_council_project.security.CustomUserDetails;
import be.kdg.youth_council_project.service.youth_council_items.NewsItemService;
import be.kdg.youth_council_project.tenants.TenantId;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/news-items")
@AllArgsConstructor
public class NewsItemController {
    private final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(NewsItemController.class);
    private final NewsItemService newsItemService;

    @GetMapping
    public ResponseEntity<List<NewsItemDto>> getAllNewsItems(@TenantId long tenantId) {
        LOGGER.info("NewsItemsController is running getAllNewsItems");
        List<NewsItem> newsItems = newsItemService.getNewsItemsByYouthCouncilId(tenantId);
        if (newsItems.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        List<NewsItemDto> newsItemDtos = newsItems.parallelStream().map(newsItemService::mapToDto).toList();
        return new ResponseEntity<>(newsItemDtos, HttpStatus.OK);
    }

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<HttpStatus> createNewsItem(@TenantId long tenantId,
                                                     @RequestPart("newsItem") @Valid NewNewsItemDto newsItemCreateDto,
                                                     @RequestPart("image") MultipartFile image,
                                                     @AuthenticationPrincipal CustomUserDetails user) {
        LOGGER.info("NewsItemsController is running createNewsItem");

        NewsItem createdNewsItem = new NewsItem(newsItemCreateDto.getTitle(), newsItemCreateDto.getContent());
        newsItemService.setYouthCouncilOfNewsItem(createdNewsItem, tenantId);

        try {
            newsItemService.setImageOfNewsItem(createdNewsItem, image.getBytes());
        } catch (IOException e) {
            LOGGER.error("NewsItemsController is running createNewsItem and has thrown an exception: " + e);
            e.printStackTrace();
        }
        newsItemService.setAuthorOfIdea(createdNewsItem, user.getUserId(), tenantId);
        if (newsItemService.createNewsItem(createdNewsItem) != null) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @PostMapping("{newsItemId}/likes")
    public ResponseEntity<NewsItemLike> likeNewsItem(@TenantId long tenantId,
                                                     @PathVariable("newsItemId") long newsItemId,
                                                     @AuthenticationPrincipal CustomUserDetails user) {
        LOGGER.info("NewsItemsController is running likeNewsItem");
        NewsItemLike newsItemLike = new NewsItemLike(new NewsItemLikeId(), LocalDateTime.now());
        newsItemService.setNewsItemOfNewsItemLike(newsItemLike, newsItemId, tenantId);
        newsItemService.setUserOfNewsItemLike(newsItemLike, user.getUserId(), tenantId);
        NewsItemLike createdNewsItemLike = newsItemService.createNewsItemLike(newsItemLike);
        if (createdNewsItemLike != null) {
            return new ResponseEntity<>(createdNewsItemLike, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("{newsItemId}/likes")
    public ResponseEntity<HttpStatus> unlikeNewsItem(@TenantId long tenantId,
                                                     @PathVariable("newsItemId") long newsItemId,
                                                     @AuthenticationPrincipal CustomUserDetails user) {
        LOGGER.info("NewsItemsController is running unlikeNewsItem");
        newsItemService.removeNewsItemLike(newsItemId, user.getUserId(), tenantId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{newsItemId}")
    @PreAuthorize("hasRole('ROLE_YOUTH_COUNCIL_ADMINISTRATOR') or hasRole('ROLE_YOUTH_COUNCIL_MODERATOR')")
    public ResponseEntity<HttpStatus> deleteNewsItem(
            @PathVariable("newsItemId") long id) {
        LOGGER.info("NewsItemController is running deleteNewsItem");
        try {
            newsItemService.deleteNewsItem(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            LOGGER.error("NewsItemController is running deleteNewsItem and has thrown an exception: " + e);
            return ResponseEntity.badRequest().build();
        }
    }
}
