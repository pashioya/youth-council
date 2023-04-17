package be.kdg.youth_council_project.domain.webpage;

import javax.persistence.*;

import lombok.*;

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
    private String body;
    @Lob
    private byte[] image;
    @ManyToOne
    @JoinColumn(name="webpage_id")
    private InformativePage page;
    public Section(String heading, String body) {
        this.heading = heading;
        this.body = body;
    }
}
