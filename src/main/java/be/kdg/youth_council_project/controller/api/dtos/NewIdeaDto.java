package be.kdg.youth_council_project.controller.api.dtos;

import be.kdg.youth_council_project.domain.platform.youthCouncilItems.ActionPoint;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewIdeaDto {

    private long authorId;
    private String description;

    private List<String> images;

    private long themeId;


}
