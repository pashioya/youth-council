package be.kdg.youth_council_project.util;

import be.kdg.youth_council_project.domain.platform.Element;
import be.kdg.youth_council_project.domain.platform.Section;
import jakarta.persistence.*;

import java.util.HashMap;
import java.util.List;

@Entity
@Table(name = "webpage")
public class WebPage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;
    @Transient
    private List<Section> sections;
    @Transient
    private HashMap<Element, Module> pageElements;
}
