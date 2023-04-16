package be.kdg.youth_council_project.domain.webpage;

import lombok.*;

import javax.persistence.*;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class InformativePageTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

    @OneToMany(mappedBy="template", cascade = {CascadeType.PERSIST})
    private List<InformativePageTemplateSection> sections;
}
