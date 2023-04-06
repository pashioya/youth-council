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

    public CommentViewModel(long id, String content, String authorUsername, LocalDateTime createdDate) {
        this.id = id;
        this.content = content;
        this.author = authorUsername;
        this.createdDate = createdDate;
    }
}
