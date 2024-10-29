package springstudy.session.entities;


import jakarta.persistence.*;
import lombok.Getter;

@Table(name = "study_user")
@Entity
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "su_password")
    private String password;

    @Column(name = "su_email")
    private String email;

    @Column(name = "su_name")
    private String username;

    @Column(name = "su_role")
    private String role;
}
