package be.kdg.youth_council_project.domain.platform;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Long id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String postCode;
    private List<Membership> memberships;


}
