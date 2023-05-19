package be.kdg.youth_council_project.controller.mvc.viewmodels;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ActionPointViewModel {
    private Long id;
    private String status;
    private String title;
    private String description;
    private String standardAction;
    private String theme;
    private List<String> images;
    private String video;
    private LocalDateTime dateAdded;
    private long numberOfLikes;
    private boolean isLikedByUser;
    private List<LinkedIdeaViewModel> linkedIdeas;
    private List<CommentViewModel> comments;

    public boolean hasLinkedIdea(long ideaId) {
        if (linkedIdeas == null) return false;
        return linkedIdeas.stream()
                .anyMatch(idea -> idea.getIdeaId() == ideaId);
    }
}
