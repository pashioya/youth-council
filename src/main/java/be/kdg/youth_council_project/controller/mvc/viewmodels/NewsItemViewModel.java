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
public class NewsItemViewModel {
    private Long id;
    private String title;
    private String content;
    private String image;
    private LocalDateTime dateAdded;

    private List<CommentViewModel> comments;

    private long numberOfLikes;
    private String author;
    private boolean isLikedByUser;

}
