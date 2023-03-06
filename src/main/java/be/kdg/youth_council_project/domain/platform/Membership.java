package be.kdg.youth_council_project.domain.platform;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Membership {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Enumerated(EnumType.STRING)
    private Role role;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime dateJoined;
    @ManyToOne
    @JoinColumn(name = "youth_council_id")
    private YouthCouncil youthCouncil;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
