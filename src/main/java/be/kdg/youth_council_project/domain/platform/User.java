package be.kdg.youth_council_project.domain.platform;

import javax.persistence.*;

import com.ctc.wstx.util.ElementId;
import lombok.*;

import java.util.List;
import java.util.Objects;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "app_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long id;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String postCode;

    private boolean isGeneralAdmin;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
