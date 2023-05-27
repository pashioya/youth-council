package be.kdg.youth_council_project.controller.mvc.viewmodels;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserViewModel {
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String postalCode;
    private String status;
}
