package be.kdg.youth_council_project.controller.mvc.viewmodels;

import be.kdg.youth_council_project.domain.platform.Section;

public class SectionViewModel {
    private Long id;
    private String header;
    private String body;
    private String image;

    public SectionViewModel(Section section) {
        this.id = section.getId();
        this.header = section.getHeader();
        this.body = section.getBody();
        this.image = section.getImage();
    }
}
