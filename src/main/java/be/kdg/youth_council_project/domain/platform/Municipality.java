package be.kdg.youth_council_project.domain.platform;

import javax.persistence.*;
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

    @Column(unique = true)
    private String name;

    @ElementCollection
    @CollectionTable(name="postcodes",
            joinColumns=@JoinColumn(name="municipality_id"))
    @ToString.Exclude
    private List<Integer> postCodes;


}
