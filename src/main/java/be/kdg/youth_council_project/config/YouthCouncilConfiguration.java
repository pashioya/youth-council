package be.kdg.youth_council_project.config;

import be.kdg.youth_council_project.controller.api.dtos.YouthCouncilDto;
import be.kdg.youth_council_project.controller.mvc.viewmodels.*;
import be.kdg.youth_council_project.domain.platform.User;
import be.kdg.youth_council_project.domain.platform.YouthCouncil;
import be.kdg.youth_council_project.domain.platform.youth_council_items.*;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class YouthCouncilConfiguration {

    @Bean
    public ModelMapper modelMapper() {
        var modelMapper = new ModelMapper();


        Converter<YouthCouncil, YouthCouncilDto> youthCouncilDtoConverter = new AbstractConverter<>() {
            @Override
            protected YouthCouncilDto convert(YouthCouncil source) {
                if (source == null)
                    return null;
                YouthCouncilDto destination = new YouthCouncilDto();
                destination.setId(source.getId());
                destination.setName(source.getName());
                destination.setMunicipalityName(source.getMunicipalityName());
                return destination;
            }
        };

        Converter<User, UserViewModel> userViewModelConverter = new AbstractConverter<>() {
            @Override
            protected UserViewModel convert(User source) {
                if (source == null)
                    return null;
                UserViewModel destination = new UserViewModel();
                destination.setId(source.getId());
                destination.setUsername(source.getUsername());
                destination.setFirstName(source.getFirstName());
                destination.setLastName(source.getLastName());
                destination.setEmail(source.getEmail());
                destination.setPostalCode(source.getPostCode());
                return destination;
            }
        };

        Converter<Election, ElectionViewModel> electionConverter = new AbstractConverter<>() {
            @Override
            protected ElectionViewModel convert(Election source) {
                if (source == null)
                    return null;
                ElectionViewModel destination = new ElectionViewModel();
                destination.setId(source.getId());
                destination.setTitle(source.getTitle());
                destination.setDescription(source.getDescription());
                destination.setStartDate(source.getStartDate());
                destination.setEndDate(source.getEndDate());
                destination.setActive(source.isActive());
                destination.setLocation(source.getLocation());
                return destination;
            }
        };

        Converter<Idea, IdeaViewModel> ideaConverter = new AbstractConverter<>() {
            @Override
            protected IdeaViewModel convert(Idea source) {
                if (source == null)
                    return null;
                IdeaViewModel destination = new IdeaViewModel();
                destination.setId(source.getId());
                destination.setDescription(source.getDescription());
                destination.setTheme(source.getTheme().getName());
                destination.setAuthor(source.getAuthor().getUsername());
                destination.setDateAdded(source.getCreatedDate());
                return destination;
            }
        };

        Converter<ActionPoint, ActionPointViewModel> actionPointConverter = new AbstractConverter<>() {
            @Override
            protected ActionPointViewModel convert(ActionPoint source) {
                if (source == null)
                    return null;
                ActionPointViewModel destination = new ActionPointViewModel();
                destination.setId(source.getId());
                destination.setTitle(source.getTitle());
                destination.setDescription(source.getDescription());
                destination.setDateAdded(source.getCreatedDate());
                destination.setImages(source.getImages().stream().map(image -> Base64.getEncoder().encodeToString(image.getImage())).collect(Collectors.toList()));
                destination.setStatus(source.getStatus().toString());
                if (source.getLinkedStandardAction().getTheme() != null) {
                    destination.setStandardAction(source.getLinkedStandardAction().getName());
                    destination.setTheme(source.getLinkedStandardAction().getTheme().getName());
                } else {
                    destination.setStandardAction("Deleted Standard Action");
                    destination.setTheme("Deleted Theme");
                }
                destination.setVideo(source.getVideo());
                return destination;
            }
        };

        Converter<Theme, ThemeViewModel> themeConverter = new AbstractConverter<>() {
            @Override
            protected ThemeViewModel convert(Theme source) {
                if (source == null)
                    return null;
                ThemeViewModel destination = new ThemeViewModel();
                destination.setId(source.getId());
                destination.setName(source.getName());
                return destination;
            }
        };
        Converter<NewsItem, NewsItemViewModel> newsItemConverter = new AbstractConverter<>() {
            @Override
            protected NewsItemViewModel convert(NewsItem source) {
                if (source == null)
                    return null;
                NewsItemViewModel destination = new NewsItemViewModel();
                destination.setId(source.getId());
                destination.setTitle(source.getTitle());
                destination.setContent(source.getContent());
                if (source.getImage() != null) {
                    destination.setImage(Base64.getEncoder().encodeToString(source.getImage()));
                }
                destination.setDateAdded(source.getCreatedDate());
                List<CommentViewModel> commentViewModels = source.getComments().stream().map(c -> new CommentViewModel(c.getId(), c.getContent(), c.getAuthor().getUsername(), c.getCreatedDate())).toList();
                destination.setComments(commentViewModels);
                destination.setNumberOfLikes(source.getLikes().size());
                if (source.getAuthor() == null) {
                    destination.setAuthor("Youth Council");
                } else {
                    destination.setAuthor(source.getAuthor().getUsername());
                }
                return destination;
            }
        };


        Converter<YouthCouncil, YouthCouncilViewModel> youthCouncilConverter = new AbstractConverter<>() {


            @Override
            protected YouthCouncilViewModel convert(YouthCouncil source) {
                if (source == null)
                    return null;
                YouthCouncilViewModel destination = new YouthCouncilViewModel();
                destination.setId(source.getId());
                destination.setName(source.getName());
                destination.setMunicipalityName(source.getMunicipalityName());
                destination.setLogo(Base64.getEncoder().encodeToString(source.getLogo()));
                return destination;
            }
        };
        modelMapper.addConverter(userViewModelConverter);
        modelMapper.addConverter(youthCouncilDtoConverter);
        modelMapper.addConverter(electionConverter);
        modelMapper.addConverter(newsItemConverter);
        modelMapper.addConverter(ideaConverter);
        modelMapper.addConverter(actionPointConverter);
        modelMapper.addConverter(themeConverter);
        modelMapper.addConverter(youthCouncilConverter);
        return modelMapper;
    }
}
