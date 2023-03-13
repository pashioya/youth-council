package be.kdg.youth_council_project.domain.platform;

import javax.persistence.*;
import lombok.*;

import java.util.List;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "app_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String postCode;
    private String role;
}
