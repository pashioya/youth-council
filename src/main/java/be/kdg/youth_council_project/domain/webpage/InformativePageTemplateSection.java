package be.kdg.youth_council_project.domain.webpage;

import lombok.*;

import javax.persistence.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class InformativePageTemplateSection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String header;
    private String body;
    @Lob
    private byte[] image;
    @ManyToOne
    @JoinColumn(name="template_id")
    private InformativePageTemplate template;
    public InformativePageTemplateSection(String header, String body) {
        this.header = header;
        this.body = body;
    }
}
