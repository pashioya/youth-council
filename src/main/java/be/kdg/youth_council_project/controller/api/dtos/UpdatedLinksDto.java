package be.kdg.youth_council_project.controller.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdatedLinksDto {
    private String facebookLink;
    private String instagramLink;
    private String twitterLink;
    private String tiktokLink;
}
