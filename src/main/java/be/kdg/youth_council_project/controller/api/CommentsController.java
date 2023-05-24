package be.kdg.youth_council_project.controller.api;

import be.kdg.youth_council_project.controller.api.dtos.CommentDto;
import be.kdg.youth_council_project.service.youth_council_items.ActionPointService;
import be.kdg.youth_council_project.service.youth_council_items.IdeaService;
import be.kdg.youth_council_project.service.youth_council_items.NewsItemService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/comments")
public class CommentsController {

    private final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(this.getClass());
    private final ActionPointService actionPointService;
    private final IdeaService ideaService;
    private final NewsItemService newsItemService;
    private final ModelMapper modelMapper;


    @GetMapping("/{userId}")
    public ResponseEntity<List<CommentDto>> getCommentsByUserId(@PathVariable("userId") long userId) {
        LOGGER.info("CommentsController is running getCommentsByUserId");
        List<CommentDto> comments = new ArrayList<>();

        comments.addAll(actionPointService.getCommentsByUserId(userId)
                .stream()
                .map(comment -> modelMapper.map(comment, CommentDto.class))
                .toList());

        comments.addAll(ideaService.getCommentsByUserId(userId)
                .stream()
                .map(comment -> modelMapper.map(comment, CommentDto.class))
                .toList());

        comments.addAll(newsItemService.getCommentsByUserId(userId)
                .stream()
                .map(comment -> modelMapper.map(comment, CommentDto.class))
                .toList());

        if (comments.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok().body(comments);
        }
    }

}
