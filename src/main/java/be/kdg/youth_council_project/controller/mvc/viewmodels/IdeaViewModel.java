package be.kdg.youth_council_project.controller.mvc.viewmodels;

import be.kdg.youth_council_project.domain.platform.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class IdeaViewModel {
    private Long id;
    private String description;
    private String author;
    private String theme;
    private List<String> images;
    private List<CommentViewModel> comments;
    private LocalDateTime dateAdded;
    private long numberOfLikes;
    private boolean isLikedByUser;
    private List<User> user;

    public IdeaViewModel(Long id, String description, List<User> user) {
        this.id = id;
        this.description = description;
        this.user = user;
    }
}
