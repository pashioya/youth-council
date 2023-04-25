package be.kdg.youth_council_project.controller.api;

import be.kdg.youth_council_project.controller.api.dtos.StatsIdeaDto;
import be.kdg.youth_council_project.controller.api.dtos.StatsUserDto;
import be.kdg.youth_council_project.service.UserService;
import be.kdg.youth_council_project.service.youth_council_items.IdeaService;
import be.kdg.youth_council_project.tenants.NoTenantController;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/ga")
@NoTenantController
public class GeneralAdminController {

    private final UserService userService;
    private final IdeaService ideaService;
    private final ModelMapper modelMapper;


    @GetMapping("/users")
    public List<StatsUserDto> getAllUsers() {
        return userService.getAllUsers()
                .stream()
                .map(user -> modelMapper
                        .map(user, StatsUserDto.class))
                .toList();
    }


    @GetMapping("/ideas")
    public List<StatsIdeaDto> getAllIdeas() {
        return ideaService.getAllIdeas()
                .stream()
                .map(
                        idea -> new StatsIdeaDto(
                                idea.getId(),
                                idea.getDescription(),
                                idea.getCreatedDate()
                        )
                )
                .toList();
    }
}
