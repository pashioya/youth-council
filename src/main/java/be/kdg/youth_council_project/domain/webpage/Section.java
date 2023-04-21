package be.kdg.youth_council_project.domain.webpage;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Section {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String heading;

    @Size(max = 500)
    private String body;
    @Lob
    private byte[] image;
    @ManyToOne
    @JoinColumn(name="webpage_id")
    private WebPage page;
    public Section(String heading, String body) {
        this.heading = heading;
        this.body = body;
    }
}
