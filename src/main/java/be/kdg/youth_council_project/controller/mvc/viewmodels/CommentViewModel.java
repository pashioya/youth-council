package be.kdg.youth_council_project.controller.mvc.viewmodels;

import be.kdg.youth_council_project.domain.platform.youth_council_items.comments.IdeaComment;

import java.time.LocalDateTime;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommentViewModel {
    private Long id;
    private String content;
    private String author;
    private LocalDateTime createdDate;

    public CommentViewModel(IdeaComment ideaComment) {
        this.id = ideaComment.getId();
        this.content = ideaComment.getContent();
        this.author = ideaComment.getAuthor().getUsername();
        this.createdDate = ideaComment.getCreatedDate();
    }
}
