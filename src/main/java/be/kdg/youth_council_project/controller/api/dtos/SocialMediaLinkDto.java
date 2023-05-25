package be.kdg.youth_council_project.controller.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SocialMediaLinkDto {
    private long id;
    private String socialMedia;
    private String link;

}
