package be.kdg.youth_council_project.controller.api;

import be.kdg.youth_council_project.controller.api.dtos.SocialMediaLinkDto;
import be.kdg.youth_council_project.controller.api.dtos.UpdatedLinksDto;
import be.kdg.youth_council_project.controller.api.dtos.WebPageDto;
import be.kdg.youth_council_project.domain.platform.youth_council_items.SocialMediaLink;
import be.kdg.youth_council_project.domain.webpage.WebPage;
import be.kdg.youth_council_project.security.CustomUserDetails;
import be.kdg.youth_council_project.service.webpage.WebPageService;
import be.kdg.youth_council_project.tenants.TenantId;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/webpages")
public class WebPageController {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private final WebPageService webPageService;
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<WebPageDto> getWebPage(@TenantId long tenantId, @AuthenticationPrincipal CustomUserDetails user) {
        LOGGER.info("WebPageController is running getWebPage");
        WebPageDto webPageDto = modelMapper.
                map(webPageService.getHomePageByYouthCouncilId(tenantId), WebPageDto.class);
        // check if user has admin role
        if (user != null && user.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_YOUTH_COUNCIL_ADMINISTRATOR"))) {
            webPageDto.setAdminDashboardEnabled(true);
        }
        return ResponseEntity.ok(webPageDto);
    }

    @PatchMapping("/{webPageId}")
    @PreAuthorize("hasRole('ROLE_YOUTH_COUNCIL_ADMINISTRATOR')")
    public ResponseEntity<WebPageDto> updateWebPage(@TenantId long tenantId,@PathVariable long webPageId,
                                                    @RequestBody WebPageDto webPageDto) {
        LOGGER.info("WebPageController is running updateWebPage");
        webPageDto.setId(webPageId);
        return ResponseEntity.ok(modelMapper.map(
                webPageService.updateWebPage(tenantId, modelMapper.map(
                        webPageDto,
                        WebPage.class)),
                WebPageDto.class));
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_YOUTH_COUNCIL_ADMINISTRATOR')")
    public ResponseEntity<WebPageDto> addWebPage(@TenantId long tenantId,
                                                     @RequestBody WebPageDto webPageDto) {
        LOGGER.info("WebPageController is running addWebPage");
        return ResponseEntity.ok(modelMapper.map(
                webPageService.addWebPage(tenantId, modelMapper.map(
                        webPageDto,
                        WebPage.class)),
                WebPageDto.class));
    }
    @GetMapping("/social-media")
    public ResponseEntity<List<SocialMediaLinkDto>> getSocialMediaLinks(
            @TenantId long tenantId
    ) {
        LOGGER.info("YouthCouncilsController is running getSocialLinks");
        try {
            List<SocialMediaLinkDto> socialLinkDtos = webPageService.getSocialMediaLinks(tenantId)
                    .stream()
                    .map(webPageService::mapSocialMediaLinkToDto).
                    toList();

            return new ResponseEntity<>(socialLinkDtos, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Failed to get social links: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PutMapping("/social-media")
    public ResponseEntity<List<SocialMediaLinkDto>> updateSocialMediaLinks(
            @TenantId long tenantId,
            @RequestBody UpdatedLinksDto updatedLinksDto
            ) {
        LOGGER.info("YouthCouncilsController is running updateSocialMediaLink");
        try {
            List<SocialMediaLink> socialMediaLinks = webPageService.updateSocialMediaLinks(tenantId, updatedLinksDto);
            List<SocialMediaLinkDto> updatedSocialMediaLinkDto = socialMediaLinks.stream()
                    .map(webPageService::mapSocialMediaLinkToDto)
                    .toList();
            return new ResponseEntity<>(updatedSocialMediaLinkDto, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Failed to update social link: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
