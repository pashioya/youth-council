package be.kdg.youth_council_project.controller.api;

import be.kdg.youth_council_project.controller.api.dtos.*;
import be.kdg.youth_council_project.domain.platform.youth_council_items.like.NewsItemLike;
import be.kdg.youth_council_project.domain.platform.youth_council_items.like.NewsItemLikeId;
import be.kdg.youth_council_project.security.CustomUserDetails;
import be.kdg.youth_council_project.service.NewsItemService;
import be.kdg.youth_council_project.tenants.TenantId;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/news-items")
@AllArgsConstructor
public class NewsItemController {
    private final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(NewsItemController.class);
    private final NewsItemService newsItemService;

    @PostMapping("{newsItemId}/likes")
    public ResponseEntity<HttpStatus> likeNewsItem(@TenantId long tenantId,
                                                @PathVariable("newsItemId") long newsItemId,
                                                @AuthenticationPrincipal CustomUserDetails user) {
        LOGGER.info("NewsItemsController is running likeNewsItem");
        NewsItemLike createdNewsItemLike = new NewsItemLike(new NewsItemLikeId(), LocalDateTime.now());
        newsItemService.setNewsItemOfNewsItemLike(createdNewsItemLike, newsItemId, tenantId);
        newsItemService.setUserOfNewsItemLike(createdNewsItemLike, user.getUserId(), tenantId);
        if (newsItemService.createNewsItemLike(createdNewsItemLike)) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @DeleteMapping("{newsItemId}/likes")
    public ResponseEntity<HttpStatus> unlikeNewsItem(@TenantId long tenantId,
                                                  @PathVariable("newsItemId") long newsItemId,
                                                  @AuthenticationPrincipal CustomUserDetails user) {
        LOGGER.info("NewsItemsController is running unlikeNewsItem");
        newsItemService.removeNewsItemLike(newsItemId, user.getUserId(), tenantId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
