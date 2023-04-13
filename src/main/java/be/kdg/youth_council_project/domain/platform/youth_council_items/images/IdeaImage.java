package be.kdg.youth_council_project.domain.platform.youth_council_items.images;


import be.kdg.youth_council_project.domain.platform.youth_council_items.Idea;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class IdeaImage {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="idea_id")
    private Idea idea;

    @Lob
    private byte[] image;
}
