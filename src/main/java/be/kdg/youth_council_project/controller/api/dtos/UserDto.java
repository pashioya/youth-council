package be.kdg.youth_council_project.controller.api.dtos;


import javax.persistence.Entity;
import lombok.*;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long id;
    private String firstName;


}
