package be.kdg.youth_council_project.controller.api.dtos;


import lombok.*;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewUserDto {

private String email;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String postCode;
    private boolean isGeneralAdmin;
}
