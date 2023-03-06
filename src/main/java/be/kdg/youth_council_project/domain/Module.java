package be.kdg.youth_council_project.domain;

import be.kdg.youth_council_project.util.WebPage;
import jakarta.persistence.*;

import java.util.List;


public class Module<T> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    @OneToOne
    @JoinColumn(name = "webpage_id")
    private WebPage webPage;
    @OneToMany(mappedBy = "module")
    private List<T> item;
}
