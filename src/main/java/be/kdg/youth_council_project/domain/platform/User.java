package be.kdg.youth_council_project.domain.platform;

import be.kdg.youth_council_project.domain.platform.youth_council_items.Idea;
import be.kdg.youth_council_project.domain.platform.youth_council_items.NewsItem;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "app_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String postCode;
    private LocalDateTime dateCreated;
    private boolean isGeneralAdmin;

    @OneToMany(orphanRemoval = true, mappedBy = "author", cascade = CascadeType.PERSIST)
    private List<Idea> ideas;
    @OneToMany(orphanRemoval = true, mappedBy = "author", cascade = CascadeType.PERSIST)
    private List<NewsItem> newsItems;

    public User(String email, String username, String password, String firstName, String lastName, String postCode, boolean isGeneralAdmin) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.postCode = postCode;
        this.isGeneralAdmin = isGeneralAdmin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
