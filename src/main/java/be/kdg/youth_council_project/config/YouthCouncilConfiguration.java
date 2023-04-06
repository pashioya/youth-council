package be.kdg.youth_council_project.config;

import be.kdg.youth_council_project.controller.mvc.viewmodels.ActionPointViewModel;
import be.kdg.youth_council_project.controller.mvc.viewmodels.CommentViewModel;
import be.kdg.youth_council_project.controller.mvc.viewmodels.IdeaViewModel;
import be.kdg.youth_council_project.controller.mvc.viewmodels.ThemeViewModel;
import be.kdg.youth_council_project.domain.platform.youth_council_items.ActionPoint;
import be.kdg.youth_council_project.domain.platform.youth_council_items.Idea;
import be.kdg.youth_council_project.domain.platform.youth_council_items.Theme;
import be.kdg.youth_council_project.domain.platform.youth_council_items.comments.ActionPointComment;
import be.kdg.youth_council_project.domain.platform.youth_council_items.comments.IdeaComment;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class YouthCouncilConfiguration {

    @Bean
    public ModelMapper modelMapper() {
        var modelMapper = new ModelMapper();

        Converter<Idea, IdeaViewModel> ideaConverter = new AbstractConverter<>() {
            @Override
            protected IdeaViewModel convert(Idea source) {
                if (source == null)
                    return null;
                IdeaViewModel destination = new IdeaViewModel();
                destination.setId(source.getId());
                destination.setDescription(source.getDescription());
                destination.setImages(source.getImages());
                List<IdeaComment> ideaComments = source.getComments();
                List<CommentViewModel> commentViewModels = ideaComments.stream().map(c -> new CommentViewModel(c.getId(), c.getContent(), c.getAuthor().getUsername(), c.getCreatedDate())).toList();
                destination.setComments(commentViewModels);
                destination.setNumberOfLikes(source.getLikes().size());
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
                destination.setImages(source.getImages());
                destination.setStatus(source.getStatus().toString());
                destination.setStandardAction(source.getLinkedStandardAction().getName());
                destination.setTheme(source.getLinkedStandardAction().getTheme().getName());
                destination.setVideo(source.getVideo());
                destination.setNumberOfLikes(source.getLikes().size());
                List<CommentViewModel> commentViewModels = source.getComments().stream().map(c -> new CommentViewModel(c.getId(), c.getContent(), c.getAuthor().getUsername(), c.getCreatedDate())).toList();
                destination.setComments(commentViewModels);
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

        modelMapper.addConverter(ideaConverter);
        modelMapper.addConverter(actionPointConverter);
        modelMapper.addConverter(themeConverter);
        return modelMapper;
    }
}
