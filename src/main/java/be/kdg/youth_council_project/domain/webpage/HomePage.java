package be.kdg.youth_council_project.domain.webpage;

import be.kdg.youth_council_project.domain.platform.YouthCouncil;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@ToString
@Entity
@DiscriminatorValue("HOME")
@Table( uniqueConstraints={@UniqueConstraint(name="un_youthcouncil_homepage", columnNames={"youth_council_id"})})
public class HomePage extends WebPage {
    // if a page is a homepage, its title is always the youth council's name
    @OneToOne
    @JoinColumn(name = "youth_council_id")
    private YouthCouncil youthCouncil;

    public HomePage() {
        super(true, true, true, true, true, true);

    }
}
