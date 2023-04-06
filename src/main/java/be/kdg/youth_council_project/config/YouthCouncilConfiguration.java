package be.kdg.youth_council_project.config;

import be.kdg.youth_council_project.controller.mvc.viewmodels.CommentViewModel;
import be.kdg.youth_council_project.controller.mvc.viewmodels.IdeaViewModel;
import be.kdg.youth_council_project.domain.platform.youth_council_items.Idea;
import be.kdg.youth_council_project.domain.platform.youth_council_items.comments.IdeaComment;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Configuration
public class YouthCouncilConfiguration {

    @Bean
    public ModelMapper modelMapper() {
        var modelMapper = new ModelMapper();


//         @Override
//    public IdeaViewModel mapToIdeaViewModel(Idea idea) {
//        LOGGER.info("IdeaServiceImpl is running mapToIdeaViewModel");
//
//    }
        Converter<Idea, IdeaViewModel> ideaConverter = new AbstractConverter<>() {
            @Override
            protected IdeaViewModel convert(Idea source) {
                if (source == null)
                    return null;
                IdeaViewModel destination = new IdeaViewModel();


                destination.setId(source.getId());
                destination.setDescription(source.getDescription());
                destination.setImages(source.getImages());
                // Later on, action point should also be linked to an idea
                List<IdeaComment> ideaComments = source.getComments();
                List<CommentViewModel> commentViewModels = ideaComments.stream().map(CommentViewModel::new).toList();
                destination.setComments(commentViewModels);
                destination.setLikes(source.getLikes().size());
                destination.setTheme(source.getTheme().getName());
                destination.setAuthor(source.getAuthor().getFirstName() + " " + source.getAuthor().getLastName());
                destination.setDateAdded(source.getCreatedDate());

                return destination;
            }
        };
        modelMapper.addConverter(ideaConverter);
        return modelMapper;
    }
}
