package be.kdg.youth_council_project.domain.platform;

import lombok.*;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;


@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class MembershipId implements Serializable {

    @ManyToOne
    @JoinColumn(name="youth_council_id", nullable=false)
    private YouthCouncil youthCouncil;
    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;
}
