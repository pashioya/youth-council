package be.kdg.youth_council_project.domain.platform;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Municipality {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private List<Integer> postCodes;
    @OneToOne
    private YouthCouncil youthCouncil;
}
