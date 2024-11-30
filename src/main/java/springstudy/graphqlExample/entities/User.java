package springstudy.graphqlExample.entities;

import jakarta.persistence.*;
import lombok.*;
import springstudy.graphqlExample.domain.UserDomain;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
@Getter
@NoArgsConstructor()
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private int age;
    @Column(nullable = false)
    private String email;

    @OneToMany(mappedBy = "user")
    private List<Order> orders = new ArrayList<>();

    public void setUser(UserDomain userDomain) {
        this.username = userDomain.getUsername();
        this.age = userDomain.getAge();
        this.email = userDomain.getEmail();
    }
}
