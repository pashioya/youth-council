package be.kdg.youth_council_project.domain;

import be.kdg.youth_council_project.util.WebPage;

import java.util.List;

public class Module<T> {
    private Long id;
    private String description;
    private WebPage webPage;
    private List<T> item;
}
