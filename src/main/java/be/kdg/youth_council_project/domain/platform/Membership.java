package be.kdg.youth_council_project.domain.platform;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Membership {

    @EmbeddedId
    @ToString.Exclude
    private MembershipId membershipId;
    @Enumerated(EnumType.STRING)
    private Role role;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime dateJoined;

    @ManyToOne
    @JoinColumn(name="user_id", insertable = false, updatable = false)
    private User user;

    public Membership(MembershipId membershipId, Role role, LocalDateTime dateJoined) {
        this.membershipId = membershipId;
        this.role = role;
        this.dateJoined = dateJoined;
    }
}
