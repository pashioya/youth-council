package be.kdg.youth_council_project.controller.mvc.viewmodels;


import lombok.*;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewUserViewModel {

    private String email;
    private String username;
    private String password;
    private String confirmPassword;
    private String firstName;
    private String lastName;
    private String postCode;
    private boolean isGeneralAdmin;
}
