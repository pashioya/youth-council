package be.kdg.youth_council_project.controller.api;

import be.kdg.youth_council_project.controller.api.dtos.WebPageDto;
import be.kdg.youth_council_project.security.CustomUserDetails;
import be.kdg.youth_council_project.service.webpage.WebPageService;
import be.kdg.youth_council_project.tenants.TenantId;
import be.kdg.youth_council_project.domain.webpage.WebPage;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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
    @DeleteMapping("/{webPageId}")
    @PreAuthorize("hasRole('ROLE_YOUTH_COUNCIL_ADMINISTRATOR')")
    public ResponseEntity deleteWebPage(@TenantId long tenantId, @PathVariable long webPageId) {
        LOGGER.info("WebPageController is running deleteWebPage");
        webPageService.deleteWebPage(tenantId,webPageId);
        return ResponseEntity.ok().build();
    }


}
