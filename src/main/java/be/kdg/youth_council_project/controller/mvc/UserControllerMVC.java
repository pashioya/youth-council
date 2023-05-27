package be.kdg.youth_council_project.controller.mvc;

import be.kdg.youth_council_project.controller.mvc.viewmodels.IdeaViewModel;
import be.kdg.youth_council_project.controller.mvc.viewmodels.MunicipalityViewModel;
import be.kdg.youth_council_project.controller.mvc.viewmodels.UserViewModel;
import be.kdg.youth_council_project.domain.platform.Membership;
import be.kdg.youth_council_project.domain.platform.Municipality;
import be.kdg.youth_council_project.domain.platform.Role;
import be.kdg.youth_council_project.domain.platform.User;
import be.kdg.youth_council_project.domain.platform.youth_council_items.Idea;
import be.kdg.youth_council_project.security.CustomUserDetails;
import be.kdg.youth_council_project.service.UserService;
import be.kdg.youth_council_project.service.youth_council_items.IdeaService;
import be.kdg.youth_council_project.service.youth_council_items.MunicipalityService;
import be.kdg.youth_council_project.tenants.TenantId;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/user")
public class UserControllerMVC {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final IdeaService ideaService;
    private final MunicipalityService municipalityService;

    @GetMapping("/user-ideas")
    public ModelAndView getUserIdeas(@TenantId long tenantId,
                                     @AuthenticationPrincipal CustomUserDetails user) {
        LOGGER.info("YouthCouncilControllerMVC is running getUserIdeas with tenantId {}", tenantId);
        ModelAndView modelAndView = new ModelAndView("user/user-ideas");
        List<Idea> ideas = ideaService.getIdeasByUserId(user.getUserId());
        List<IdeaViewModel> ideaViewModels = ideas.stream().map(idea -> {
                    idea.setImages(ideaService.getImagesOfIdea(idea.getId()));
                    idea.setComments(ideaService.getCommentsOfIdea(idea));
                    idea.setLikes(ideaService.getLikesOfIdea(idea.getId()));
                    IdeaViewModel ideaViewModel = modelMapper.map(idea, IdeaViewModel.class);
                    ideaViewModel.setLikedByUser(ideaService.isLikedByUser(idea.getId(), user.getUserId()));
                    return ideaViewModel;
                }
        ).toList();
        modelAndView.addObject("ideas", ideaViewModels);
        return modelAndView;
    }

    @GetMapping("/profile")
    public ModelAndView getSettings(@TenantId long tenantId,
                                    @AuthenticationPrincipal CustomUserDetails user) {
        LOGGER.info("YouthCouncilControllerMVC is running getSettings with tenantId {}", tenantId);
        User user1 = userService.getUserById(user.getUserId());
        ModelAndView modelAndView = new ModelAndView("user/user-profile");
        UserViewModel userViewModel = modelMapper.map(user1, UserViewModel.class);
        userViewModel.setStatus(userService.findMemberShipByUserIdAndYouthCouncilId(user.getUserId(), tenantId).getRole().toString());
        Municipality municipality = municipalityService.getMunicipalityByYouthCouncilId(tenantId);
        MunicipalityViewModel municipalityViewModel = modelMapper.map(municipality, MunicipalityViewModel.class);
        modelAndView.addObject("user", modelMapper.map(user1, UserViewModel.class));
        modelAndView.addObject("municipality", municipalityViewModel);
        return modelAndView;
    }

    @GetMapping("/{userName}")
    public ModelAndView getProfile(@TenantId long tenantId,
                                   @PathVariable String userName) {
        LOGGER.info("YouthCouncilControllerMVC is running getProfile with tenantId {}", tenantId);
        User user1 = userService.getUserByUsername(userName);
        Membership usersMembership = userService.findMemberShipByUserIdAndYouthCouncilId(user1.getId(), tenantId);

        if (usersMembership.getRole().equals(Role.DELETED)) {
            //            TODO: redirect to 404 Page
            return new ModelAndView("redirect:/");
        }
        ModelAndView modelAndView = new ModelAndView("user/profile");
        UserViewModel userViewModel = modelMapper.map(user1, UserViewModel.class);
        userViewModel.setStatus(usersMembership.getRole().toString());
        Municipality municipality = municipalityService.getMunicipalityByYouthCouncilId(tenantId);
        MunicipalityViewModel municipalityViewModel = modelMapper.map(municipality, MunicipalityViewModel.class);
        modelAndView.addObject("user", userViewModel);
        modelAndView.addObject("municipality", municipalityViewModel);
        return modelAndView;
    }
}
