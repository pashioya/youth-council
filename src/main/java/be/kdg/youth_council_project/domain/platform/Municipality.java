package be.kdg.youth_council_project.domain.platform;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Municipality {
    private Long id;
    private String name;
    private List<Integer> postCodes;
    private YouthCouncil youthCouncil;
}
