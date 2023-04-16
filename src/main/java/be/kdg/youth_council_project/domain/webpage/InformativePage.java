package be.kdg.youth_council_project.domain.webpage;

import be.kdg.youth_council_project.domain.platform.YouthCouncil;
import lombok.*;

import javax.persistence.*;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@DiscriminatorValue("INFO")
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"url", "youth_council_id"}))
public class InformativePage extends WebPage {
    @ManyToOne
    @JoinColumn(name = "youth_council_id")
    private YouthCouncil youthCouncil;

    private String url;

    @OneToMany(mappedBy="page")
    private List<Section> sections;
}
