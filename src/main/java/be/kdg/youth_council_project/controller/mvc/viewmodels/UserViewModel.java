package be.kdg.youth_council_project.controller.mvc.viewmodels;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserViewModel {
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String postalCode;
    private String password;

    public UserViewModel( String firstName, String lastName,String username, String email, String postalCode, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.postalCode = postalCode;
        this.password = password;
    }
}
