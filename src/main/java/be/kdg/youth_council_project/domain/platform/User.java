package be.kdg.youth_council_project.domain.platform;

import be.kdg.youth_council_project.domain.platform.youth_council_items.comments.NewsItemComment;
import be.kdg.youth_council_project.domain.platform.youth_council_items.like.NewsItemLike;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

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

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<Membership> memberships;
    @OneToMany(mappedBy = "author", orphanRemoval = true)
    private List<NewsItemComment> newsItemComments;
    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<NewsItemLike> newsItemLikes;
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
    public int hashCode() {
        return Objects.hash(id);
    }

}
