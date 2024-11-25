package springstudy.graphqlExample.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import springstudy.graphqlExample.domain.UserDomain;

@Entity
@Table
@Getter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private int age;
    private String email;

    public void setUser(UserDomain userDomain) {
        username = userDomain.getUsername();
        age = userDomain.getAge();
    }
}
