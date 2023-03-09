package be.kdg.youth_council_project.domain.platform.youthCouncilItems;

import be.kdg.youth_council_project.domain.platform.YouthCouncil;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Activity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String name;
    private String description;

    @ManyToOne
    @JoinColumn(name="youth_council_id")
    private YouthCouncil youthCouncil;

}
