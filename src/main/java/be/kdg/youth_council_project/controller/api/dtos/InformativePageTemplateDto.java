package be.kdg.youth_council_project.controller.api.dtos;


import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.List;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InformativePageTemplateDto {

    private long id;

    private String title;

    private List<SectionDto> sections;


}
