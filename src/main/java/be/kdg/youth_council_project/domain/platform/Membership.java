package be.kdg.youth_council_project.domain.platform;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Membership {
    private long id;
    private Role role;
    private LocalDateTime dateJoined;
    private YouthCouncil youthCouncil;
    private User user;

}
