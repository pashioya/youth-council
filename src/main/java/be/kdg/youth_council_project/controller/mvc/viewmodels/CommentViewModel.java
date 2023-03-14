package be.kdg.youth_council_project.controller.mvc.viewmodels;

import java.time.LocalDateTime;

public class CommentViewModel {
    private Long id;
    private String content;
    private UserViewModel user;
    private LocalDateTime createdDate;
    private long likes;
}
