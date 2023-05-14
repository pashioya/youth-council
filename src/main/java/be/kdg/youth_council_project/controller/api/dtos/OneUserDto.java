package be.kdg.youth_council_project.controller.api.dtos;

import lombok.*;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OneUserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String postalCode;
    private String password;
}
