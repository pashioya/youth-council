package be.kdg.youth_council_project.controller.mvc.viewmodels;

import java.time.LocalDateTime;
import java.util.List;

public class ActionPointViewModel {
    private Long id;
    private String status;
    private String title;
    private String description;
    private List<String> images;
    private String video;
    private LocalDateTime dateAdded;
    private long likes;
    private List<IdeaViewModel> linkedIdeas;
}
