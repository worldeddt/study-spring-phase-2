package springstudy.jpa.entities;


import jakarta.persistence.*;
import lombok.Getter;


@Table(name = "user")
@Entity
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
